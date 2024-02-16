package com.ishanitech.ipalika.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StanderLogDTO {
    private int formId;
    private String tokenId;
    private int status;
    private String registrationNumber;
    private String status1IN;
    private String status1By;
    private String status2By;
    private String status2In;
    private String status3By;
    private String status3In;
    private String status4By;
    private String status4In;
    private String status5By;
    private String status5In;
}