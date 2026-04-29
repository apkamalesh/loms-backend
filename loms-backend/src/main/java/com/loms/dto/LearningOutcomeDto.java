package com.loms.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

public class LearningOutcomeDto {
    @Data public static class Request {
        @NotNull private Long subjectId;
        @NotBlank private String code;
        @NotBlank private String description;
        private BigDecimal passPercentage = new BigDecimal("60.00");
    }
    @Data public static class Response {
        private Long id;
        private Long subjectId;
        private String subjectName;
        private String subjectCode;
        private String code;
        private String description;
        private BigDecimal passPercentage;
    }
}
