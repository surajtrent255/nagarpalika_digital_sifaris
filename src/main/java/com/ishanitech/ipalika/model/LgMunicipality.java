package com.ishanitech.ipalika.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LgMunicipality {
    private int municipalityId;
    private String municipalityName;
    private String municipalityNameNep;
    private int provinceId;
    private int districtId;
    private boolean disabled;
}