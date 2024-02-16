package com.ishanitech.ipalika.exception;


import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@code GlobalExceptionHandler} is a class which handles the exception occured in any controller
 * as the {@code @RestControllerAdvice} annotation is used for this class. If you want to handle 
 * any custom exception then use {@code @ExceptionHandler} annotation along with exception class you
 * want to handle above the method name.
 * @Author Umesh Bhujel
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * This method handle the {@code EntityNotFoundException} exception.
	 * @param ex the custom exception class.
	 * @return {@code ResponseEntity} instance with {@code ApiError} type.
	 * @since 1.0
	 * @Author Umesh Bhujel
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFoundException(EntityNotFoundException ex) {
		ApiError apiError =	new ApiError.Builder(EntityNotFoundException.getStatus().value())
				.withMessage(ex.getMessage())
				.withTime(LocalDateTime.now())
				.withDescription("Wrong input or there are no results to show!")
				.build();
		return buildErrorResponse(apiError, HttpStatus.NOT_FOUND);
				
	}
	
	/**
	 * Handles the sql exception occurred while persisting data into database table.
	 * @param cex custom sql exception object.
	 * @return {@code ResponseEntity} instance with {@code ApiError} type.
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@ExceptionHandler(CustomSqlException.class)
	public ResponseEntity<ApiError> handleCustomSqlException(CustomSqlException cex) {
		ApiError apiError = new ApiError.Builder(cex.getStatus().value())
				.withMessage("Error Occured.")
				.withDescription(cex.getMessage())
				.withTime(LocalDateTime.now())
				.build();
		return buildErrorResponse(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Handles the file storage exception
	 * 
	 */
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ApiError> handleStorageException(FileStorageException stex) {
		ApiError apiError = new ApiError.Builder(FileStorageException.getStatus().value())
				.withMessage("Storage Exception.")
				.withDescription(stex.getMessage())
				.withTime(LocalDateTime.now())
				.build();
		return buildErrorResponse(apiError, FileStorageException.getStatus());
	}
	
	/**
	 * Builds the response entity.
	 * @param <T> type object
	 * @param t error type object
	 * @param status HttpStatus 
	 * @return {@code ResponseEntity} response entity
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	private <T> ResponseEntity<T> buildErrorResponse(T t, HttpStatus status) {
		return new ResponseEntity<>(t, status);
	}
}
