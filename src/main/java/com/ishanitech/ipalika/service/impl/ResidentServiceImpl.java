package com.ishanitech.ipalika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.converter.impl.FamilyMemberConverter;
import com.ishanitech.ipalika.dao.ResidentDAO;
import com.ishanitech.ipalika.dto.DeathRecordDTO;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.MemberFormDetailsDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.model.FamilyMember;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.ResidentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResidentServiceImpl implements ResidentService {
	
	private final ResidentDAO residentDAO;

	public ResidentServiceImpl(DbService dbService) {
		this.residentDAO = dbService.getDao(ResidentDAO.class);
	}

	@Override
	public void addResidentMembers(List<FamilyMemberDTO> familyMemberInfo) {
		List<String> memberIdsInDatabase = residentDAO.getAllMemberIds();
		List<FamilyMember> familyMembers = new FamilyMemberConverter().fromDto(familyMemberInfo)
				.stream()
				.filter(famMember -> !memberIdsInDatabase.contains(famMember.getMemberId()))
				.collect(Collectors.toList());
		
		try {
			residentDAO.addFamilyMembers(familyMembers);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
		
	}

	@Override
	public List<FamilyMemberDTO> getAllFamilyMembersFromFamilyId(String familyId) {
		List<FamilyMemberDTO> familyMembers = new ArrayList<>();
		try {
			List<FamilyMember> familyMemberInfo = residentDAO.getAllFamilyMembersFromFamilyId(familyId);
			familyMembers = new FamilyMemberConverter().fromEntity(familyMemberInfo);
			return familyMembers;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

	@Override
	public FamilyMemberDTO getMemberDetailsFromMemberId(String memberId) {
		FamilyMemberDTO familyMember = new FamilyMemberDTO();
		try {
			FamilyMember familyMemberInfo = residentDAO.getMemberDetailsFromMemberId(memberId);
			familyMember = new FamilyMemberConverter().fromEntity(familyMemberInfo);
			return familyMember;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

	@Override
	public void deleteResidentByFamilyId(String familyId) {
		try {
			residentDAO.deleteResidentByFamilyId(familyId);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
		
	}

	@Override
	public void addResidentSingle(FamilyMemberDTO familyMemberInfo) {
		FamilyMember familyMember = new FamilyMemberConverter().fromDto(familyMemberInfo);
		
		try {
			residentDAO.addFamilyMemberSingle(familyMember);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}


	@Override
	public MemberFormDetailsDTO getMemberFormDetails() {
		MemberFormDetailsDTO memberFormDetails = new MemberFormDetailsDTO();

		try {
			List<String> relationList = residentDAO.getListofRelation();

			if (relationList.size() > 0) {
				memberFormDetails.setRelation(relationList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Relation details!!!");
		}
		
		try {
			List<String> qualificationList = residentDAO.getListofQualification();

			if (qualificationList.size() > 0) {
				memberFormDetails.setEducation(qualificationList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Qualification details!!!");
		}
		
		try {
			List<String> genderList = residentDAO.getListofGender();

			if (genderList.size() > 0) {
				memberFormDetails.setGender(genderList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Gender Details!!!");
		}
		
		try {
			List<String> maritalStatusList = residentDAO.getListofMaritalStatus();

			if (maritalStatusList.size() > 0) {
				memberFormDetails.setMaritalStatus(maritalStatusList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Marital Status details!!!");
		}
		
		try {
			List<String> educationalInstituteList = residentDAO.getListofEducationalInstitute();

			if (educationalInstituteList.size() > 0) {
				memberFormDetails.setEducationalInstitute(educationalInstituteList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Educational Institute details!!!");
		}
		
		try {
			List<String> occupationList = residentDAO.getListofOccupation();

			if (occupationList.size() > 0) {
				memberFormDetails.setOccupation(occupationList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Occupation details!!!");
		}
		
		
		try {
			List<String> differentlyAbledList = residentDAO.getListofDifferentlyAbled();

			if (differentlyAbledList.size() > 0) {
				memberFormDetails.setDifferentlyAbled(differentlyAbledList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Differently Abled details!!!");
		}
		
		try {
			List<String> healthStatusList = residentDAO.getListofHealthStatus();

			if (healthStatusList.size() > 0) {
				memberFormDetails.setHealthStatus(healthStatusList);
			}
		} catch (UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
			throw new EntityNotFoundException("No Health Status details!!!");
		}
		
		return memberFormDetails;
	}

	@Override
	public void editMemberInfo(FamilyMemberDTO familyMemberInfo, String memberId) {
		FamilyMember familyMember = new FamilyMemberConverter().fromDto(familyMemberInfo);
		try {
			residentDAO.editFamilyMemberInfo(familyMember, memberId);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
		
	}

	@Override
	public void deleteMemberByMemberId(String memberId) {
		try {
			residentDAO.deleteMemberByMemberId(memberId);
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
		
		
	}

	@Override
	public void setFamilyMemberDead(String memberId) {
		try {
			residentDAO.setFamilyMemberDead(memberId);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

	@Override
	public FamilyMemberDTO getMemberRawDataFromMemberId(String memberId) {
		FamilyMemberDTO familyMember = new FamilyMemberDTO();
		try {
			FamilyMember familyMemberInfo = residentDAO.getMemberRawDataFromMemberId(memberId);
			familyMember = new FamilyMemberConverter().fromEntity(familyMemberInfo);
			return familyMember;
		} catch (JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

	@Override
	public void addDeathRecord(DeathRecordDTO deathRecord) {
		try {
			residentDAO.markMemberDeadAndAddDeathRecord(deathRecord);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception :" + jex.getLocalizedMessage());
		}
	}

}
