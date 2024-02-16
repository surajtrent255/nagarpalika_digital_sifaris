package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import com.ishanitech.ipalika.model.FavouritePlace;

/**
 * 
 * @author Tanchhowpa
 *	email: rev.x17@gmail.com
 *	Jan 29, 2020 11:12:41 AM
 */

public interface FavouritePlaceDAO {

	
	@SqlQuery("SELECT fav_place_id FROM favourite_place")
	List<String> getAllFilledIds();
	
	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE deleted = 0")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> getFavouritePlaces();

	@UseClasspathSqlLocator
	@SqlBatch("insert_favourite_places")
	void addFavouritePlaces(@BindBean List<FavouritePlace> favPlaces);
	
	@Transaction
	default void addFavouritePlaceList(List<FavouritePlace> favouritePlaces) {
		addFavouritePlaces(favouritePlaces);
	}

	@SqlQuery("SELECT fp.fav_place_name AS favPlaceName, "
			+ " fp.fav_place_desc AS favPlaceDesc, "
			+ " fp.fav_place_photo AS favPlacePhoto, "
			+ " fp.fav_place_type AS favPlaceType, "
			+ " fp.fav_place_location AS favPlaceLocation, "
			+ " fp.fav_place_ward AS favPlaceWard "
			+ " FROM favourite_place fp "
			+ " WHERE fp.fav_place_id =:favPlaceId ")
	@RegisterBeanMapper(FavouritePlace.class)
	FavouritePlace getFavouritePlaceByPlaceId(@Bind("favPlaceId")  String placeId);

	@SqlUpdate("UPDATE favourite_place SET deleted = 1 WHERE fav_place_id = :placeId")
	void deleteFavouritePlaceByPlaceId(@Bind("placeId") String placeId);
	
	@SqlUpdate("UPDATE favourite_place SET "
			+ " fav_place_name =:favPlaceName, "
			+ " fav_place_desc =:favPlaceDesc, "
			+ " fav_place_photo =:favPlacePhoto, "
			+ " fav_place_type =:favPlaceType, "
			+ " fav_place_location =:favPlaceLocation,"
			+ " fav_place_ward =:favPlaceWard "
			+ " WHERE fav_place_id =:placeId")
	void updateFavouritePlaceByPlaceId(@BindBean FavouritePlace favPlace, @Bind("placeId") String placeId);
	
	@SqlUpdate("INSERT INTO favourite_place (fav_place_id, fav_place_name, fav_place_desc, fav_place_photo, fav_place_location, fav_place_ward, fav_place_type, added_by) VALUE(:favPlaceId, :favPlaceName, :favPlaceDesc, :favPlacePhoto, :favPlaceLocation, :favPlaceWard, :favPlaceType, :addedBy)")
	void addFavouritePlaceSingle(@BindBean FavouritePlace favPlace);

	
	@SqlQuery("SELECT place_type_nep FROM favourite_place_type")
	List<String> getTypesofFavouritePlaces();

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.fav_place_name LIKE CONCAT('%', :searchKey, '%') AND fp.deleted = 0 AND fp.fav_place_ward LIKE :wardNo <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchFavouritePlaceByKey(@Bind("searchKey") String searchKey, @Bind("wardNo") String wardNo, @Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.fav_place_name LIKE CONCAT('%', :searchKey, '%') AND fp.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchAllFavouritePlaceByKey(@Bind("searchKey") String searchKey, @Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchAllFavouritePlaceByWard(@Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 AND fp.fav_place_ward LIKE :wardNo  <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchFavouritePlaceByWard(@Bind("wardNo") String wardNo, @Define("caseQuery") String caseQuery);

	
	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> getFavouritePlaces(@Define("caseQuery") String caseQuery);

	
	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 AND added_by = :userId")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> getFavouritePlacesBySurveyor(@Define("caseQuery") String caseQuery, @Bind("userId") int userId);
	
	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchAllFavouritePlaceByType(@Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 AND fp.fav_place_type LIKE :placeType <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchFavouritePlaceByType(@Bind("placeType") String placeType, @Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT type_id FROM favourite_place_type")
	List<String> getTypeIdofFavouritePlaces();

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchAllFavouritePlaceBySurveyor(@Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT id AS id, fav_place_id AS favPlaceId, fav_place_name AS favPlaceName, fav_place_desc AS favPlaceDesc, fav_place_photo AS favPlacePhoto, fav_place_location AS favPlaceLocation, fav_place_ward AS favPlaceWard, added_by As addedBy FROM favourite_place fp WHERE fp.deleted = 0 AND fp.added_by = :surveyor <caseQuery>")
	@RegisterBeanMapper(FavouritePlace.class)
	List<FavouritePlace> searchFavouritePlaceBySurveyor(@Bind("surveyor") String surveyor, @Define("caseQuery") String caseQuery);
	
}
