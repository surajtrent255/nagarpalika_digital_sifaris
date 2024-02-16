package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface DistrictDAO {

	@SqlQuery("SELECT district_name_nep FROM districts")
	public List<String> getListofDistricts();
	
	
	@SqlQuery("SELECT district_name_nep FROM districts WHERE district_id =:districtId")
	public String getDistrictNameNepaliByDistrictId(@Bind("districtId") Integer districtId);
}
