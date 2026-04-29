package com.loms.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class MarksDto {
    @Data public static class MarkEntry { private Long studentId; private Long learningOutcomeId; private BigDecimal marksObtained; }
    @Data public static class EnterMarksRequest { private Long testId; private List<MarkEntry> marks; }
    @Data public static class StudentLoResult {
        private Long loId; private String loCode; private String loDescription;
        private BigDecimal totalObtained; private BigDecimal totalMax;
        private BigDecimal percentage; private boolean achieved; private BigDecimal passPercentage;
    }
    @Data public static class StudentSubjectResult {
        private Long subjectId; private String subjectName; private String subjectCode;
        private boolean passed; private List<StudentLoResult> loResults;
    }
    @Data public static class ClassSummaryLoResult {
        private Long loId; private String loCode; private String loDescription;
        private long totalStudents; private long achievedCount; private BigDecimal achievementRate; private BigDecimal passPercentage;
    }
    @Data public static class ClassSummaryResponse {
        private Long subjectId; private String subjectName; private List<ClassSummaryLoResult> loResults;
    }
}
