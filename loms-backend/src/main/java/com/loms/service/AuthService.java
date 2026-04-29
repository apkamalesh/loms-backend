package com.loms.service;

import com.loms.dto.AuthDto;
import com.loms.entity.LoginLog;
import com.loms.entity.User;
import com.loms.repository.LoginLogRepository;
import com.loms.repository.UserRepository;
import com.loms.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public String signup(AuthDto.SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered: " + req.getEmail());
        if (req.getRole() == User.Role.ADMIN)
            throw new RuntimeException("Cannot self-register as ADMIN");

        User user = User.builder()
            .name(req.getName())
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .role(req.getRole())
            .approved(false)
            .build();
        userRepository.save(user);
        return "Registration successful! Please wait for admin approval before logging in.";
    }

    public AuthDto.LoginResponse login(AuthDto.LoginRequest req, HttpServletRequest httpRequest) {
        // Step 1: Find user
        User user = userRepository.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Step 2: Check password manually first (before Spring Security)
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid email or password");

        // Step 3: Check approval - MUST be before generating token
        if (user.getRole() != User.Role.ADMIN && !user.isApproved())
            throw new RuntimeException("Your account is pending admin approval. Please wait.");

        // Step 4: Authenticate via Spring Security
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        String token = jwtUtils.generateToken(auth);

        // Step 5: Log the login
        String ip = getClientIp(httpRequest);
        String ua = httpRequest.getHeader("User-Agent");
        loginLogRepository.save(LoginLog.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .role(user.getRole())
            .ipAddress(ip)
            .userAgent(ua != null && ua.length() > 500 ? ua.substring(0, 500) : ua)
            .build());

        return new AuthDto.LoginResponse(
            token, user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isApproved());
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        return (xf != null && !xf.isEmpty()) ? xf.split(",")[0].trim() : request.getRemoteAddr();
    }
}
