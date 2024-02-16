package com.ishanitech.ipalika.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.dto.RoleWardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.service.MemberService;

import lombok.extern.slf4j.Slf4j;


/**
 * {@code MemberController} is a controller class which handles the CRUD operaiton of family members.
 * @author Tanchhowpa Singzango
 * @since 1.0
 */
@RequestMapping("/member")
@RestController
public class MemberController {

	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	/**
	 * Gets list of all members from database
	 * @param request HttpServletRequest object
	 * @param roleWardDTO request object
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping
	public ResponseDTO<List<FamilyMemberDTO>> getMembers(@RequestBody RoleWardDTO roleWardDTO, HttpServletRequest request) throws CustomSqlException {
		return new ResponseDTO<List<FamilyMemberDTO>>(memberService.getMembers(roleWardDTO, request));
	}
	
	
	/**
	 * Gets list of all family members from database using the searchKey
	 * @param request HttpServletRequest object
	 * @param searchKey String searchKey
	 * @param wardNo String wardNo
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping("/search")
	public ResponseDTO<List<FamilyMemberDTO>> searchMember(HttpServletRequest request, @RequestParam("searchKey") String searchKey, @RequestParam("wardNo") String wardNo) throws CustomSqlException {
		String extractedSearchKey = null;
		try {
			extractedSearchKey = URLDecoder.decode(searchKey, "Utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseDTO<List<FamilyMemberDTO>> (memberService.searchMember(request, extractedSearchKey, wardNo));
	}
	
	
	/**
	 * Gets list of all family members from database ward wise
	 * @param request HttpServletRequest object
	 * @param wardNo String wardNo
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping("/search/ward")
	public ResponseDTO<List<FamilyMemberDTO>> searchMemberByWard(HttpServletRequest request, @RequestParam("wardNo") String wardNo) throws CustomSqlException {
		return new ResponseDTO<List<FamilyMemberDTO>> (memberService.searchMemberByWard(request, wardNo));
	}
	
	
	/**
	 * Gets list of all family members from database with page limit
	 * @param request HttpServletRequest object
	 * @param wardNo String wardNo
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping("/pageLimit")
	public ResponseDTO<List<FamilyMemberDTO>> getMemberByPageLimit(HttpServletRequest request, @RequestParam("wardNo") String wardNo) throws CustomSqlException {
		return new ResponseDTO<List<FamilyMemberDTO>> (memberService.getMemberByPageLimit(request));
	}
	
	
	/**
	 * Gets list of all family members from database next page
	 * @param request HttpServletRequest object
	 * @param roleWardDTO RoleWardDTO object
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping("/nextLot")
	public ResponseDTO<List<FamilyMemberDTO>> getNextLotMember(HttpServletRequest request, @RequestBody RoleWardDTO roleWardDTO) throws CustomSqlException {
		return new ResponseDTO<List<FamilyMemberDTO>> (memberService.getNextLotMember(request, roleWardDTO));
	}
	
	
	/**
	 * Gets list of all family members from database sorting
	 * @param request HttpServletRequest object
	 * @return {@code ResponseDTO<List<FamilyMemberDTO>>} object
	 * @throws CustomSqlException
	 * @author <b> Tanchhowpa Singzango </b>
	 * @since 1.0
	 */
	@PostMapping("/sortBy")
	public ResponseDTO<List<FamilyMemberDTO>> getSortedMember(HttpServletRequest request) throws CustomSqlException {
		return new ResponseDTO<List<FamilyMemberDTO>> (memberService.getSortedMember(request));
	}
}