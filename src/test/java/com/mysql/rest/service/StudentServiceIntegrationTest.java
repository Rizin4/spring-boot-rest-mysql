package com.mysql.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mysql.rest.dto.StudentRequestDTO;
import com.mysql.rest.dto.StudentResponseDTO;
import com.mysql.rest.dto.StudentUpdateDTO;
import com.mysql.rest.exception.DuplicateResourceException;
import com.mysql.rest.exception.ResourceNotFoundException;
import com.mysql.rest.repository.StudentRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentServiceIntegrationTest {

	@Autowired
	private StudentService service;

	@Autowired
	private StudentRepository repository;

	@Test
	void save_shouldPersistStudentInDatabase() {

		// Arrange
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setName("Rizin");
		dto.setEmail("rizin@gmail.com");
		dto.setCourse("Java");

		// Act
		StudentResponseDTO response = service.save(dto);

		// Assert
		assertNotNull(response.getId());
		assertEquals("Rizin", response.getName());

		assertTrue(repository.existsByEmail("rizin@gmail.com"));
	}

	@Test
	void save_shouldThrowException_whenEmailAlreadyExists() {

		StudentRequestDTO dto1 = new StudentRequestDTO();
		dto1.setName("A");
		dto1.setEmail("dup@gmail.com");
		dto1.setCourse("Java");

		StudentRequestDTO dto2 = new StudentRequestDTO();
		dto2.setName("B");
		dto2.setEmail("dup@gmail.com");
		dto2.setCourse("Spring");

		service.save(dto1);

		assertThrows(DuplicateResourceException.class, () -> service.save(dto2));
	}
	
	@Test
	void findAll_shouldReturnAllStudents_whenStudentsExist() {

	    // Arrange
	    StudentRequestDTO dto1 = new StudentRequestDTO();
	    dto1.setName("A");
	    dto1.setEmail("a@gmail.com");
	    dto1.setCourse("Java");

	    StudentRequestDTO dto2 = new StudentRequestDTO();
	    dto2.setName("B");
	    dto2.setEmail("b@gmail.com");
	    dto2.setCourse("Spring");

	    service.save(dto1);
	    service.save(dto2);

	    // Act
	    List<StudentResponseDTO> students = service.findAll();

	    // Assert
	    assertNotNull(students);
	    assertEquals(2, students.size());

	    List<String> names =
	            students.stream().map(StudentResponseDTO::getName).toList();

	    assertTrue(names.contains("A"));
	    assertTrue(names.contains("B"));
	}
	
	@Test
	void updateById_shouldUpdateStudentInDatabase() {

		StudentRequestDTO createDto = new StudentRequestDTO();
		createDto.setName("Old Name");
		createDto.setEmail("update@gmail.com");
		createDto.setCourse("Old Course");

		StudentResponseDTO created = service.save(createDto);

		StudentUpdateDTO updateDto = new StudentUpdateDTO();
		updateDto.setName("New Name");
		updateDto.setCourse("New Course");

		StudentResponseDTO updated = service.updateById(created.getId(), updateDto);

		assertEquals("New Name", updated.getName());
		assertEquals("New Course", updated.getCourse());
	}

	@Test
	void updateById_shouldThrowException_whenStudentNotFound() {

		// Arrange
		StudentUpdateDTO dto = new StudentUpdateDTO();
		dto.setName("New Name");
		dto.setCourse("New Course");

		Long nonExistingId = 999L;

		// Act + Assert
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
				() -> service.updateById(nonExistingId, dto));

		assertTrue(ex.getMessage().contains("No student found"));
	}

	@Test
	void findById_shouldReturnStudent_whenStudentExists() {

		// Arrange
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setName("Rizin");
		dto.setEmail("find@gmail.com");
		dto.setCourse("Java");

		StudentResponseDTO created = service.save(dto);

		// Act
		StudentResponseDTO found = service.findById(created.getId());

		// Assert
		assertNotNull(found);
		assertEquals("Rizin", found.getName());
		assertEquals("Java", found.getCourse());
	}

	@Test
	void findById_shouldThrowException_whenStudentDoesNotExist() {

		Long nonExistingId = 888L;

		// Act + Assert
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
				() -> service.findById(nonExistingId));

		assertTrue(ex.getMessage().contains("No student found"));
	}

	@Test
	void delete_shouldRemoveStudentFromDatabase() {

		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setName("Delete Me");
		dto.setEmail("delete@gmail.com");
		dto.setCourse("Temp");

		StudentResponseDTO created = service.save(dto);

		service.delete(created.getId());

		assertFalse(repository.existsByEmail("delete@gmail.com"));
	}
	
	@Test
	void delete_shouldThrowException_whenStudentNotFound() {

	    // Arrange
	    Long nonExistingId = 777L;

	    // Act + Assert
	    ResourceNotFoundException ex = assertThrows(
	            ResourceNotFoundException.class,
	            () -> service.delete(nonExistingId)
		);

		assertTrue(ex.getMessage().contains("No student found"));
	}
}
