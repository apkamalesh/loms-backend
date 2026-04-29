package com.loms.repository;
import com.loms.entity.StudentMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentMarkRepository extends JpaRepository<StudentMark, Long> {
    Optional<StudentMark> findByStudentIdAndTestIdAndLearningOutcomeId(Long studentId, Long testId, Long loId);
    @Query("SELECT sm FROM StudentMark sm WHERE sm.test.subject.id = :subjectId AND sm.student.id = :studentId")
    List<StudentMark> findBySubjectAndStudent(Long subjectId, Long studentId);
}
