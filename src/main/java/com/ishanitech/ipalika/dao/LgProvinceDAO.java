package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.model.LgProvince;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(LgProvince.class)
public interface LgProvinceDAO {
    @SqlQuery("SELECT * FROM lg_province WHERE disabled = 0")
    List<LgProvince> getAllProvinces();

    @SqlQuery("SELECT * FROM lg_province WHERE province_id=:id AND disabled = 0")
    LgProvince getProvinceById(@Bind int id);


}
