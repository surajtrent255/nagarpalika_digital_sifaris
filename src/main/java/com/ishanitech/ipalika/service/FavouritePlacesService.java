package com.ishanitech.ipalika.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.dto.FavouritePlaceDTO;

public interface FavouritePlacesService {

	public List<FavouritePlaceDTO> getFavouritePlaces(HttpServletRequest request);
	
	public List<FavouritePlaceDTO> getFavouritePlacesBySurveyor(HttpServletRequest request, int userId);

	public void addFavouritePlaceImage(MultipartFile image);

	public void addFavouritePlace(List<FavouritePlaceDTO> favouritePlaceInfo);

	public FavouritePlaceDTO getFavouritePlaceByPlaceId(String placeId);

	public void deleteFavouritePlaceByPlaceId(String placeId);

	public void updateFavouritePlaceByPlaceId(FavouritePlaceDTO favouritePlaceInfo, String placeId);

	public void addSingleFavouritePlace(FavouritePlaceDTO favouritePlaceInfo);

	public List<String> getTypesofFavouritePlaces();

	public void deleteFile(String demoFileName);

	public void updateFavouritePlaceImage(MultipartFile image);

	public List<FavouritePlaceDTO> searchFavouritePlaces(HttpServletRequest request, String searchKey, String wardNo);

	public List<FavouritePlaceDTO> searchWardFavouritePlaces(String wardNo, HttpServletRequest request);

	public List<FavouritePlaceDTO> getFavouritePlaceByPageLimit(HttpServletRequest request);

	public List<FavouritePlaceDTO> getNextLotFavouritePlace(HttpServletRequest request);

	public List<FavouritePlaceDTO> getSortedFavouritePlace(HttpServletRequest request);

	public List<FavouritePlaceDTO> searchPlaceTypeFavouritePlaces(String placeType, HttpServletRequest request);

	public List<FavouritePlaceDTO> searchSurveyorFavouritePlaces(String surveyor, HttpServletRequest request);
}
