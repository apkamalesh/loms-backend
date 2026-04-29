package com.loms.repository;
import com.loms.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findBySubjectId(Long subjectId);
    List<Test> findBySubjectIdOrderByTestDateDesc(Long subjectId);
    long countBySubjectId(Long subjectId);
}
