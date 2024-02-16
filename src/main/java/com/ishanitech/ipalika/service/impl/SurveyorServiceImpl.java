package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.dao.SurveyorDAO;
import com.ishanitech.ipalika.dto.SurveyorDTO;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.SurveyorService;

@Service
public class SurveyorServiceImpl implements SurveyorService {

	private final DbService dbService;
	
	
	
	public SurveyorServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}



	@Override
	public List<SurveyorDTO> getAllSurveyors() {
		List<SurveyorDTO> surveyors = new ArrayList<>();
		
		try {
			surveyors = dbService.getDao(SurveyorDAO.class).getAllSurveyors();
			return surveyors;
		}  catch(JdbiException jex) {
			throw new EntityNotFoundException("Exception : " + jex.getLocalizedMessage());
		}
	}

}
