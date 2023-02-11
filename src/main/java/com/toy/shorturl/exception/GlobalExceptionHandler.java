package com.toy.shorturl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.toy.shorturl.exception.type.DuplicateUrlException;
import com.toy.shorturl.exception.type.NoSuchUrlException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateUrlException.class)
	public ErrorMessage DuplicateUrlExceptionHandler(DuplicateUrlException e) {
		return new ErrorMessage(e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchUrlException.class)
	public ErrorMessage NoSuchUrlExceptionHandler(NoSuchUrlException e) {
		return new ErrorMessage(e.getMessage());
	}
}

@Getter
class ErrorMessage {
	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}