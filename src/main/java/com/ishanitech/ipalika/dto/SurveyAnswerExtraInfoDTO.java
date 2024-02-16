package com.ishanitech.ipalika.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyAnswerExtraInfoDTO {
	private Date date;
	private String duration;
	private String filledFormId;
	private int userId;
}
