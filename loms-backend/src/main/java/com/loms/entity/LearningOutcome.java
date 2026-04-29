package com.loms.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "learning_outcomes")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LearningOutcome {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "subject_id", nullable = false) private Subject subject;
    @Column(nullable = false, length = 20) private String code;
    @Column(nullable = false, columnDefinition = "TEXT") private String description;
    @Column(name = "pass_percentage", precision = 5, scale = 2) private BigDecimal passPercentage = new BigDecimal("60.00");
    @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "learningOutcome", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude private List<TestLoMapping> testLoMappings;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate  protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
