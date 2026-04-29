package com.loms.controller;
import com.loms.dto.DepartmentDto;
import com.loms.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/admin/departments") @RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping public ResponseEntity<List<DepartmentDto.Response>> getAll() { return ResponseEntity.ok(departmentService.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<DepartmentDto.Response> getById(@PathVariable Long id) { return ResponseEntity.ok(departmentService.getById(id)); }
    @PostMapping public ResponseEntity<DepartmentDto.Response> create(@Valid @RequestBody DepartmentDto.Request req) { return ResponseEntity.ok(departmentService.create(req)); }
    @PutMapping("/{id}") public ResponseEntity<DepartmentDto.Response> update(@PathVariable Long id, @Valid @RequestBody DepartmentDto.Request req) { return ResponseEntity.ok(departmentService.update(id, req)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { departmentService.delete(id); return ResponseEntity.noContent().build(); }
}
