package com.loms.dto;
import com.loms.entity.User;
import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthDto {
    @Data public static class SignupRequest {
        @NotBlank private String name;
        @NotBlank @Email private String email;
        @NotBlank @Size(min=6) private String password;
        @NotNull private User.Role role;
    }
    @Data public static class LoginRequest {
        @NotBlank @Email private String email;
        @NotBlank private String password;
    }
    @Data public static class LoginResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String name;
        private String email;
        private User.Role role;
        private boolean approved;
        public LoginResponse(String token, Long id, String name, String email, User.Role role, boolean approved) {
            this.token=token; this.id=id; this.name=name; this.email=email; this.role=role; this.approved=approved;
        }
    }
}
