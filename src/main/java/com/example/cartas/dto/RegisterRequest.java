package com.example.cartas.dto;

import jakarta.validation.constraints.*;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    @NotBlank @Size(max = 100)
    private String firstName;

    @NotBlank @Size(max = 100)
    private String lastName;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8)
    private String password;

    @NotBlank
    private String secretKey;

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName () { return lastName;}
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSecretKey() { return secretKey; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    
}