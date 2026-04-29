package com.loms.repository;
import com.loms.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByCode(String code);
    List<Subject> findByDepartmentId(Long departmentId);
    List<Subject> findByTeacherId(Long teacherId);
    @Query("SELECT s FROM Subject s JOIN s.enrollments e WHERE e.student.id = :studentId")
    List<Subject> findByStudentId(Long studentId);
}
