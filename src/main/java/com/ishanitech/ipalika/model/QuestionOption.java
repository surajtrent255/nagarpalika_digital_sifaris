package com.ishanitech.ipalika.model;

import java.util.ArrayList;
import java.util.List;

import com.ishanitech.ipalika.dto.QuestionType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOption {
	private int questionId;
	private QuestionType questionType;
	private List<Option> options = new ArrayList<>();
}
