package com.mysql.rest.controller;

import org.springframework.web.bind.annotation.*;

import com.mysql.rest.dto.StudentRequestDTO;
import com.mysql.rest.dto.StudentResponseDTO;
import com.mysql.rest.dto.StudentUpdateDTO;
import com.mysql.rest.service.StudentService;

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
