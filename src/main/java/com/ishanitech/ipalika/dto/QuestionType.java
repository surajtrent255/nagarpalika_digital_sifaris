package com.ishanitech.ipalika.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
	TEXT("TEXT"),
	RADIO("RADIO"),
	CHECKBOX("CHECKBOX"),
	NUMBER("NUMBER"),
	RATING("RATING"),
	RATING_M("RATING_M"),
	DATE("DATE"),
	GPS("GPS"),
	IMAGE("IMAGE"),
	MULTI_TEXT("MULTI_TEXT"),
	RADIO_D("RADIO_D"),
	FAMILY_MEMBER("FAMILY_MEMBER"),
	CHECKBOX_N("CHECKBOX_N"),
	DROPDOWN("DROPDOWN"),
	RADIO_M("RADIO_M");
	
	@JsonValue
	private final String questionType;

	private QuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionType() {
		return questionType;
	}
}
