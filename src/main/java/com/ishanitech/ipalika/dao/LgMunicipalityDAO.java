package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.model.LgMunicipality;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(LgMunicipality.class)
public interface LgMunicipalityDAO {
    @SqlQuery("SELECT * FROM lg_municipality WHERE disabled = 0")
    List<LgMunicipality> getAllMunicipalities();

    @SqlQuery("SELECT * FROM lg_municipality WHERE district_id = :districtId AND disabled = 0")
    List<LgMunicipality> getMunicipalitiesByDistrictId(@Bind("districtId") int districtId);


    @SqlQuery("SELECT * FROM lg_municipality WHERE municipality_id=:id AND disabled = 0")
    LgMunicipality getMunicipalityById(@Bind int id);
}
