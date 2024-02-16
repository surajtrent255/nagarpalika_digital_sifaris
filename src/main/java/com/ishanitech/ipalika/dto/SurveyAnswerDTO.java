package com.ishanitech.ipalika.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyAnswerDTO {
	private String answer;
	private String questionId;
	private String filledId;
	private QuestionType questionType;
}
