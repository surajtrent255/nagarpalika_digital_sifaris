package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdbi.v3.core.JdbiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.dao.PublicDAO;
import com.ishanitech.ipalika.dao.SurveyAnswerDAO;
import com.ishanitech.ipalika.dto.PaginationTypeClass;
import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.PublicService;
import com.ishanitech.ipalika.utils.CustomQueryCreator;
import com.ishanitech.ipalika.utils.FileUtilService;
import com.ishanitech.ipalika.utils.ImageUtilService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PublicServiceImpl implements PublicService {
	private final DbService dbService;
	
	@Autowired
	private RestBaseProperty restUrlProperty;
	
	public PublicServiceImpl(DbService dbService, FileUtilService fileUtilService) {
		this.dbService = dbService;
	}

	

	@Override
	public List<ResidentDTO> getResident(HttpServletRequest request) {
		try {
			List<ResidentDTO> residents;
			
			
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
			
			//For other
			residents = dbService.getDao(PublicDAO.class).getResidents(caseQuery);
			
			residents.forEach(resident -> resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl())));
			return residents;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}

	
	@Override
	public List<ResidentDTO> searchResident(HttpServletRequest request, String searchKey, String wardNo) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		
		if(wardNo.equals("")) {
			log.info("All search called");
			residents = dbService.getDao(SurveyAnswerDAO.class).searchAllResidentByKey(searchKey, caseQuery);
		} 

		else {
			log.info("Super admin  called");
			residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByKey(searchKey, wardNo, caseQuery);
		}
			residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}

	@Override
	public List<ResidentDTO> searchWardResident(String wardNo, HttpServletRequest request) {
		
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		if(wardNo.equals("")) {
			residents = dbService.getDao(PublicDAO.class).searchAllResidentByWard(caseQuery);
		}else {
		residents = dbService.getDao(PublicDAO.class).searchResidentByWard(wardNo, caseQuery);
		}
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}
	
	

	@Override
	public List<ResidentDTO> searchToleResident(String wardNo, HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		if(wardNo.equals("")) {
			residents = dbService.getDao(PublicDAO.class).searchAllResidentByWard(caseQuery);
		}else {
		residents = dbService.getDao(PublicDAO.class).searchResidentByWard(wardNo, caseQuery);
		}
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}


	@Override
	public List<ResidentDTO> getNextLotResident(String wardNo, HttpServletRequest request) {
		try {
			List<ResidentDTO> residents;
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
			
			log.info("CaseQueryCentralAdmin--->"+ caseQuery);
			residents = dbService.getDao(PublicDAO.class).getResidents(caseQuery);

			residents.forEach(resident -> resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl())));
			
			if(request.getParameter("action").equals("prev")) {
				
				if(request.getParameter("sortBy") == null) {
				List<ResidentDTO> orderedresidents = reverseList(residents);
				residents = orderedresidents;
				}
			}
			
			return residents;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " +jex.getLocalizedMessage());
		}
	}
	
	  public static<T> List<T> reverseList(List<T> list)
	  {
	    List<T> reverse = new ArrayList<>(list);
	    Collections.reverse(reverse);
	    return reverse;
	  }

	@Override
	public List<ResidentDTO> getWardResidentByPageLimit(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		
		List<ResidentDTO> residents;
		
		residents = dbService.getDao(PublicDAO.class).getResidents(caseQuery);
		
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}

	@Override
	public List<ResidentDTO> getSortedResident(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		
		List<ResidentDTO> residents;
		
		residents = dbService.getDao(PublicDAO.class).getResidents(caseQuery);
		
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}





}
