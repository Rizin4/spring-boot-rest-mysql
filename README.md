# Spring Boot CRUD Application (DTO + Global Exception Handling)

A simple Spring Boot REST API that demonstrates **basic CRUD operations** using **DTO pattern** and **centralized exception handling** with `@ControllerAdvice`.

This project is built for learning and understanding **clean backend structure** and **best practices**.

---
## Features

- Create a student
- Fetch all students
- Fetch student by ID
- Update student details
- Delete student
- DTO-based request & response handling
- Centralized exception handling using `GlobalExceptionHandler`
- Clean separation of Controller, Service, DTO, Entity, Repository layers

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL (can be switched to H2 for development)
- Maven

## Project Structure
```
src/main/java
└── com.mysql.rest
├── controller  # REST controllers
├── service     # Business logic
├── repository  # JPA repositories
├── model       # Entity classes
├── dto         # Request & Response DTOs
├── mapper      # DTO ↔ Entity mapping
└── exception   # Custom exceptions & GlobalExceptionHandler
 ```
## Run:

- Import as Existing Maven Project in STS/Eclipse
- Run RestMysqlApplication
  
## Endpoints:

POST   /api/students
GET    /api/students
GET    /api/students/{id}
PUT    /api/students/{id}
DELETE /api/students/{id}
