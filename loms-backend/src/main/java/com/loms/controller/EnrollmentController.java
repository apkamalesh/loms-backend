package com.loms.controller;
import com.loms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/admin/enrollments") @RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping("/enroll") public ResponseEntity<Void> enroll(@RequestParam Long studentId, @RequestParam Long subjectId) { enrollmentService.enroll(studentId, subjectId); return ResponseEntity.ok().build(); }
    @DeleteMapping("/unenroll") public ResponseEntity<Void> unenroll(@RequestParam Long studentId, @RequestParam Long subjectId) { enrollmentService.unenroll(studentId, subjectId); return ResponseEntity.noContent().build(); }
    @GetMapping("/subject/{subjectId}/students") public ResponseEntity<List<Long>> getStudents(@PathVariable Long subjectId) { return ResponseEntity.ok(enrollmentService.getEnrolledStudentIds(subjectId)); }
}
