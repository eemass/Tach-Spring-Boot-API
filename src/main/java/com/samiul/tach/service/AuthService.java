package com.samiul.tach.service;

import com.cloudinary.Cloudinary;
import com.samiul.tach.dto.LoginRequest;
import com.samiul.tach.dto.SignupRequest;
import com.samiul.tach.dto.UpdateProfileRequest;
import com.samiul.tach.dto.UserResponse;
import com.samiul.tach.model.User;
import com.samiul.tach.repository.UserRepository;
import com.samiul.tach.security.JwtUtils;
import com.samiul.tach.util.ImageUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    public UserResponse signup(SignupRequest request, HttpServletResponse response) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Account with this email already exists.");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(hashedPassword)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtUtils.generateToken(savedUser.getId());
        jwtUtils.setToken(response, token);

        return new UserResponse(savedUser);

    }

    public  UserResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User with this email does not exist."));

        boolean isPasswordCorrect = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordCorrect) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        String token = jwtUtils.generateToken(user.getId());
        jwtUtils.setToken(response, token);

        return new UserResponse(user);
    }

    public void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");

        response.addCookie(cookie);
    }

    public UserResponse updateProfile(UpdateProfileRequest updateProfileRequest, User currentUser) throws IOException {
        boolean updated = false;

        if (updateProfileRequest.getProfilePic() != null) {
            if (currentUser.getProfilePic() != null && !currentUser.getProfilePic().isEmpty()) {
                String publicId = ImageUtils.extractPublicId(currentUser.getProfilePic());
                cloudinary.uploader().destroy(publicId, Map.of());
            }
            Map<String, Object> uploadResult = cloudinary.uploader().upload(updateProfileRequest.getProfilePic(), Map.of());
            currentUser.setProfilePic((String) uploadResult.get("secure_url"));
            updated = true;
        }

        if (updated) {
            userRepository.save(currentUser);
        }

        return new UserResponse(currentUser);
    }
}
