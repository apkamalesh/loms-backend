package com.loms.service;

import com.loms.dto.MarksDto;
import com.loms.entity.*;
import com.loms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarksService {

    private final StudentMarkRepository markRepository;
    private final TestRepository testRepository;
    private final LearningOutcomeRepository loRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestLoMappingRepository mappingRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public void enterMarks(MarksDto.EnterMarksRequest req) {
        Test test = testRepository.findById(req.getTestId()).orElseThrow(() -> new RuntimeException("Test not found"));
        for (MarksDto.MarkEntry entry : req.getMarks()) {
            User student = userRepository.findById(entry.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
            LearningOutcome lo = loRepository.findById(entry.getLearningOutcomeId()).orElseThrow(() -> new RuntimeException("LO not found"));
            Optional<StudentMark> existing = markRepository.findByStudentIdAndTestIdAndLearningOutcomeId(entry.getStudentId(), req.getTestId(), entry.getLearningOutcomeId());
            if (existing.isPresent()) {
                existing.get().setMarksObtained(entry.getMarksObtained());
                markRepository.save(existing.get());
            } else {
                markRepository.save(StudentMark.builder().student(student).test(test).learningOutcome(lo).marksObtained(entry.getMarksObtained()).build());
            }
        }
    }

    public List<MarksDto.StudentSubjectResult> getStudentResults(Long studentId) {
        return subjectRepository.findByStudentId(studentId).stream()
            .map(s -> buildSubjectResult(studentId, s)).collect(Collectors.toList());
    }

    public MarksDto.StudentSubjectResult getStudentSubjectResult(Long studentId, Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));
        return buildSubjectResult(studentId, subject);
    }

    private MarksDto.StudentSubjectResult buildSubjectResult(Long studentId, Subject subject) {
        List<LearningOutcome> los = loRepository.findBySubjectId(subject.getId());
        List<Test> tests = testRepository.findBySubjectId(subject.getId());

        List<MarksDto.StudentLoResult> loResults = los.stream().map(lo -> {
            BigDecimal totalObtained = BigDecimal.ZERO, totalMax = BigDecimal.ZERO;
            for (Test test : tests) {
                Optional<TestLoMapping> mapping = mappingRepository.findByTestIdAndLearningOutcomeId(test.getId(), lo.getId());
                if (mapping.isPresent()) {
                    totalMax = totalMax.add(mapping.get().getMaxMarks());
                    Optional<StudentMark> mark = markRepository.findByStudentIdAndTestIdAndLearningOutcomeId(studentId, test.getId(), lo.getId());
                    if (mark.isPresent()) totalObtained = totalObtained.add(mark.get().getMarksObtained());
                }
            }
            BigDecimal pct = totalMax.compareTo(BigDecimal.ZERO) > 0
                ? totalObtained.divide(totalMax, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
            boolean achieved = totalMax.compareTo(BigDecimal.ZERO) > 0 && pct.compareTo(lo.getPassPercentage()) >= 0;

            MarksDto.StudentLoResult r = new MarksDto.StudentLoResult();
            r.setLoId(lo.getId()); r.setLoCode(lo.getCode()); r.setLoDescription(lo.getDescription());
            r.setTotalObtained(totalObtained); r.setTotalMax(totalMax); r.setPercentage(pct);
            r.setAchieved(achieved); r.setPassPercentage(lo.getPassPercentage());
            return r;
        }).collect(Collectors.toList());

        boolean passed = !loResults.isEmpty() && loResults.stream().allMatch(MarksDto.StudentLoResult::isAchieved);

        MarksDto.StudentSubjectResult res = new MarksDto.StudentSubjectResult();
        res.setSubjectId(subject.getId()); res.setSubjectName(subject.getName()); res.setSubjectCode(subject.getCode());
        res.setPassed(passed); res.setLoResults(loResults);
        return res;
    }

    public MarksDto.ClassSummaryResponse getClassSummary(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));
        List<LearningOutcome> los = loRepository.findBySubjectId(subjectId);
        List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(subjectId);

        List<MarksDto.ClassSummaryLoResult> loResults = los.stream().map(lo -> {
            long achieved = enrollments.stream().filter(e -> {
                MarksDto.StudentSubjectResult r = buildSubjectResult(e.getStudent().getId(), subject);
                return r.getLoResults().stream().filter(lr -> lr.getLoId().equals(lo.getId())).findFirst().map(MarksDto.StudentLoResult::isAchieved).orElse(false);
            }).count();

            long total = enrollments.size();
            BigDecimal rate = total > 0
                ? BigDecimal.valueOf(achieved).divide(BigDecimal.valueOf(total), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

            MarksDto.ClassSummaryLoResult r = new MarksDto.ClassSummaryLoResult();
            r.setLoId(lo.getId()); r.setLoCode(lo.getCode()); r.setLoDescription(lo.getDescription());
            r.setTotalStudents(total); r.setAchievedCount(achieved); r.setAchievementRate(rate); r.setPassPercentage(lo.getPassPercentage());
            return r;
        }).collect(Collectors.toList());

        MarksDto.ClassSummaryResponse res = new MarksDto.ClassSummaryResponse();
        res.setSubjectId(subjectId); res.setSubjectName(subject.getName()); res.setLoResults(loResults);
        return res;
    }
}
