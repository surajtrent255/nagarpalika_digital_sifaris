package com.ishanitech.ipalika.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Option {
	private int id;
	private int optionId;
	private String optionText;
	private int questionId;
}
