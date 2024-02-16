/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 13, 2020
 */
package com.ishanitech.ipalika.dao;

import java.util.List;
import java.util.Map;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.ishanitech.ipalika.dto.ToleDTO;
import com.ishanitech.ipalika.model.Ward;

public interface WardDAO {

	@SqlQuery("SELECT ward_number FROM ward w ORDER BY w.ward_number")
	List<Integer> getAllWardNumbers();
	
	
	@SqlUpdate("INSERT INTO ward ( `ward_number`, "
			+ " `location`, "
			+ " `name`, "
			+ " `ward_description`, "
			+ " `main_person`, "
			+ " `contact_no`, "
			+ " `building_image`) "
			+ " VALUES (:wardNumber, "
			+ " :wardLocation, "
			+ " :wardName, "
			+ " :wardDescription,"
			+ " :mainPerson, "
			+ " :contactNumber, "
			+ " :buildingImage)")
	void addWard(@BindBean Ward ward);
	
	@SqlQuery("SELECT w.ward_number AS wardNumber,"
			+ "w.location AS wardLocation, "
			+ "w.name AS wardName, "
			+ "w.ward_description AS wardDescription, "
			+ "w.main_person AS mainPerson, "
			+ "w.contact_no AS contactNumber,"
			+ "w.building_image AS buildingImage "
			+ "FROM ward w "
			+ "WHERE w.ward_number =:wardNo")
	@RegisterBeanMapper(Ward.class)
	Ward getWardByWardNumber(@Bind("wardNo") int wardNo);
	
	
	@SqlUpdate("UPDATE ward SET "
			+ "ward_number =:wardNumber, "
			+ "location =:wardLocation, "
			+ "name =:wardName, "
			+ "ward_description =:wardDescription, "
			+ "main_person =:mainPerson, "
			+ "contact_no =:contactNumber,"
			+ "building_image =:buildingImage "
			+ "WHERE ward_number =:wardNo")
	void updateWardInfoByWardNumber(@BindBean Ward ward, @Bind("wardNo") int wardNo);


	@SqlUpdate("DELETE FROM ward WHERE ward_number =:wardNo")
	void deleteWardByWardNumber(@Bind("wardNo") int wardNo);

	@SqlQuery("SELECT w.ward_number AS wardNumber,"
			+ "w.location AS wardLocation, "
			+ "w.name AS wardName, "
			+ "w.ward_description AS wardDescription, "
			+ "w.main_person AS mainPerson, "
			+ "w.contact_no AS contactNumber,"
			+ "w.building_image AS buildingImage "
			+ "FROM ward w ")
	@RegisterBeanMapper(Ward.class)
	List<Ward> getAllWardsInfo();


	@SqlQuery("SELECT COUNT(*) FROM answer WHERE answer_3 = :wardNo AND deleted = 0")
	Integer getTotalHouseCountByWard(@Bind("wardNo") int wardNo);


	@SqlQuery("SELECT DISTINCT ans.answer_3 as wardNumber, ans.answer_2 as toleName FROM answer ans WHERE deleted = 0;")
	@RegisterBeanMapper(ToleDTO.class)
	List<ToleDTO> getAllTolesMap();

	@SqlQuery("SELECT COUNT(*) FROM answer WHERE answer_3 = :wardNo AND answer_2 = :toleName AND deleted = 0")
	Integer getTotalHouseCountByWardTole(int wardNo, String toleName);
	
}
