package com.loms.service;
import com.loms.dto.SubjectDto;
import com.loms.entity.*;
import com.loms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final LearningOutcomeRepository loRepository;
    private final TestRepository testRepository;

    public List<SubjectDto.Response> getAll() { return subjectRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }
    public SubjectDto.Response getById(Long id) { return toResponse(findById(id)); }
    public List<SubjectDto.Response> getByTeacher(Long tid) { return subjectRepository.findByTeacherId(tid).stream().map(this::toResponse).collect(Collectors.toList()); }
    public List<SubjectDto.Response> getByStudent(Long sid) { return subjectRepository.findByStudentId(sid).stream().map(this::toResponse).collect(Collectors.toList()); }

    public SubjectDto.Response create(SubjectDto.Request req) {
        if (subjectRepository.existsByCode(req.getCode())) throw new RuntimeException("Code exists");
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Dept not found"));
        Subject s = Subject.builder().name(req.getName()).code(req.getCode().toUpperCase()).department(dept).description(req.getDescription()).build();
        if (req.getTeacherId() != null) s.setTeacher(userRepository.findById(req.getTeacherId()).orElseThrow(() -> new RuntimeException("Teacher not found")));
        return toResponse(subjectRepository.save(s));
    }
    public SubjectDto.Response update(Long id, SubjectDto.Request req) {
        Subject s = findById(id);
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Dept not found"));
        s.setName(req.getName()); s.setCode(req.getCode().toUpperCase()); s.setDepartment(dept); s.setDescription(req.getDescription());
        s.setTeacher(req.getTeacherId() != null ? userRepository.findById(req.getTeacherId()).orElse(null) : null);
        return toResponse(subjectRepository.save(s));
    }
    public void delete(Long id) { subjectRepository.deleteById(id); }
    private Subject findById(Long id) { return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found")); }

    public SubjectDto.Response toResponse(Subject s) {
        SubjectDto.Response r = new SubjectDto.Response();
        r.setId(s.getId()); r.setName(s.getName()); r.setCode(s.getCode());
        r.setDepartmentId(s.getDepartment().getId()); r.setDepartmentName(s.getDepartment().getName());
        if (s.getTeacher() != null) { r.setTeacherId(s.getTeacher().getId()); r.setTeacherName(s.getTeacher().getName()); }
        r.setDescription(s.getDescription());
        r.setOutcomeCount(loRepository.countBySubjectId(s.getId()));
        r.setTestCount(testRepository.countBySubjectId(s.getId()));
        r.setCreatedAt(s.getCreatedAt());
        return r;
    }
}
