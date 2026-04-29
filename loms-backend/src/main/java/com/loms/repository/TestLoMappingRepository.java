package com.loms.repository;
import com.loms.entity.TestLoMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestLoMappingRepository extends JpaRepository<TestLoMapping, Long> {
    List<TestLoMapping> findByTestId(Long testId);
    Optional<TestLoMapping> findByTestIdAndLearningOutcomeId(Long testId, Long loId);
    void deleteByTestId(Long testId);
}
