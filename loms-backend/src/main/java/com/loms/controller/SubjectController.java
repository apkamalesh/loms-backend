package com.loms.controller;
import com.loms.dto.SubjectDto;
import com.loms.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @GetMapping("/admin/subjects") public ResponseEntity<List<SubjectDto.Response>> getAll() { return ResponseEntity.ok(subjectService.getAll()); }
    @GetMapping("/admin/subjects/{id}") public ResponseEntity<SubjectDto.Response> getById(@PathVariable Long id) { return ResponseEntity.ok(subjectService.getById(id)); }
    @GetMapping("/teacher/subjects/my/{tid}") public ResponseEntity<List<SubjectDto.Response>> getByTeacher(@PathVariable Long tid) { return ResponseEntity.ok(subjectService.getByTeacher(tid)); }
    @GetMapping("/student/subjects/my/{sid}") public ResponseEntity<List<SubjectDto.Response>> getByStudent(@PathVariable Long sid) { return ResponseEntity.ok(subjectService.getByStudent(sid)); }
    @PostMapping("/admin/subjects") public ResponseEntity<SubjectDto.Response> create(@Valid @RequestBody SubjectDto.Request req) { return ResponseEntity.ok(subjectService.create(req)); }
    @PutMapping("/admin/subjects/{id}") public ResponseEntity<SubjectDto.Response> update(@PathVariable Long id, @Valid @RequestBody SubjectDto.Request req) { return ResponseEntity.ok(subjectService.update(id, req)); }
    @DeleteMapping("/admin/subjects/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { subjectService.delete(id); return ResponseEntity.noContent().build(); }
}
