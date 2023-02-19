package com.toy.shorturl.exception.type;

public class NoSuchUrlException extends RuntimeException{
	public NoSuchUrlException(String message) {
		super(message);
	}
}
