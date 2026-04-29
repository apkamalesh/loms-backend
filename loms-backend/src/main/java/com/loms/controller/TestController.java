package com.loms.controller;
import com.loms.dto.TestDto;
import com.loms.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/teacher/tests") @RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    @GetMapping("/subject/{sid}") public ResponseEntity<List<TestDto.Response>> getBySubject(@PathVariable Long sid) { return ResponseEntity.ok(testService.getBySubject(sid)); }
    @GetMapping("/{id}") public ResponseEntity<TestDto.Response> getById(@PathVariable Long id) { return ResponseEntity.ok(testService.getById(id)); }
    @PostMapping public ResponseEntity<TestDto.Response> create(@Valid @RequestBody TestDto.Request req) { return ResponseEntity.ok(testService.create(req)); }
    @PutMapping("/{id}") public ResponseEntity<TestDto.Response> update(@PathVariable Long id, @Valid @RequestBody TestDto.Request req) { return ResponseEntity.ok(testService.update(id, req)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { testService.delete(id); return ResponseEntity.noContent().build(); }
}
