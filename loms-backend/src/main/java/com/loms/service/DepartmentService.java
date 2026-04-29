package com.loms.service;
import com.loms.dto.DepartmentDto;
import com.loms.entity.Department;
import com.loms.repository.DepartmentRepository;
import com.loms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final SubjectRepository subjectRepository;

    public List<DepartmentDto.Response> getAll() {
        return departmentRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }
    public DepartmentDto.Response getById(Long id) { return toResponse(findById(id)); }
    public DepartmentDto.Response create(DepartmentDto.Request req) {
        if (departmentRepository.existsByCode(req.getCode())) throw new RuntimeException("Code exists: " + req.getCode());
        return toResponse(departmentRepository.save(Department.builder().name(req.getName()).code(req.getCode().toUpperCase()).description(req.getDescription()).build()));
    }
    public DepartmentDto.Response update(Long id, DepartmentDto.Request req) {
        Department d = findById(id); d.setName(req.getName()); d.setCode(req.getCode().toUpperCase()); d.setDescription(req.getDescription());
        return toResponse(departmentRepository.save(d));
    }
    public void delete(Long id) { departmentRepository.deleteById(id); }
    private Department findById(Long id) { return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Dept not found")); }
    public DepartmentDto.Response toResponse(Department d) {
        DepartmentDto.Response r = new DepartmentDto.Response();
        r.setId(d.getId()); r.setName(d.getName()); r.setCode(d.getCode()); r.setDescription(d.getDescription());
        r.setSubjectCount(subjectRepository.findByDepartmentId(d.getId()).size()); r.setCreatedAt(d.getCreatedAt());
        return r;
    }
}
