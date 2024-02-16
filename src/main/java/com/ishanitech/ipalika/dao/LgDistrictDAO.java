package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.model.LgDistrict;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(LgDistrict.class)
public interface LgDistrictDAO {
    @SqlQuery("SELECT * FROM lg_districts WHERE disabled = 0")
    List<LgDistrict> getAllDistricts();


    @SqlQuery("SELECT * FROM lg_districts WHERE province_id = :provinceId AND disabled = 0")
    List<LgDistrict> getDistrictsByProvinceId(@Bind("provinceId") int provinceId);


    @SqlQuery("SELECT * FROM lg_districts WHERE district_id =:id AND disabled = 0")
    LgDistrict getDistrictById(@Bind int id);
}
