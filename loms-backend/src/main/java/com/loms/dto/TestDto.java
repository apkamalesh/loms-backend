package com.loms.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestDto {
    @Data public static class LoMapping { private Long learningOutcomeId; private BigDecimal maxMarks; }
    @Data public static class Request {
        @NotNull private Long subjectId;
        @NotBlank private String name;
        @NotNull private BigDecimal totalMarks;
        @NotNull private LocalDate testDate;
        private List<LoMapping> loMappings;
    }
    @Data public static class Response {
        private Long id; private Long subjectId; private String subjectName;
        private String name; private BigDecimal totalMarks; private LocalDate testDate;
        private List<LoMappingResponse> loMappings; private LocalDateTime createdAt;
    }
    @Data public static class LoMappingResponse {
        private Long id; private Long learningOutcomeId; private String loCode;
        private String loDescription; private BigDecimal maxMarks;
    }
}
