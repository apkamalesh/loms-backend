package com.loms.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "test_lo_mapping", uniqueConstraints = @UniqueConstraint(columnNames = {"test_id","learning_outcome_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TestLoMapping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "test_id", nullable = false) private Test test;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "learning_outcome_id", nullable = false) private LearningOutcome learningOutcome;
    @Column(name = "max_marks", nullable = false, precision = 8, scale = 2) private BigDecimal maxMarks;
}
