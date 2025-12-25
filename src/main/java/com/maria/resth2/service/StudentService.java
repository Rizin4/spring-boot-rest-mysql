package com.maria.resth2.service;

import com.maria.resth2.entity.Student;
import com.maria.resth2.exception.DuplicateResourceException;
import com.maria.resth2.exception.ResourceNotFoundException;
import com.maria.resth2.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    
    //create
    public Student save(Student student) {
    	if(repository.existsByEmail(student.getEmail())) {
    		throw new DuplicateResourceException("Email already exists");
    	}
        return repository.save(student);
    }
    
    //find all
    public List<Student> findAll() {
        return repository.findAll();
    }
    
    //find by id
    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    }
    
    //update by id
    public Student updateById(Long id, Student student) {
    	Student existing = repository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    	
    	existing.setName(student.getName());
    	existing.setCourse(student.getCourse());
    	
    	return repository.save(existing);
    }
    
    //delete by id
    public void delete(Long id) {
    	Student existing = repository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    	repository.delete(existing);
    }    
}

