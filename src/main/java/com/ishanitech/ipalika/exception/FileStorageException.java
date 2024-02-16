package com.ishanitech.ipalika.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException {
	private static final long serialVersionUID = 3575790713508748785L;
	private static final HttpStatus status;
	static {
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileStorageException(String message) {
		super(message);
	}
	
	public static HttpStatus getStatus() {
		return status;
	}
}
