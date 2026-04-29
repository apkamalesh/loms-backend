package com.loms.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

public class SubjectDto {
    @Data public static class Request {
        @NotBlank private String name;
        @NotBlank private String code;
        @NotNull private Long departmentId;
        private Long teacherId;
        private String description;
    }
    @Data public static class Response {
        private Long id;
        private String name;
        private String code;
        private String departmentName;
        private Long departmentId;
        private String teacherName;
        private Long teacherId;
        private String description;
        private long outcomeCount;
        private long testCount;
        private LocalDateTime createdAt;
    }
}
