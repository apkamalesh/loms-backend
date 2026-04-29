package com.loms.service;

import com.loms.dto.LoginLogDto;
import com.loms.dto.UserDto;
import com.loms.entity.LoginLog;
import com.loms.entity.User;
import com.loms.repository.LoginLogRepository;
import com.loms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto.Response> getAll() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<UserDto.Response> getByRole(User.Role role) {
        return userRepository.findByRole(role).stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Users pending approval (students & teachers who signed up)
    public List<UserDto.Response> getPendingApproval() {
        return userRepository.findByApprovedFalseAndRoleNot(User.Role.ADMIN)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserDto.Response getById(Long id) {
        return toResponse(findById(id));
    }

    public UserDto.Response create(UserDto.Request req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered");
        User user = User.builder()
            .name(req.getName()).email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword() != null ? req.getPassword() : "changeme123"))
            .role(req.getRole()).approved(req.isApproved()).build();
        return toResponse(userRepository.save(user));
    }

    public UserDto.Response update(Long id, UserDto.Request req) {
        User user = findById(id);
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setApproved(req.isApproved());
        if (req.getPassword() != null && !req.getPassword().isBlank())
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        return toResponse(userRepository.save(user));
    }

    // Approve a pending user
    public UserDto.Response approveUser(Long id) {
        User user = findById(id);
        user.setApproved(true);
        return toResponse(userRepository.save(user));
    }

    // Reject / delete pending user
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Get all login logs (for admin visitor view)
    public List<LoginLogDto.Response> getAllLoginLogs() {
        return loginLogRepository.findAllByOrderByLoginTimeDesc()
            .stream().map(this::toLogResponse).collect(Collectors.toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public UserDto.Response toResponse(User u) {
        UserDto.Response res = new UserDto.Response();
        res.setId(u.getId());
        res.setName(u.getName());
        res.setEmail(u.getEmail());
        res.setRole(u.getRole());
        res.setApproved(u.isApproved());
        res.setCreatedAt(u.getCreatedAt());
        res.setLoginCount(loginLogRepository.countByUserId(u.getId()));
        loginLogRepository.findByUserIdOrderByLoginTimeDesc(u.getId())
            .stream().findFirst().ifPresent(log -> res.setLastLogin(log.getLoginTime()));
        return res;
    }

    private LoginLogDto.Response toLogResponse(LoginLog log) {
        LoginLogDto.Response res = new LoginLogDto.Response();
        res.setId(log.getId());
        res.setUserId(log.getUserId());
        res.setName(log.getName());
        res.setEmail(log.getEmail());
        res.setRole(log.getRole());
        res.setLoginTime(log.getLoginTime());
        res.setIpAddress(log.getIpAddress());
        res.setUserAgent(log.getUserAgent());
        return res;
    }
}
