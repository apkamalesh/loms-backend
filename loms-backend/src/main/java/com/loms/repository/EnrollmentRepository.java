package com.loms.repository;
import com.loms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findBySubjectId(Long subjectId);
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
    Optional<Enrollment> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    long countBySubjectId(Long subjectId);
}
