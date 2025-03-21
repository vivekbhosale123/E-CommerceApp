package com.vivek.Vivek.Ecommerce.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleAllException(Exception e,WebRequest request)
	{
		Response responseBuilder = Response.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(e.getMessage())
				.build();
		return new ResponseEntity<Response>(responseBuilder,HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response> handleNotFoundException(NotFoundException ex,WebRequest request)
	{
		Response errorResponse=Response.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCreaditialsException.class)
	public ResponseEntity<Response> handlerCreaditialException(InvalidCreaditialsException ex,WebRequest request)
	{
		Response errorResponse=Response.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
	}
}
