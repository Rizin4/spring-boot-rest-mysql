package com.mysql.rest.mapper;

import com.mysql.rest.dto.StudentRequestDTO;
import com.mysql.rest.dto.StudentResponseDTO;
import com.mysql.rest.entity.Student;

public class StudentMapper {

	// DTO → Entity
    public static Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setCourse(dto.getCourse());
        return student;
    }

    // Entity → DTO
    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
//        dto.setEmail(student.getEmail());
        dto.setCourse(student.getCourse());
        return dto;
    }
}
