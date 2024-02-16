/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 18, 2020
 */
package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.JdbiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.converter.impl.WardConverter;
import com.ishanitech.ipalika.dao.WardDAO;
import com.ishanitech.ipalika.dto.ToleDTO;
import com.ishanitech.ipalika.dto.WardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.Ward;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.servicer.WardService;
import com.ishanitech.ipalika.utils.FileUtilService;

@Service
public class WardServiceImpl implements WardService {
	private final DbService dbService;
	private final FileUtilService fileUtilService;
	
	@Autowired
	private RestBaseProperty restUrlProperty;
	
	public WardServiceImpl(DbService dbService, FileUtilService fileUtilService) {
		this.dbService = dbService;
		this.fileUtilService = fileUtilService;
	}

	@Override
	public List<Integer> getAllWardNumbers() {
		 return dbService.getDao(WardDAO.class).getAllWardNumbers();
	}

	@Override
	public void addWard(WardDTO wardInfo) {
		Ward ward = new WardConverter().fromDto(wardInfo);
		
		try {
			dbService.getDao(WardDAO.class).addWard(ward);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

	@Override
	public void updateWardInfoByWardNumber(WardDTO wardInfo, int wardNo) {
		Ward ward = new WardConverter().fromDto(wardInfo);
		
		try {
			dbService.getDao(WardDAO.class).updateWardInfoByWardNumber(ward, wardNo);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
	}

	@Override
	public void deleteWardByWardNumber(int wardNo) {
		dbService.getDao(WardDAO.class).deleteWardByWardNumber(wardNo);		
	}

	@Override
	public WardDTO getWardByWardNumber(int wardNo) {
		WardDTO ward = new WardDTO();
		
		try {
			Ward wardInfo = dbService.getDao(WardDAO.class)
					.getWardByWardNumber(wardNo);
			ward = new WardConverter(restUrlProperty).fromEntity(wardInfo);
			return ward;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception : " + jex.getLocalizedMessage());
		}
	}

	@Override
	public List<WardDTO> getAllwardsInfo() {
		List<WardDTO> wards = new ArrayList<>();
		
		try {
			List<Ward> wardInfo = dbService.getDao(WardDAO.class).getAllWardsInfo();
			wards = new WardConverter().fromEntity(wardInfo);
			return wards;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}

	@Override
	public Integer getHouseCountByWard(int wardNo, String toleName) {
		
		Integer totalHouseCount;
		System.out.println("ToleName---->" + toleName);
		System.out.println("WardNo---->" + wardNo);
		if(toleName.equals("null") || toleName.equals("")) {
		totalHouseCount = dbService.getDao(WardDAO.class).getTotalHouseCountByWard(wardNo);
		System.out.println("toleName == null---->" + toleName);
		}
		else {
			totalHouseCount = dbService.getDao(WardDAO.class).getTotalHouseCountByWardTole(wardNo, toleName);	
			System.out.println("toleName not null--->" + toleName);
		}
			return totalHouseCount;
	}

	@Override
	public void addWardBuilginImage(MultipartFile image) {
		fileUtilService.storeFile(image);
		
	}

	@Override
	public List<ToleDTO> getAllToles() {
		
		 List<ToleDTO> toles = dbService.getDao(WardDAO.class).getAllTolesMap();
		
		return toles;
	}

}
