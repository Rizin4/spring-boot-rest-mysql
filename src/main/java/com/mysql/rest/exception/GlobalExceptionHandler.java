package com.mysql.rest.exception;

import java.time.LocalDateTime;

//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private ResponseEntity<ErrorResponse> buildResponse(
			HttpStatus status, String message, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(message);
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, status);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(
			ResourceNotFoundException e, HttpServletRequest request){
		return buildResponse(
				HttpStatus.NOT_FOUND,
				e.getMessage(),
				request);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(
			DuplicateResourceException e, HttpServletRequest request){
		return buildResponse(
				HttpStatus.CONFLICT,
				e.getMessage(),
				request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAll(
	        Exception e,
	        HttpServletRequest request) {

	    return buildResponse(
	            HttpStatus.INTERNAL_SERVER_ERROR,
	            "Something went wrong",
	            request
	    );
	}

//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<String> handleNotFound(ResourceNotFoundException e){
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
//	}
//	
//	@ExceptionHandler(DuplicateResourceException.class)
//	public ResponseEntity<String> handleDuplicateEmail(DuplicateResourceException e){
//		return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
//	}
	
}
