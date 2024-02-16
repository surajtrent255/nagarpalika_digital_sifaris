package com.ishanitech.ipalika.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.DeathRecordDTO;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.MemberFormDetailsDTO;
import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.dto.ResidentDetailDTO;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.dto.RoleWardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.Answer;
import com.ishanitech.ipalika.service.ResidentService;
import com.ishanitech.ipalika.service.SurveyAnswerService;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code FormDetailController} is a controller class.
 * @author Umesh Bhujel, Tanchhowpa Singzango, Pujan K.C.
 * @since 1.0
 */
@Slf4j
@RequestMapping("/resident")
@RestController
public class ResidentController {
	private final SurveyAnswerService surveyAnswerService;
	private final ResidentService residentService;
	public ResidentController(SurveyAnswerService surveyAnswerService, ResidentService residentService) {
		this.surveyAnswerService = surveyAnswerService;
		this.residentService = residentService;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/memberFormDetails")
	public ResponseDTO<MemberFormDetailsDTO> getMemberFormDetails() throws CustomSqlException {
		return new ResponseDTO<MemberFormDetailsDTO> (residentService.getMemberFormDetails());
	}
	
	@PostMapping
	public ResponseDTO<List<ResidentDTO>>getResidents(@RequestBody RoleWardDTO roleWardDTO, HttpServletRequest request) {
		log.info("Roles-->" + roleWardDTO);
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.getResident(roleWardDTO, request));
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
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchResident(request, searchkey, wardNo));
	}
	
	@PostMapping("/search/ward")
	public ResponseDTO<List<ResidentDTO>> searchResidentofWard(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchWardResident(wardNo, request));
	}
	
	
	@PostMapping("/search/tole")
	public ResponseDTO<List<ResidentDTO>> searchResidentofTole(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! ward");
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchToleResident(wardNo, request));
	}
	
	@PostMapping("/pageLimit")
	public ResponseDTO<List<ResidentDTO>> getResidentbyPageLimit(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("PageddLImitedd---->" + request.getParameter("pageSize") + " NoWorries! pagelimit");
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.getWardResidentByPageLimit(request));
	}
	
	@PostMapping("/nextLot")
	public ResponseDTO<List<ResidentDTO>> getNextLotResident(@RequestBody RoleWardDTO roleWardDTO, HttpServletRequest request) {
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("Action--->" + request.getParameter("action"));
		log.info("LastSeenId--->" + request.getParameter("last_seen"));
		//return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchWardResident(wardNo, request));
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.getNextLotResident(roleWardDTO, request));	
	}
	
	@PostMapping("/sortBy")
	public ResponseDTO<List<ResidentDTO>> getSortedResident(HttpServletRequest request) {
		log.info("wardyyyzNo--->" + request.getParameter("wardNo"));
		log.info("SearchKey--->" + request.getParameter("searchKey"));
		log.info("PageSize--->" + request.getParameter("pageSize"));
		log.info("SortBy--->" + request.getParameter("sortBy"));
		log.info("SortByOrder--->" + request.getParameter("sortByOrder"));
		//return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.searchWardResident(wardNo, request));
		return new ResponseDTO<List<ResidentDTO>>(surveyAnswerService.getSortedResident(request));	
	}
	
	//returns full information of the resident by its filled id.
	@GetMapping("/detail/{filledId}")
	public ResponseDTO<ResidentDetailDTO> getFullInformationOfResident(@PathVariable("filledId") String filledId) {
		return new ResponseDTO<ResidentDetailDTO>(surveyAnswerService.getAnswerByFilledId(filledId));
	}
	
	//returns information of the resident by its filled id.
	@GetMapping("/detail/rawAnswers/{filledId}")
	public ResponseDTO<Answer> getRawInformationOfResident(@PathVariable("filledId") String filledId) {
		return new ResponseDTO<Answer>(surveyAnswerService.getRawAnswerByFilledId(filledId));
	}
	
	//Adds the family members to a resident
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add-resident-members")
	public void addResidentMembers(HttpServletResponse http, @RequestBody List<FamilyMemberDTO> familyMemberInfo ) throws CustomSqlException {
		residentService.addResidentMembers(familyMemberInfo);
	}
	
	//Add single member to the family
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/member")
	public void addResidentSingle(HttpServletResponse http, @RequestBody FamilyMemberDTO familyMemberInfo) throws CustomSqlException {
		residentService.addResidentSingle(familyMemberInfo);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/family/{familyId}")
	public ResponseDTO<List<FamilyMemberDTO>> getAllFamilyMembersFromFamilyId(@PathVariable("familyId") String familyId) {
		return new ResponseDTO<List<FamilyMemberDTO>>(residentService.getAllFamilyMembersFromFamilyId(familyId));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/member/{memberId}")
	public ResponseDTO<FamilyMemberDTO> getFamilyMemberByMemberId(@PathVariable("memberId") String memberId) {
		return new ResponseDTO<FamilyMemberDTO> (residentService.getMemberDetailsFromMemberId(memberId));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{familyId}")
	public void deleteResidentByFamilyId(@PathVariable("familyId") String familyId) throws CustomSqlException {
		residentService.deleteResidentByFamilyId(familyId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/member-data/{memberId}")
	public ResponseDTO<FamilyMemberDTO> getFamilyMemberRawDataByMemberId(@PathVariable("memberId") String memberId) {
		return new ResponseDTO<FamilyMemberDTO> (residentService.getMemberRawDataFromMemberId(memberId));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/member/{memberId}")
	public void editMemberInfo(HttpServletResponse http, @RequestBody FamilyMemberDTO familyMemberInfo, @PathVariable("memberId") String memberId) throws CustomSqlException {
		residentService.editMemberInfo(familyMemberInfo, memberId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/member/{memberId}")
	public void deleteMemberByMemberId(@PathVariable("memberId") String memberId) throws CustomSqlException {
		residentService.deleteMemberByMemberId(memberId);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/member/dead")
	public void postDeathRecord(@RequestBody DeathRecordDTO deathRecord) throws CustomSqlException {
		residentService.addDeathRecord(deathRecord);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/member/dead/{memberId}")
	public void setFamilyMemberDead(@PathVariable("memberId") String memberId) throws CustomSqlException {
		residentService.setFamilyMemberDead(memberId);
	}

}