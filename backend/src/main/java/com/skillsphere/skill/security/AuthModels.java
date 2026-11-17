package com.skillsphere.skill.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class AuthModels {
    private AuthModels() {
    }

    public record LoginRequest(
        @JsonProperty("username") @NotBlank String username,
        @JsonProperty("password") @NotBlank String password
    ) {}

    public record CreateEmployeeAccountRequest(
        @JsonProperty("username") @NotBlank @Email @Size(max = 150) String username,
        @JsonProperty("password") @NotBlank @Size(min = 12, max = 72) String password,
        @JsonProperty("employeeId") @NotNull Long employeeId
    ) {}

    public record CreateStudentAccountRequest(
        @JsonProperty("username") @NotBlank @Email @Size(max = 150) String username,
        @JsonProperty("password") @NotBlank @Size(min = 12, max = 72) String password,
        @JsonProperty("name") @NotBlank @Size(max = 100) String name,
        @JsonProperty("email") @NotBlank @Email @Size(max = 150) String email,
        @JsonProperty("program") @NotBlank @Size(max = 150) String program
    ) {}

        public record AuthResponse(String accessToken, String tokenType, long expiresIn,
                String username, String role, Long employeeId, Long studentId) {
        }

        public record CurrentUserResponse(String username, String role, Long employeeId, Long studentId) {
        }

        public record AccountCreatedResponse(String username, String role, Long employeeId, Long studentId) {
        }
}