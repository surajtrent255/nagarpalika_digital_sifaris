package com.ishanitech.ipalika.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.service.PublicService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/public/resident")
@RestController
public class PublicController {
	private final PublicService publicService;
	public PublicController(PublicService publicService) {
		this.publicService = publicService;
	}

	
	@PostMapping
	public ResponseDTO<List<ResidentDTO>>getResidents(HttpServletRequest request) {
//		log.info("Roles-->" + roleWardDTO);
		return new ResponseDTO<List<ResidentDTO>>(publicService.getResident(request));
	}
	
	//Searches resident bases on searchKey. SearchKey = house owner name....
	@PostMapping("/search")
	public ResponseDTO<List<ResidentDTO>> searchResident(HttpServletRequest request, @RequestParam("searchKey") String searchKey, @RequestParam("wardNo") String wardNo) {
		String searchkey = null;
		try {
			searchkey = URLDecoder.decode(searchKey, "Utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseDTO<List<ResidentDTO>>(publicService.searchResident(request, searchkey, wardNo));
	}
	
	@PostMapping("/search/ward")
	public ResponseDTO<List<ResidentDTO>> searchResidentofWard(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<ResidentDTO>>(publicService.searchWardResident(wardNo, request));
	}
	
	
	@PostMapping("/search/tole")
	public ResponseDTO<List<ResidentDTO>> searchResidentofTole(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<ResidentDTO>>(publicService.searchToleResident(wardNo, request));
	}
	
	@PostMapping("/pageLimit")
	public ResponseDTO<List<ResidentDTO>> getResidentbyPageLimit(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! pagelimit");
		return new ResponseDTO<List<ResidentDTO>>(publicService.getWardResidentByPageLimit(request));
	}
	
	@PostMapping("/nextLot")
	public ResponseDTO<List<ResidentDTO>> getNextLotResident(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("Action--->" + request.getParameter("action"));
		log.info("LastSeenId--->" + request.getParameter("last_seen"));
		//return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchWardResident(wardNo, request));
		return new ResponseDTO<List<ResidentDTO>>(publicService.getNextLotResident(wardNo, request));	
	}
	
	@PostMapping("/sortBy")
	public ResponseDTO<List<ResidentDTO>> getSortedResident(HttpServletRequest request) {
		
		log.info("This is reached");
		
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("SortBy--->" + request.getParameter("sortBy"));
		log.info("SortByOrder--->" + request.getParameter("sortByOrder"));
		//return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchWardResident(wardNo, request));
		return new ResponseDTO<List<ResidentDTO>>(publicService.getSortedResident(request));	
	}
	
	

}