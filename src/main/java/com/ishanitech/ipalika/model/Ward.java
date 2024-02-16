package com.ishanitech.ipalika.model;

import lombok.Data;

@Data
public class Ward {
	private int wardNumber;
	private String wardName;
	private String wardLocation;
	private String wardDescription;
	private String mainPerson;
	private String contactNumber;
	
	private String buildingImage;
}
