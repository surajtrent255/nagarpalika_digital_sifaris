package com.ishanitech.ipalika.model;


import lombok.Data;


@Data
public class FavouritePlace {
	private int id;
	private String favPlaceId;
	private String favPlaceName;
	private String favPlaceDesc;
	private String favPlacePhoto;
	private String favPlaceLocation;
	private String favPlaceWard;
	private String favPlaceType;
	private int addedBy;
	
}
