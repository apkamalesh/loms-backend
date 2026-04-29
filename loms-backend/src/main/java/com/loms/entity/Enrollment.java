package com.loms.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "enrollments", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","subject_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private User student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "subject_id", nullable = false) private Subject subject;
    @Column(name = "enrolled_at", updatable = false) private LocalDateTime enrolledAt;
    @PrePersist protected void onCreate() { enrolledAt = LocalDateTime.now(); }
}
