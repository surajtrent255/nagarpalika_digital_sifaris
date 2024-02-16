package com.ishanitech.ipalika.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationReport {
    private String formId;
    private String formTitle;
    private int unverified;
    private int stamped;
    private int registered;
    private int processed;
    private int verified;
    private int total;

}

