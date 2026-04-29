package com.loms.controller;
import com.loms.dto.SubjectDto;
import com.loms.entity.User;
import com.loms.repository.*;
import com.loms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class DashboardController {
    private final DepartmentRepository departmentRepository;
    private final SubjectRepository subjectRepository;
    private final LearningOutcomeRepository loRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final SubjectService subjectService;
    private final DepartmentService departmentService;
    private final LoginLogRepository loginLogRepository;

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Map<String,Object>> adminDashboard() {
        Map<String,Object> d = new LinkedHashMap<>();
        d.put("departmentCount", departmentRepository.count());
        d.put("subjectCount", subjectRepository.count());
        d.put("outcomeCount", loRepository.count());
        d.put("teacherCount", userRepository.findByRole(User.Role.TEACHER).size());
        d.put("studentCount", userRepository.findByRole(User.Role.STUDENT).size());
        d.put("pendingCount", userRepository.findByApprovedFalseAndRoleNot(User.Role.ADMIN).size());
        d.put("totalLogins", loginLogRepository.count());
        d.put("recentDepartments", departmentService.getAll().stream().limit(5).collect(Collectors.toList()));
        d.put("recentSubjects", subjectService.getAll().stream().limit(5).collect(Collectors.toList()));
        return ResponseEntity.ok(d);
    }

    @GetMapping("/teacher/dashboard/{teacherId}")
    public ResponseEntity<Map<String,Object>> teacherDashboard(@PathVariable Long teacherId) {
        List<SubjectDto.Response> subjects = subjectService.getByTeacher(teacherId);
        long tests = subjects.stream().mapToLong(s -> testRepository.countBySubjectId(s.getId())).sum();
        long los = subjects.stream().mapToLong(s -> loRepository.countBySubjectId(s.getId())).sum();
        List<Map<String,Object>> recentTests = subjects.stream()
            .flatMap(s -> testRepository.findBySubjectIdOrderByTestDateDesc(s.getId()).stream().map(t -> {
                Map<String,Object> m = new LinkedHashMap<>();
                m.put("id",t.getId()); m.put("name",t.getName()); m.put("subjectName",s.getName());
                m.put("totalMarks",t.getTotalMarks()); m.put("testDate", t.getTestDate() != null ? t.getTestDate().toString() : "");
                return m;
            })).limit(5).collect(Collectors.toList());
        Map<String,Object> d = new LinkedHashMap<>();
        d.put("subjectCount",subjects.size()); d.put("testCount",tests); d.put("outcomeCount",los);
        d.put("subjects",subjects); d.put("recentTests",recentTests);
        return ResponseEntity.ok(d);
    }

    @GetMapping("/student/dashboard/{studentId}")
    public ResponseEntity<Map<String,Object>> studentDashboard(@PathVariable Long studentId) {
        List<SubjectDto.Response> subjects = subjectService.getByStudent(studentId);
        long los = subjects.stream().mapToLong(s -> loRepository.countBySubjectId(s.getId())).sum();
        Map<String,Object> d = new LinkedHashMap<>();
        d.put("subjectCount",subjects.size()); d.put("outcomeCount",los); d.put("subjects",subjects);
        return ResponseEntity.ok(d);
    }
}
