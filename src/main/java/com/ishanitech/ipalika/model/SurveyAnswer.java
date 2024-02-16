package com.ishanitech.ipalika.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyAnswer {
	private String questionId;
	private Integer answerId;
	private String answerText;
	private String filledId;
}
