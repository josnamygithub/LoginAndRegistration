package com.example.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity handlerAccessDeniedException(AccessDeniedException e) {
		
		APIResponse apiResponse = new APIResponse();
		apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		apiResponse.setMessage(e.getMessage());
		return ResponseEntity.status(401).body(apiResponse);
	}
}
