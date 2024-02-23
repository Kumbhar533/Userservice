package com.user.service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

		String info = ex.getMessage();

		ApiResponse response = ApiResponse.builder().message(info).success(true).httpStatus(HttpStatus.BAD_REQUEST)
				.build();

		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);

	}

}
