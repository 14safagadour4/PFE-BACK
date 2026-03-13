package com.example.cartas.dto;

import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = "Bearer";
    private long expiresIn;
    private UserInfo user;

    // Getters
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresIn() { return expiresIn; }
    public UserInfo getUser() { return user; }

    // Setters
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
    public void setUser(UserInfo user) { this.user = user; }

    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private String preferredTheme;

        // Getters
        public Long getId() { return id; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getPreferredTheme() { return preferredTheme; }

        // Setters
        public void setId(Long id) { this.id = id; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public void setEmail(String email) { this.email = email; }
        public void setRole(String role) { this.role = role; }
        public void setPreferredTheme(String preferredTheme) { this.preferredTheme = preferredTheme; }

    }
}