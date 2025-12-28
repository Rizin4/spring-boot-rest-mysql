package com.mysql.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.mysql.rest.entity.Student;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

	@Autowired
	private StudentRepository repository;

	@Test
	void existsByEmail_shouldReturnTrue_whenEmailExists() {

		// Arrange
		Student student = new Student();
		student.setName("Rizin");
		student.setEmail("rizin@gmail.com");
		student.setCourse("Java");

		repository.save(student);

		// Act
		boolean exists = repository.existsByEmail("rizin@gmail.com");

		// Assert
		assertThat(exists).isTrue();
	}
}
