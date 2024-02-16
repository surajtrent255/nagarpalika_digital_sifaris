package com.ishanitech.ipalika.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * {@code ApiError} is a custom error response class which holds the erros. 
 * @author Umesh Bhujel
 * @since 1.0
 */
public class ApiError {
	private Integer status;
	private String message;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime time;
	
	
	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	private ApiError(Builder builder) {
		this.description = builder.description;
		this.message = builder.message;
		this.status = builder.status;
		this.time = builder.time;
	}
	
	public static class Builder {
		private Integer status;
		private String message;
		private String description;
		private LocalDateTime time;
		
		public Builder(Integer status) {
			this.status = status;
		}
		
		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}
		
		public Builder withTime(LocalDateTime time) {
			this.time = time;
			return this;
		}
		
		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public ApiError build() {
			return new ApiError(this);
		}
	}
}
