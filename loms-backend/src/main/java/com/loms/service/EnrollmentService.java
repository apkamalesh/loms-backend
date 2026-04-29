package com.loms.service;
import com.loms.entity.*;
import com.loms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public void enroll(Long studentId, Long subjectId) {
        if (enrollmentRepository.existsByStudentIdAndSubjectId(studentId, subjectId)) throw new RuntimeException("Already enrolled");
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));
        enrollmentRepository.save(Enrollment.builder().student(student).subject(subject).build());
    }
    public void unenroll(Long studentId, Long subjectId) {
        Enrollment e = enrollmentRepository.findByStudentIdAndSubjectId(studentId, subjectId).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollmentRepository.delete(e);
    }
    public List<Long> getEnrolledStudentIds(Long subjectId) {
        return enrollmentRepository.findBySubjectId(subjectId).stream().map(e -> e.getStudent().getId()).collect(Collectors.toList());
    }
}
