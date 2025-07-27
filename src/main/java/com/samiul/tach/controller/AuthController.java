package com.samiul.tach.controller;

import com.samiul.tach.dto.SignupRequest;
import com.samiul.tach.dto.UserResponse;
import com.samiul.tach.model.User;
import com.samiul.tach.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request, HttpServletResponse response) {
        UserResponse userResponse = authService.signup(request, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
