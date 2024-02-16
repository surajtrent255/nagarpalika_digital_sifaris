package com.ishanitech.ipalika.model;

import lombok.Data;

@Data
public class FamilyMember {
	private int id;
	private String memberId;
    private String mainId;			//family Id (Form field id)
    private String name;
    private String occupation;
    private String relation;
    private String education;
    private int age;
    private String gender;
    private String maritalStatus;
    private Boolean voterCard;
    private String healthCondition;
    private String dateOfBirthAD;
    private String dateOfBirthBS;
    
    private String educationalInstitute;
    private String disability;
    
    private Boolean isDead;
}
