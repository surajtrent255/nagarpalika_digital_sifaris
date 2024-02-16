package com.ishanitech.ipalika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSec<T> {
	private T data;
	private int currentNo;
	private int startNo;
	private int endNo;
	private int status;
	private String message;


	public static <T> ResponseSec<T> ok(T data, int currentNo, int startNo, int endNo, int status, String message) {
		ResponseSec<T> response = new ResponseSec<T>();
		response.data = data;
		response.currentNo = currentNo;
		response.startNo = startNo;
		response.endNo = endNo;
		response.status = status;
		response.message = message;
		return response;
	}

	public static <T> ResponseSec<T> ok (T data, int status, String message){
		ResponseSec<T> response = new ResponseSec<T>();
		response.status = status;
		response.data = data;
		response.message = message;
		return response;
	}

	/**
	 *
	 * @param status status in case of fail eg: 404
	 * @param message human understandable message
	 * @param <T> generic type
	 * @return ResponseSec<T>
	 */

	public static <T> ResponseSec<T> fail(int status, String message){
		ResponseSec<T> response = new ResponseSec<T>();
		response.status = status;
		response.message = message;
		return response;
	}
}