package com.ishanitech.ipalika.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOptionRequestDTO implements Serializable {
	private static final long serialVersionUID = -5282551367079861882L;
	private int formId;
	private List<QuestionDTO> questions;
}
