package com.toy.shorturl.exception.type;

public class DuplicateUrlException extends RuntimeException {
	public DuplicateUrlException(String message) {
		super(message);
	}
}
