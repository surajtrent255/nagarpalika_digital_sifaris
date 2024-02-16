package com.ishanitech.ipalika.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgProvince {
    private int provinceId;
    private String provinceName;
    private String provinceNameNep;
    private boolean disabled;
}