package com.example.cartas.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileRegisterRequest {

    @NotBlank @Size(max = 100)
    private String firstName;

    @NotBlank @Size(max = 100)
    private String lastName;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8)
    private String password;

    @NotBlank
    private String role; // APP_USER, SPECIALIST, ART_THERAPIST

    // Optional fields for professionals
    private String phone;
    private String specialty;
    private String licenseNumber;
    private String bio;
    private String artDiscipline;
}
