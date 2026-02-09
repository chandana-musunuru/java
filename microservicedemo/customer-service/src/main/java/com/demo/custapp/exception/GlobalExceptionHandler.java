package com.demo.custapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.custapp.bean.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ApiResponse> handleCustomerNotFoundException( CustomerNotFoundException ex){
		ApiResponse response = new ApiResponse(ex.getMessage(), 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}

}
