package com.ishanitech.ipalika.service.impl;

import com.ishanitech.ipalika.dao.StanderLogDAO;
import com.ishanitech.ipalika.dto.StanderLogDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.StanderLogService;
import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class StanderLogServiceimpl implements StanderLogService {
    private final DbService dbService;

    public StanderLogServiceimpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public List<StanderLogDTO> getStanderLoglist(HttpServletRequest request) {
        List<StanderLogDTO> standerLog;
        try {

            standerLog = dbService.getDao(StanderLogDAO.class).getStanderLoglist();

            return standerLog;

        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception @ getStanderLoglist: " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<StanderLogDTO> getStanderLoglistBYFormId(int formId) {

        List<StanderLogDTO> standerLog;
        try {
            standerLog = dbService.getDao(StanderLogDAO.class).getStanderLoglistBYFormId(formId);
            return standerLog;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception @ getStanderLoglistBYFormId : " + jex.getLocalizedMessage());
        }

    }


    @Override
    public List<StanderLogDTO> getStanderLogByTokenId(String tokenId) {
        List<StanderLogDTO> standerLogInfo ;
        try {
            standerLogInfo = dbService.getDao(StanderLogDAO.class).getStanderLogByTokenId(tokenId);
            return standerLogInfo;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception @ getStanderLoglistBYFormId : " + jex.getLocalizedMessage());
        }
    }
    @Override
    public List<StanderLogDTO> getStanderLoglistBYstatus(int status) {

        List<StanderLogDTO> standerLogstatus;
        try {
            standerLogstatus = dbService.getDao(StanderLogDAO.class).getStanderLoglistBYstatus(status);
            return standerLogstatus;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception @ getStanderLoglistBYFormId : " + jex.getLocalizedMessage());
        }

    }





}