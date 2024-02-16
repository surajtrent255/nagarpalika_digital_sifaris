package com.ishanitech.ipalika.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO<T, E> {
	private T data;
	private E infoData;
}
