package com.loms.controller;
import com.loms.dto.LearningOutcomeDto;
import com.loms.service.LearningOutcomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class LearningOutcomeController {
    private final LearningOutcomeService loService;
    @GetMapping("/admin/learning-outcomes") public ResponseEntity<List<LearningOutcomeDto.Response>> getAll() { return ResponseEntity.ok(loService.getAll()); }
    @GetMapping("/admin/learning-outcomes/{id}") public ResponseEntity<LearningOutcomeDto.Response> getById(@PathVariable Long id) { return ResponseEntity.ok(loService.getById(id)); }
    @GetMapping("/teacher/learning-outcomes/subject/{sid}") public ResponseEntity<List<LearningOutcomeDto.Response>> getBySubject(@PathVariable Long sid) { return ResponseEntity.ok(loService.getBySubject(sid)); }
    @PostMapping("/admin/learning-outcomes") public ResponseEntity<LearningOutcomeDto.Response> create(@Valid @RequestBody LearningOutcomeDto.Request req) { return ResponseEntity.ok(loService.create(req)); }
    @PutMapping("/admin/learning-outcomes/{id}") public ResponseEntity<LearningOutcomeDto.Response> update(@PathVariable Long id, @Valid @RequestBody LearningOutcomeDto.Request req) { return ResponseEntity.ok(loService.update(id, req)); }
    @DeleteMapping("/admin/learning-outcomes/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { loService.delete(id); return ResponseEntity.noContent().build(); }
}
