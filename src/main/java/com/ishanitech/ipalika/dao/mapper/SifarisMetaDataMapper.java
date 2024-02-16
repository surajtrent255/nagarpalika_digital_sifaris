package com.ishanitech.ipalika.dao.mapper;

import com.ishanitech.ipalika.dao.OnlinSifarisFormDAO;
import com.ishanitech.ipalika.dao.RegistrationReportDAO;
import com.ishanitech.ipalika.model.SifarisLetterMetaData;
import com.ishanitech.ipalika.service.DbService;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SifarisMetaDataMapper implements RowMapper<SifarisLetterMetaData> {

    private final OnlinSifarisFormDAO onlinSifarisFormDAO;
    public SifarisMetaDataMapper(DbService dbService, OnlinSifarisFormDAO onlinSifarisFormDAO){
        this.onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
    }
    @Override
    public SifarisLetterMetaData map(ResultSet rs, StatementContext ctx) throws SQLException {
        SifarisLetterMetaData sifarisLetterMetaData = new SifarisLetterMetaData();
        sifarisLetterMetaData.setElementName(rs.getString("element_placeholder"));

//        sifarisLetterMetaData.setElementValue(onlinSifarisFormDAO.getSifarisMetaDataAnswer("answer_"+rs.getString("element_id")));
//        sifarisLetterMetaData.setDesc(rs.getString("q_desc"));

        return sifarisLetterMetaData;
    }
}
