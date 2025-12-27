package com.mysql.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysql.rest.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	boolean existsByEmail(String email);
}
