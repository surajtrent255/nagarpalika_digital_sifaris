package com.ishanitech.ipalika.dto;

import lombok.Data;

@Data
public class FavouritePlaceTypeDTO {

    private int id;
    private String filledId;
    private String placeName;
    private String placeDescription;
    private String placeImage;
    private String placeWard;
    private String placeGPS;
    private String placeType;
    private Boolean submitStatus;
    private String addedBy;
    private String surveyor;
}
