package com.loms.repository;
import com.loms.entity.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LearningOutcomeRepository extends JpaRepository<LearningOutcome, Long> {
    List<LearningOutcome> findBySubjectId(Long subjectId);
    long countBySubjectId(Long subjectId);
}
