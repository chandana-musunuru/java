package com.demo.bookapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.bookapp.bean.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiResponse> handleBookNotFoundException( BookNotFoundException ex){
		ApiResponse response = new ApiResponse(ex.getMessage(), 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}

}
