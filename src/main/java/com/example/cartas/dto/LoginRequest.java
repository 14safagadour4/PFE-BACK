package com.example.cartas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


public class LoginRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
