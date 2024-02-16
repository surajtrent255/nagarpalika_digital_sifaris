package com.ishanitech.ipalika.service.impl;

import java.util.List;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.dao.DistrictDAO;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.DistrictService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DistrictServiceImpl implements DistrictService {
	DbService dbService;
	
	public DistrictServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}

	@Override
	public List<String> getListofDistricts() {
		DistrictDAO districtDao = dbService.getDao(DistrictDAO.class);
		try {
			List<String> districtList = districtDao.getListofDistricts();
			
			if(districtList.size() > 0) {
				return districtList;
			}
		} catch(UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
		}
		throw new EntityNotFoundException("NO RESULTS!");
	}

}
