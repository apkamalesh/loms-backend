package com.loms.repository;
import com.loms.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
    List<LoginLog> findAllByOrderByLoginTimeDesc();
    List<LoginLog> findByUserIdOrderByLoginTimeDesc(Long userId);
    long countByUserId(Long userId);
}
