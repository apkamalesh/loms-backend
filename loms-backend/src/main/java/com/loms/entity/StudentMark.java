package com.loms.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "student_marks", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","test_id","learning_outcome_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentMark {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private User student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "test_id", nullable = false) private Test test;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "learning_outcome_id", nullable = false) private LearningOutcome learningOutcome;
    @Column(name = "marks_obtained", nullable = false, precision = 8, scale = 2) private BigDecimal marksObtained = BigDecimal.ZERO;
    @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate  protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
