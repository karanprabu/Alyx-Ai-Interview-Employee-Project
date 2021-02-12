package com.utils;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.controller.EmployeeController;
import com.dto.ServeiceResponseDTO;
 
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice(basePackageClasses = EmployeeController.class)
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public final ResponseEntity<Object> handleAllExceptions(InvalidInputException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ServeiceResponseDTO error = new ServeiceResponseDTO("Server Error", details);
		
		return new ResponseEntity(error,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
	 @ExceptionHandler(RecordNotFoundException.class)
	    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
	        List<String> details = new ArrayList<>();
	        details.add(ex.getLocalizedMessage());
	        ServeiceResponseDTO error = new ServeiceResponseDTO("Record Not Found", details);
	        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	    }
	 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ServeiceResponseDTO error = new ServeiceResponseDTO("Validation Failed", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

}
