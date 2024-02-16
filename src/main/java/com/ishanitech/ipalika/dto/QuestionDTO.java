package com.ishanitech.ipalika.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionDTO implements Serializable {
	private static final long serialVersionUID = 8075901987656685198L;
	private String question;
	private Integer questionType;
	private String group;
	private Integer required;
	private String[] options;
}
