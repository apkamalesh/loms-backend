package com.loms.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

public class DepartmentDto {
    @Data public static class Request {
        @NotBlank private String name;
        @NotBlank private String code;
        private String description;
    }
    @Data public static class Response {
        private Long id;
        private String name;
        private String code;
        private String description;
        private long subjectCount;
        private LocalDateTime createdAt;
    }
}
