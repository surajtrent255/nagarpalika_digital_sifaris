package com.ishanitech.ipalika.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.dto.FavouritePlaceDTO;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.exception.FileStorageException;
import com.ishanitech.ipalika.service.FavouritePlacesService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code FavouritePlaceController} is a controller class that handles favourite place.
 * @author Tanchhowpa email: rev.x17@gmail.com Jan 29, 2020 10:16:40 AM
 * @since 1.0
 */
@Slf4j
@RequestMapping("/favourite-place")
@RestController
public class FavouritePlacesController {

	private final FavouritePlacesService favouritePlacesService;

	public FavouritePlacesController(FavouritePlacesService favouritePlacesService) {
		this.favouritePlacesService = favouritePlacesService;
	}

	
	/**
	 * Gets all the list of Favourite Places
	 * @return {@code ResponseDTO<List<FavouritePlaceDTO>>} object
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseDTO<List<FavouritePlaceDTO>> getFavouritePlaces(HttpServletRequest request) {
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.getFavouritePlaces(request));
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/surveyor/{userId}")
	public ResponseDTO<List<FavouritePlaceDTO>> getFavouritePlacesBySurveyor(HttpServletRequest request, @PathVariable("userId") int userId) {
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.getFavouritePlacesBySurveyor(request, userId));
	}
	
	/**
	 * This endpoint expects the list of request object ({@code List<FavouritePlaceDTO>}) which has the same format as
	 * {@code FavouritePlace} model.
	 * @param http HttpServletResponse object 
	 * @param favouritePlaceInfo request object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@ApiOperation("Add Favourite Place")
	public void addFavouritePlace(HttpServletResponse http, @RequestBody List<FavouritePlaceDTO> favouritePlaceInfo)
			throws CustomSqlException {
		log.info("incomming request");
		favouritePlacesService.addFavouritePlace(favouritePlaceInfo);
	}
	
	
	/**
	 * Deletes the favourite place record from table based on placeId
	 * @param placeId String placeId
	 * @throws CustomSqlException if something goes while executing the query.
	 * @return void
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{placeId}")
	public void deleteFavouritePlaceByPlaceId(@PathVariable("placeId") String placeId) throws CustomSqlException {
		favouritePlacesService.deleteFavouritePlaceByPlaceId(placeId);
	}
	
	
	/**
	 * Updates the favourite place details on basis of placeId with single request object ({@code FavouritePlaceDTO}) which has the same format as
	 * {@code FavouritePlace} model.
	 * @param http HttpServletResponse object 
	 * @param favouritePlaceInfo request object
	 * @param placeId String placeId
	 * @return void
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{placeId}")
	public void updateFavouritePlaceByPlaceId(HttpServletResponse http,
			@RequestBody FavouritePlaceDTO favouritePlaceInfo, @PathVariable("placeId") String placeId)
			throws CustomSqlException {
		favouritePlacesService.updateFavouritePlaceByPlaceId(favouritePlaceInfo, placeId);
	}
	
	
	/**
	 * Gets the favourite place from database using given placeId
	 * @param placeId String placeId
	 * @return {@code ResponseDTO<FavouritePlaceDTO>} object
	 * @throws EntityNotFoundException if nothing found with id
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{placeId}")
	public ResponseDTO<FavouritePlaceDTO> getFavouritePlaceByPlaceId(@PathVariable("placeId") String placeId)
			throws EntityNotFoundException {
		return new ResponseDTO<FavouritePlaceDTO>(favouritePlacesService.getFavouritePlaceByPlaceId(placeId));
	}

	
	/**
	 * Uploads the image for favourite place using MultipartFile
	 * @param picture MultipartFile image
	 * @return void
	 * @throws FileStorageException if there is error on storing file
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/image")
	public void uploadImageFavouritePlace(@RequestParam("picture") MultipartFile image) throws FileStorageException {
		favouritePlacesService.addFavouritePlaceImage(image);
	}

	
	/**
	 * Uploads the edited image for favourite place using MultipartFile
	 * @param picture MultipartFile image
	 * @return void
	 * @throws FileStorageException if there is error on storing file
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/editImage")
	public void uploadEditedImageFavouritePlace(@RequestParam("picture") MultipartFile image) throws FileStorageException {
		favouritePlacesService.updateFavouritePlaceImage(image);
	}

	
	/**
	 * Deletes the image file for a favourite place using placeId
	 * @param placeId String placeId
	 * @return void
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/image/{placeId}")
	public void deleteFileDemo(HttpServletResponse http, @PathVariable("placeId") String placeId) throws CustomSqlException {
		favouritePlacesService.deleteFile(placeId);
	}

	
	/**
	 * This endpoint expects a single request object ({@code FavouritePlaceDTO}) which has the same format as
	 * {@code FavouritePlace} model.
	 * @param http HttpServletResponse object 
	 * @param favouritePlaceInfo request object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/single")
	public void addSingleFavouritePlace(HttpServletResponse http, @RequestBody FavouritePlaceDTO favouritePlaceInfo)
			throws CustomSqlException {
		favouritePlacesService.addSingleFavouritePlace(favouritePlaceInfo);
	}

	
	/**
	 * Gets the list of types of favourite places
	 * @return {@code ResponseDTO<List<String>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/type")
	public ResponseDTO<List<String>> getTypesofFavouritePlaces() throws CustomSqlException {
		return new ResponseDTO<List<String>>(favouritePlacesService.getTypesofFavouritePlaces());
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/search")
	public ResponseDTO<List<FavouritePlaceDTO>> getFavouritePlaces(HttpServletRequest request, @RequestParam("searchKey") String searchKey, @RequestParam("wardNo") String wardNo, @RequestParam("placeType") String placeType) {
		String searchkey = null;
		try {
			searchkey = URLDecoder.decode(searchKey, "Utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.searchFavouritePlaces(request, searchkey, wardNo));
	}
	
	@GetMapping("/search/ward")
	public ResponseDTO<List<FavouritePlaceDTO>> searchFavourtiePlaceByWard(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.searchWardFavouritePlaces(wardNo, request));
	}
	
	
	@GetMapping("/pageLimit")
	public ResponseDTO<List<FavouritePlaceDTO>> getResidentbyPageLimit(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! pagelimit");
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.getFavouritePlaceByPageLimit(request));
	}
	
	@GetMapping("/nextLot")
	public ResponseDTO<List<FavouritePlaceDTO>> getNextLotResident(HttpServletRequest request) {
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("Action--->" + request.getParameter("action"));
		log.info("LastSeenId--->" + request.getParameter("last_seen"));
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.getNextLotFavouritePlace(request));	
	}
	
	@GetMapping("/sortBy")
	public ResponseDTO<List<FavouritePlaceDTO>> getSortedResident(HttpServletRequest request) {
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("SortBy--->" + request.getParameter("sortBy"));
		log.info("SortByOrder--->" + request.getParameter("sortByOrder"));
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.getSortedFavouritePlace(request));	
	}
	
	@GetMapping("/search/placeType")
	public ResponseDTO<List<FavouritePlaceDTO>> searchFavourtiePlaceByPlaceType(@RequestParam("placeType") String placeType, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.searchPlaceTypeFavouritePlaces(placeType, request));
	}
	
	@GetMapping("/search/surveyor")
	public ResponseDTO<List<FavouritePlaceDTO>> searchFavourtiePlaceBySurveyor(@RequestParam("surveyor") String surveyor, HttpServletRequest request) {
		log.info("Thsi is reached id " + surveyor);
		return new ResponseDTO<List<FavouritePlaceDTO>>(favouritePlacesService.searchSurveyorFavouritePlaces(surveyor, request));
	}

}