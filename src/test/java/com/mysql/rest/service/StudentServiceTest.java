package com.mysql.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysql.rest.dto.StudentRequestDTO;
import com.mysql.rest.dto.StudentResponseDTO;
import com.mysql.rest.dto.StudentUpdateDTO;
import com.mysql.rest.entity.Student;
import com.mysql.rest.exception.DuplicateResourceException;
import com.mysql.rest.repository.StudentRepository;
import java.util.Optional;
import com.mysql.rest.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentRepository repository;

	@InjectMocks
	private StudentService service;

	@Test
	void save_shouldCreateStudent_whenEmailNotExists() {

		// Arrange (given)
		StudentRequestDTO request = new StudentRequestDTO();
		request.setName("Rizin");
		request.setEmail("rizin@gmail.com");
		request.setCourse("Java");

		Student savedStudent = new Student();
		savedStudent.setId(1L);
		savedStudent.setName("Rizin");
		savedStudent.setEmail("rizin@gmail.com");
		savedStudent.setCourse("Java");

		when(repository.existsByEmail("rizin@gmail.com")).thenReturn(false);
		when(repository.save(any(Student.class))).thenReturn(savedStudent);

		// Act (when)
		StudentResponseDTO response = service.save(request);

		// Assert (then)
		assertNotNull(response);
		assertEquals("Rizin", response.getName());
		assertEquals("Java", response.getCourse());

		verify(repository).existsByEmail("rizin@gmail.com");
		verify(repository).save(any(Student.class));
	}

	@Test
	void save_shouldThrowException_whenEmailAlreadyExists() {

		StudentRequestDTO request = new StudentRequestDTO();
		request.setEmail("dup@gmail.com");

		when(repository.existsByEmail("dup@gmail.com")).thenReturn(true);

		// Act + Assert
		DuplicateResourceException ex = assertThrows(DuplicateResourceException.class, () -> service.save(request));

		assertEquals("Email already exists", ex.getMessage());

		verify(repository, never()).save(any());
	}

	@Test
	void findById_shouldReturnStudent_whenExists() {

		Student student = new Student();
		student.setId(1L);
		student.setName("Rizin");

		when(repository.findById(1L)).thenReturn(Optional.of(student));

		StudentResponseDTO response = service.findById(1L);

		assertEquals("Rizin", response.getName());
	}

	@Test
	void findById_shouldThrowException_whenNotExists() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
	}

	@Test
	void delete_shouldDeleteStudent_whenExists() {

		Student student = new Student();
		student.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(student));

		service.delete(1L);

		verify(repository).delete(student);
	}

	@Test
	void updateById_shouldUpdateStudent_whenExists() {

		// Arrange
		Long id = 1L;

		Student existingStudent = new Student();
		existingStudent.setId(id);
		existingStudent.setName("Old Name");
		existingStudent.setCourse("Old Course");
		existingStudent.setEmail("test@gmail.com"); // should NOT change

		StudentUpdateDTO updateDTO = new StudentUpdateDTO();
		updateDTO.setName("New Name");
		updateDTO.setCourse("New Course");

		when(repository.findById(id)).thenReturn(Optional.of(existingStudent));
		when(repository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		StudentResponseDTO response = service.updateById(id, updateDTO);

		// Assert
		assertNotNull(response);
		assertEquals("New Name", response.getName());
		assertEquals("New Course", response.getCourse());

		verify(repository).findById(id);
		verify(repository).save(existingStudent);
	}
	
	@Test
	void updateById_shouldThrowException_whenStudentNotFound() {

	    Long id = 1L;
	    StudentUpdateDTO dto = new StudentUpdateDTO();
	    dto.setName("Name");
	    dto.setCourse("Course");

	    when(repository.findById(id)).thenReturn(Optional.empty());

	    ResourceNotFoundException ex = assertThrows(
	            ResourceNotFoundException.class,
	            () -> service.updateById(id, dto)
	    );

	    assertTrue(ex.getMessage().contains("No student found"));

	    verify(repository, never()).save(any());
	}

}
