package com.loms.service;
import com.loms.dto.LearningOutcomeDto;
import com.loms.entity.*;
import com.loms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class LearningOutcomeService {
    private final LearningOutcomeRepository loRepository;
    private final SubjectRepository subjectRepository;

    public List<LearningOutcomeDto.Response> getAll() { return loRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }
    public List<LearningOutcomeDto.Response> getBySubject(Long sid) { return loRepository.findBySubjectId(sid).stream().map(this::toResponse).collect(Collectors.toList()); }
    public LearningOutcomeDto.Response getById(Long id) { return toResponse(findById(id)); }
    public LearningOutcomeDto.Response create(LearningOutcomeDto.Request req) {
        Subject s = subjectRepository.findById(req.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));
        return toResponse(loRepository.save(LearningOutcome.builder().subject(s).code(req.getCode()).description(req.getDescription()).passPercentage(req.getPassPercentage()).build()));
    }
    public LearningOutcomeDto.Response update(Long id, LearningOutcomeDto.Request req) {
        LearningOutcome lo = findById(id); lo.setCode(req.getCode()); lo.setDescription(req.getDescription()); lo.setPassPercentage(req.getPassPercentage());
        return toResponse(loRepository.save(lo));
    }
    public void delete(Long id) { loRepository.deleteById(id); }
    private LearningOutcome findById(Long id) { return loRepository.findById(id).orElseThrow(() -> new RuntimeException("LO not found")); }
    private LearningOutcomeDto.Response toResponse(LearningOutcome lo) {
        LearningOutcomeDto.Response r = new LearningOutcomeDto.Response();
        r.setId(lo.getId()); r.setSubjectId(lo.getSubject().getId()); r.setSubjectName(lo.getSubject().getName());
        r.setSubjectCode(lo.getSubject().getCode()); r.setCode(lo.getCode()); r.setDescription(lo.getDescription()); r.setPassPercentage(lo.getPassPercentage());
        return r;
    }
}
