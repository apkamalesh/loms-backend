package com.loms.controller;
import com.loms.dto.MarksDto;
import com.loms.service.MarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class MarksController {
    private final MarksService marksService;
    @PostMapping("/teacher/marks") public ResponseEntity<Void> enterMarks(@RequestBody MarksDto.EnterMarksRequest req) { marksService.enterMarks(req); return ResponseEntity.ok().build(); }
    @GetMapping("/teacher/marks/class-summary/{sid}") public ResponseEntity<MarksDto.ClassSummaryResponse> classSummary(@PathVariable Long sid) { return ResponseEntity.ok(marksService.getClassSummary(sid)); }
    @GetMapping("/student/marks/results/{studentId}") public ResponseEntity<List<MarksDto.StudentSubjectResult>> studentResults(@PathVariable Long studentId) { return ResponseEntity.ok(marksService.getStudentResults(studentId)); }
    @GetMapping("/student/marks/results/{studentId}/subject/{subjectId}") public ResponseEntity<MarksDto.StudentSubjectResult> subjectResult(@PathVariable Long studentId, @PathVariable Long subjectId) { return ResponseEntity.ok(marksService.getStudentSubjectResult(studentId, subjectId)); }
}
