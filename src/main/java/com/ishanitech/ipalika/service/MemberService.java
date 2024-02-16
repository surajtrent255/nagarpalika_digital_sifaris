package com.ishanitech.ipalika.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.RoleWardDTO;

public interface MemberService {

	List<FamilyMemberDTO> getMembers(RoleWardDTO roleWardDTO, HttpServletRequest request);

	List<FamilyMemberDTO> searchMember(HttpServletRequest request, String extractedSearchKey, String wardNo);

	List<FamilyMemberDTO> searchMemberByWard(HttpServletRequest request, String wardNo);

	List<FamilyMemberDTO> getMemberByPageLimit(HttpServletRequest request);

	List<FamilyMemberDTO> getNextLotMember(HttpServletRequest request, RoleWardDTO roleWardDTO);

	List<FamilyMemberDTO> getSortedMember(HttpServletRequest request);
	
	
}
