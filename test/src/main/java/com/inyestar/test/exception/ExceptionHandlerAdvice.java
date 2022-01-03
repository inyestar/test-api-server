package com.inyestar.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	//TODO 에러 메시지 응답 형식 통일 필요
	
	@ExceptionHandler({
		DuplicateException.class,
		NotFoundException.class,
		InvalidArgumentException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> badRequestHandler(RuntimeException e) {
		return ResponseEntity
				.badRequest()
				.body(e.getMessage());
	}
	
	@ExceptionHandler({
		MethodArgumentNotValidException.class,
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> badRequestHandler(MethodArgumentNotValidException e) {
		return ResponseEntity
				.badRequest()
				.body(e.getFieldErrors());
	}
}
