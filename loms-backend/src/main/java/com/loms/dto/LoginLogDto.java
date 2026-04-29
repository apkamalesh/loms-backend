package com.loms.dto;
import com.loms.entity.User;
import lombok.Data;
import java.time.LocalDateTime;

public class LoginLogDto {
    @Data public static class Response {
        private Long id;
        private Long userId;
        private String name;
        private String email;
        private User.Role role;
        private LocalDateTime loginTime;
        private String ipAddress;
        private String userAgent;
    }
}
