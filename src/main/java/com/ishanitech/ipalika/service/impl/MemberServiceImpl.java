package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.dao.ResidentDAO;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.PaginationTypeClass;
import com.ishanitech.ipalika.dto.RoleWardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.MemberService;
import com.ishanitech.ipalika.utils.CustomQueryCreator;

import lombok.extern.slf4j.Slf4j;

@Service
public class MemberServiceImpl implements MemberService {

	private final DbService dbService;
	
	
	public MemberServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}


	@Override
	public List<FamilyMemberDTO> getMembers(RoleWardDTO roleWardDTO, HttpServletRequest request) {
		try {
			List<FamilyMemberDTO> members;
			
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
			if(roleWardDTO.getRole() == 3) {
				members = dbService.getDao(ResidentDAO.class).searchMemberByWard(Integer.toString(roleWardDTO.getWardNumber()), caseQuery);
			} else {
				members = dbService.getDao(ResidentDAO.class).getMembers(caseQuery);
			}
			return members;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}


	@Override
	public List<FamilyMemberDTO> searchMember(HttpServletRequest request, String extractedSearchKey, String wardNo) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
		List<FamilyMemberDTO> members;
		if(wardNo.equals("")) {
			members = dbService.getDao(ResidentDAO.class).searchAllMemberByKey(extractedSearchKey, caseQuery);
		} else {
			members = dbService.getDao(ResidentDAO.class).searchMemberByKey(extractedSearchKey, wardNo, caseQuery);
		}
		return members;
	}


	@Override
	public List<FamilyMemberDTO> searchMemberByWard(HttpServletRequest request, String wardNo) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
		List<FamilyMemberDTO> members;
		if(wardNo.equals("")) {
			members = dbService.getDao(ResidentDAO.class).searchAllMemberByWard(caseQuery);
		} else {
			members = dbService.getDao(ResidentDAO.class).searchMemberByWard(wardNo, caseQuery);
		}
		return members;
	}

	@Override
	public List<FamilyMemberDTO> getMemberByPageLimit(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
		List<FamilyMemberDTO> members = dbService.getDao(ResidentDAO.class).getMembers(caseQuery);
		return members;
	}


	@Override
	public List<FamilyMemberDTO> getNextLotMember(HttpServletRequest request, RoleWardDTO roleWardDTO) {
		try {
			List<FamilyMemberDTO> members;
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
			if(roleWardDTO.getRole() == 3) {
				members = dbService.getDao(ResidentDAO.class).searchMemberByWard(Integer.toString(roleWardDTO.getWardNumber()), caseQuery);
			} else {
				members = dbService.getDao(ResidentDAO.class).getMembers(caseQuery);
			}
			
			if(request.getParameter("action").equals("prev")) {
				if(request.getParameter("sortBy") == null) {
					List<FamilyMemberDTO> orderedMembers = reverseList(members);
					members = orderedMembers;
				}
			}
			return members;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}

	public static<T> List<T> reverseList(List<T> list) {
		List<T> reverse = new ArrayList<>(list);
		Collections.reverse(reverse);
		return reverse;
	}
	
	@Override
	public List<FamilyMemberDTO> getSortedMember(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.FAMILY_MEMBERS);
		List<FamilyMemberDTO> members = dbService.getDao(ResidentDAO.class).getMembers(caseQuery);
		return members;
	}
}
