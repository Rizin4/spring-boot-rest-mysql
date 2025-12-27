package com.mysql.rest.service;

import org.springframework.stereotype.Service;

import com.mysql.rest.dto.StudentRequestDTO;
import com.mysql.rest.dto.StudentResponseDTO;
import com.mysql.rest.dto.StudentUpdateDTO;
import com.mysql.rest.entity.Student;
import com.mysql.rest.exception.DuplicateResourceException;
import com.mysql.rest.exception.ResourceNotFoundException;
import com.mysql.rest.mapper.StudentMapper;
import com.mysql.rest.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    
    //create
//    public Student save(Student student) {
//    	if(repository.existsByEmail(student.getEmail())) {
//    		throw new DuplicateResourceException("Email already exists");
//    	}
//        return repository.save(student);
//    }
    public StudentResponseDTO save(StudentRequestDTO dto) {
    	Student student = StudentMapper.toEntity(dto);   //DTO -> Entity
    	if(repository.existsByEmail(student.getEmail())) {
    		throw new DuplicateResourceException("Email already exists");
    	}
    	Student savedStudent = repository.save(student); //Save to DB
    	return StudentMapper.toDTO(savedStudent);        //Entity -> Response DTO
    }
    
    //find all
//    public List<Student> findAll() {
//        return repository.findAll();
//    }
    public List<StudentResponseDTO> findAll(){
    	List<Student> students = repository.findAll();
    	return students.stream()
    			.map(s -> StudentMapper.toDTO(s))
    			.collect(Collectors.toList());
    }
    
    //find by id
//    public Student findById(Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
//    }
    public StudentResponseDTO findById(Long id) {
    	Student student = repository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    	return StudentMapper.toDTO(student);
    }
    
    //update by id
//    public Student updateById(Long id, Student student) {
//    	Student existing = repository.findById(id)
//    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
//    	
//    	existing.setName(student.getName());
//    	existing.setCourse(student.getCourse());
//    	
//    	return repository.save(existing);
//    }
    public StudentResponseDTO updateById(Long id, StudentUpdateDTO dto) {
    	Student student = repository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    	student.setName(dto.getName());
    	student.setCourse(dto.getCourse());
    	Student updatedStudent = repository.save(student);
    	return StudentMapper.toDTO(updatedStudent);
    }
    
    //delete by id
    public void delete(Long id) {
    	Student existing = repository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No student found with id " + id));
    	repository.delete(existing);
    }    
}

