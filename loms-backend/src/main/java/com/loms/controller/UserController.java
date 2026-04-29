package com.loms.controller;
import com.loms.dto.LoginLogDto;
import com.loms.dto.UserDto;
import com.loms.entity.User;
import com.loms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto.Response>> getAll() { return ResponseEntity.ok(userService.getAll()); }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<UserDto.Response>> getByRole(@PathVariable User.Role role) { return ResponseEntity.ok(userService.getByRole(role)); }

    @GetMapping("/users/pending")
    public ResponseEntity<List<UserDto.Response>> getPending() { return ResponseEntity.ok(userService.getPendingApproval()); }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto.Response> getById(@PathVariable Long id) { return ResponseEntity.ok(userService.getById(id)); }

    @PostMapping("/users")
    public ResponseEntity<UserDto.Response> create(@Valid @RequestBody UserDto.Request req) { return ResponseEntity.ok(userService.create(req)); }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto.Response> update(@PathVariable Long id, @Valid @RequestBody UserDto.Request req) { return ResponseEntity.ok(userService.update(id, req)); }

    @PatchMapping("/users/{id}/approve")
    public ResponseEntity<UserDto.Response> approve(@PathVariable Long id) { return ResponseEntity.ok(userService.approveUser(id)); }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { userService.delete(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/login-logs")
    public ResponseEntity<List<LoginLogDto.Response>> getLoginLogs() { return ResponseEntity.ok(userService.getAllLoginLogs()); }
}
