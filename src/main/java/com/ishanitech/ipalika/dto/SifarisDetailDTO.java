package com.ishanitech.ipalika.dto;

import lombok.Data;
@Data
public class SifarisDetailDTO {
    private int fieldId;
    private String fieldName;
    private String fieldType;
    private String fieldAnswer;
    private String formId;
    private int status;
    private boolean required;
    private boolean fillableBySystem;
}
