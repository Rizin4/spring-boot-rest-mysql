package com.maria.resth2.service;

import com.maria.resth2.entity.Student;
import com.maria.resth2.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student save(Student student) {
        return repository.save(student);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public String delete(Long id) {
    	return repository.findById(id).map(student -> {
    		repository.delete(student);
    		return "student deleted successfully";
    	}).orElseThrow(() -> new RuntimeException("student not found"));
    	}
        
    }

