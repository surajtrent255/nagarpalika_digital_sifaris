package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.converter.impl.FavouritePlaceConverter;
import com.ishanitech.ipalika.dao.FavouritePlaceDAO;
import com.ishanitech.ipalika.dao.UserDAO;
import com.ishanitech.ipalika.dto.FavouritePlaceDTO;
import com.ishanitech.ipalika.dto.PaginationTypeClass;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.model.FavouritePlace;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.FavouritePlacesService;
import com.ishanitech.ipalika.utils.CustomQueryCreator;
import com.ishanitech.ipalika.utils.FileUtilService;
import com.ishanitech.ipalika.utils.ImageUtilService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FavouritePlacesServiceImpl implements FavouritePlacesService {

	private final DbService dbService;
	private final FileUtilService fileUtilService;
	
	@Autowired
	private RestBaseProperty restUrlProperty;
	
	public FavouritePlacesServiceImpl(DbService dbService, FileUtilService fileUtilService) {
		this.dbService = dbService;
		this.fileUtilService = fileUtilService;
	}



	@Override
	public List<FavouritePlaceDTO> getFavouritePlaces(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).getFavouritePlaces(caseQuery);
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
 			
			return favPlaces;
			
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}

	
	@Override
	public List<FavouritePlaceDTO> getFavouritePlacesBySurveyor(HttpServletRequest request, int userId) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).getFavouritePlacesBySurveyor(caseQuery, userId);
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			return favPlaces;
			
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}
	
	
	@Override
	public void addFavouritePlaceImage(MultipartFile image) {
		fileUtilService.storeFile(image);
	}

	@Override
	public void addFavouritePlace(List<FavouritePlaceDTO> favouritePlaceInfo) {
		List<String> filledIdsInDatabase = dbService.getDao(FavouritePlaceDAO.class).getAllFilledIds();
		List<FavouritePlace> favPlaces = new FavouritePlaceConverter().fromDto(favouritePlaceInfo)
				.stream()
				.filter(favPlace -> !filledIdsInDatabase.contains(favPlace.getFavPlaceId()))
				.collect(Collectors.toList());
		try {
			dbService.getDao(FavouritePlaceDAO.class).addFavouritePlaceList(favPlaces);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
	}



	@Override
	public FavouritePlaceDTO getFavouritePlaceByPlaceId(String placeId) {
		FavouritePlaceDTO favPlace = new FavouritePlaceDTO();
		try {
			FavouritePlace favPlaceInfo = dbService.getDao(FavouritePlaceDAO.class)
					.getFavouritePlaceByPlaceId(placeId);
			favPlaceInfo.setFavPlacePhoto(ImageUtilService.makeFullImageurl(restUrlProperty, favPlaceInfo.getFavPlacePhoto()));
			favPlace = new FavouritePlaceConverter().fromEntity(favPlaceInfo);
			return favPlace;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}



	@Override
	public void deleteFavouritePlaceByPlaceId(String placeId) {
		try {
			FavouritePlaceDAO favouritePlaceDAO = dbService.getDao(FavouritePlaceDAO.class);
			favouritePlaceDAO.deleteFavouritePlaceByPlaceId(placeId);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception : " + jex.getLocalizedMessage());
		}
	}

	@Override
	public void updateFavouritePlaceByPlaceId(FavouritePlaceDTO favouritePlaceInfo, String placeId) {
		
		FavouritePlace favPlace = new FavouritePlaceConverter().fromDto(favouritePlaceInfo);

		try {
			dbService.getDao(FavouritePlaceDAO.class).updateFavouritePlaceByPlaceId(favPlace, placeId);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
	}



	@Override
	public void addSingleFavouritePlace(FavouritePlaceDTO favouritePlaceInfo) {

		FavouritePlace favPlace = new FavouritePlaceConverter().fromDto(favouritePlaceInfo);
		
		try {
			dbService.getDao(FavouritePlaceDAO.class).addFavouritePlaceSingle(favPlace);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
		
	}



	@Override
	public List<String> getTypesofFavouritePlaces() {
		FavouritePlaceDAO favPlaceDAO = dbService.getDao(FavouritePlaceDAO.class);
		try {
			List<String> favPlaceTypeList = favPlaceDAO.getTypesofFavouritePlaces();
			
			if(favPlaceTypeList.size() > 0) {
				return favPlaceTypeList;
			}
		} catch(UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
		}
		throw new EntityNotFoundException("No Results!!!");
	}



	@Override
	public void deleteFile(String demoFileName) {
		fileUtilService.deleteFile(demoFileName);
	}



	@Override
	public void updateFavouritePlaceImage(MultipartFile image) {
		fileUtilService.storeEditedFile(image);
	}



	@Override
	public List<FavouritePlaceDTO> searchFavouritePlaces(HttpServletRequest request, String searchKey, String wardNo) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
		if(wardNo.equals("")) {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchAllFavouritePlaceByKey(searchKey, caseQuery);
		} 

		else {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchFavouritePlaceByKey(searchKey, wardNo, caseQuery);
		}
		
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}



	@Override
	public List<FavouritePlaceDTO> searchWardFavouritePlaces(String wardNo, HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
			if(wardNo.equals("")) {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchAllFavouritePlaceByWard(caseQuery);
			}else {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchFavouritePlaceByWard(wardNo, caseQuery);
			}
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}



	@Override
	public List<FavouritePlaceDTO> searchPlaceTypeFavouritePlaces(String placeType, HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		log.info(caseQuery.toString());
		try {
			if(placeType.equals("")) {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchAllFavouritePlaceByType(caseQuery);
			}else {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchFavouritePlaceByType(placeType, caseQuery);
			}
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}
	
	

	@Override
	public List<FavouritePlaceDTO> searchSurveyorFavouritePlaces(String surveyor, HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		log.info(caseQuery.toString());
		try {
			if(surveyor.equals("")) {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchAllFavouritePlaceBySurveyor(caseQuery);
			}else {
				favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).searchFavouritePlaceBySurveyor(surveyor, caseQuery);
			}
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}
	
	@Override
	public List<FavouritePlaceDTO> getFavouritePlaceByPageLimit(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).getFavouritePlaces(caseQuery);
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}



	@Override
	public List<FavouritePlaceDTO> getNextLotFavouritePlace(HttpServletRequest request) {
		try {
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
			List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
			List<FavouritePlace> favPlacesInfo;
			
			
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).getFavouritePlaces(caseQuery);
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			if(request.getParameter("action").equals("prev")) {
				
				if(request.getParameter("sortBy") == null) {
				List<FavouritePlaceDTO> orderedfavPlaces = reverseList(favPlaces);
				favPlaces = orderedfavPlaces;
				}
			}
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " +jex.getLocalizedMessage());
		}
	}
	
	  public static<T> List<T> reverseList(List<T> list)
	  {
	    List<T> reverse = new ArrayList<>(list);
	    Collections.reverse(reverse);
	    return reverse;
	  }



	@Override
	public List<FavouritePlaceDTO> getSortedFavouritePlace(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAV_PLACES);
		List<FavouritePlaceDTO> favPlaces = new ArrayList<>();
		List<FavouritePlace> favPlacesInfo;
		try {
			favPlacesInfo = dbService.getDao(FavouritePlaceDAO.class).getFavouritePlaces(caseQuery);
			favPlaces = new FavouritePlaceConverter().fromEntity(favPlacesInfo);
			
			for(FavouritePlaceDTO fp : favPlaces) {
				if(fp.getAddedBy() != 0) {
					fp.setSurveyor(dbService.getDao(UserDAO.class).getUserFullNameById(fp.getAddedBy()));
				} else {
					fp.setSurveyor("");
				}
			}
			
			return favPlaces;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
		
	}

}
