package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.model.LgWard;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
@RegisterBeanMapper(LgWard.class)
public interface LgWardDAO {
    @SqlQuery("SELECT * FROM lg_ward WHERE disabled = 0")
    List<LgWard> getAllWards();

    @SqlQuery("SELECT * FROM lg_ward WHERE municipality_id = :municipalityId AND disabled = 0")
    List<LgWard> getWardsByMunicipalityId(@Bind int municipalityId);

    @SqlQuery("SELECT * FROM lg_ward WHERE ward_id=:id AND disabled = 0")
    LgWard getWardById(@Bind int id);
}
