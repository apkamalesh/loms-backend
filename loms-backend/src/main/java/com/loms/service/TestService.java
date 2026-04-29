package com.loms.service;
import com.loms.dto.TestDto;
import com.loms.entity.*;
import com.loms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final SubjectRepository subjectRepository;
    private final LearningOutcomeRepository loRepository;
    private final TestLoMappingRepository mappingRepository;

    public List<TestDto.Response> getBySubject(Long sid) { return testRepository.findBySubjectIdOrderByTestDateDesc(sid).stream().map(this::toResponse).collect(Collectors.toList()); }
    public TestDto.Response getById(Long id) { return toResponse(findById(id)); }

    @Transactional
    public TestDto.Response create(TestDto.Request req) {
        Subject s = subjectRepository.findById(req.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));
        Test test = testRepository.save(Test.builder().subject(s).name(req.getName()).totalMarks(req.getTotalMarks()).testDate(req.getTestDate()).build());
        if (req.getLoMappings() != null) req.getLoMappings().forEach(m -> {
            LearningOutcome lo = loRepository.findById(m.getLearningOutcomeId()).orElseThrow(() -> new RuntimeException("LO not found"));
            mappingRepository.save(TestLoMapping.builder().test(test).learningOutcome(lo).maxMarks(m.getMaxMarks()).build());
        });
        return toResponse(test);
    }

    @Transactional
    public TestDto.Response update(Long id, TestDto.Request req) {
        Test test = findById(id); test.setName(req.getName()); test.setTotalMarks(req.getTotalMarks()); test.setTestDate(req.getTestDate());
        mappingRepository.deleteByTestId(id);
        if (req.getLoMappings() != null) req.getLoMappings().forEach(m -> {
            LearningOutcome lo = loRepository.findById(m.getLearningOutcomeId()).orElseThrow(() -> new RuntimeException("LO not found"));
            mappingRepository.save(TestLoMapping.builder().test(test).learningOutcome(lo).maxMarks(m.getMaxMarks()).build());
        });
        return toResponse(testRepository.save(test));
    }

    public void delete(Long id) { testRepository.deleteById(id); }
    private Test findById(Long id) { return testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found")); }
    private TestDto.Response toResponse(Test t) {
        TestDto.Response r = new TestDto.Response();
        r.setId(t.getId()); r.setSubjectId(t.getSubject().getId()); r.setSubjectName(t.getSubject().getName());
        r.setName(t.getName()); r.setTotalMarks(t.getTotalMarks()); r.setTestDate(t.getTestDate()); r.setCreatedAt(t.getCreatedAt());
        r.setLoMappings(mappingRepository.findByTestId(t.getId()).stream().map(m -> {
            TestDto.LoMappingResponse mr = new TestDto.LoMappingResponse();
            mr.setId(m.getId()); mr.setLearningOutcomeId(m.getLearningOutcome().getId());
            mr.setLoCode(m.getLearningOutcome().getCode()); mr.setLoDescription(m.getLearningOutcome().getDescription()); mr.setMaxMarks(m.getMaxMarks());
            return mr;
        }).collect(java.util.stream.Collectors.toList()));
        return r;
    }
}
