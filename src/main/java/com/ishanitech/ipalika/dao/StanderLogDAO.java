package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.dto.StanderLogDTO;
import com.ishanitech.ipalika.dto.StanderLogDTO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface StanderLogDAO {
    @SqlQuery("SELECT * FROM standard_log")
    @RegisterBeanMapper(StanderLogDTO.class)
    List<StanderLogDTO> getStanderLoglist();



    @SqlQuery("SELECT * FROM standard_log WHERE form_id = :formId")
    @RegisterBeanMapper(StanderLogDTO.class)
    List<StanderLogDTO> getStanderLoglistBYFormId(@Bind("formId") int formId);

    @SqlQuery("SELECT * FROM standard_log WHERE token_id = :tokenId")
    @RegisterBeanMapper(StanderLogDTO.class)
    List<StanderLogDTO> getStanderLogByTokenId(@Bind("tokenId") String tokenId);

    @SqlQuery("SELECT * FROM standard_log WHERE status = :status")
    @RegisterBeanMapper(StanderLogDTO.class)
    List<StanderLogDTO> getStanderLoglistBYstatus(@Bind("status") int status);
}