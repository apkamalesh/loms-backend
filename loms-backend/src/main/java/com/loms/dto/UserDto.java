package com.loms.dto;
import com.loms.entity.User;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

public class UserDto {
    @Data public static class Request {
        @NotBlank private String name;
        @NotBlank @Email private String email;
        private String password;
        @NotNull private User.Role role;
        private boolean approved;
    }
    @Data public static class Response {
        private Long id;
        private String name;
        private String email;
        private User.Role role;
        private boolean approved;
        private LocalDateTime createdAt;
        private long loginCount;
        private LocalDateTime lastLogin;
    }
}
