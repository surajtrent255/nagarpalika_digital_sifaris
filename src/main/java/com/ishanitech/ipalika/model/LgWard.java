package com.ishanitech.ipalika.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgWard {
    private int wardId;
    private String wardDescription;
    private int municipalityId;
    private boolean disabled;
}
