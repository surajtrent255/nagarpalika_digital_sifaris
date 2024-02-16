package com.ishanitech.ipalika.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipalika.dto.ResidentDTO;

public interface PublicService {

	List<ResidentDTO> getResident(HttpServletRequest request);
	List<ResidentDTO> searchResident(HttpServletRequest request, String searchKey, String wardNo);
	List<ResidentDTO> searchWardResident(String wardNo, HttpServletRequest request);
	List<ResidentDTO> getNextLotResident(String wardNo, HttpServletRequest request);
	List<ResidentDTO> getWardResidentByPageLimit(HttpServletRequest request);
	List<ResidentDTO> getSortedResident(HttpServletRequest request);

	List<ResidentDTO> searchToleResident(String wardNo, HttpServletRequest request);
}
