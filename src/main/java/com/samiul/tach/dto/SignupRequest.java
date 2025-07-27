package com.samiul.tach.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;
}
