package com.maria.resth2.controller;

import com.maria.resth2.dto.StudentRequestDTO;
import com.maria.resth2.dto.StudentResponseDTO;
import com.maria.resth2.dto.StudentUpdateDTO;
import com.maria.resth2.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDTO create(@RequestBody StudentRequestDTO dto) {
    	return service.save(dto);
    }

    @GetMapping
    public List<StudentResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id, @RequestBody StudentUpdateDTO dto) {
        return service.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
