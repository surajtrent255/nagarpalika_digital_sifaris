package com.ishanitech.ipalika.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgDistrict {
    private int districtId;
    private String districtName;
    private String districtNameNep;
    private int provinceId;
    private boolean disabled;
}