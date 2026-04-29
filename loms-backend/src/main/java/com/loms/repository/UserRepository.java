package com.loms.repository;
import com.loms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByApprovedFalseAndRoleNot(User.Role role);
    List<User> findByRoleAndApprovedTrue(User.Role role);
}
