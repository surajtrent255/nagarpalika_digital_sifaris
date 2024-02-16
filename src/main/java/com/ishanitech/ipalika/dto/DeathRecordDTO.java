package com.ishanitech.ipalika.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeathRecordDTO {
	private String memberId;
	private String registrationNumber;
	private String deathCause;
	private String place;
	private Date demiseDate;
}
