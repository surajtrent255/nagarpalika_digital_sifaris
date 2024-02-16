package com.ishanitech.ipalika.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.model.FamilyMember;
import com.ishanitech.ipalika.utils.AgeCalculatorUtil;

public class FamilyMemberConverter extends BaseConverter<FamilyMember, FamilyMemberDTO>{

	@Override
	public FamilyMember fromDto(FamilyMemberDTO dto) {
		FamilyMember familyMember = new FamilyMember();
		
		familyMember.setMemberId(dto.getMemberId());
		familyMember.setMainId(dto.getMainId());
		familyMember.setName(dto.getName());
		familyMember.setOccupation(dto.getOccupation());
		familyMember.setRelation(dto.getRelation());
		familyMember.setEducation(dto.getEducation());
		familyMember.setGender(dto.getGender());
		familyMember.setMaritalStatus(dto.getMaritalStatus());
		if(dto.getVoterCard().equals("0")){
            dto.setVoterCard("false");
        }
        if(dto.getVoterCard().equals("1")) {
            dto.setVoterCard("true");
        }
		familyMember.setVoterCard(Boolean.parseBoolean(dto.getVoterCard()));
		
		if(dto.getHealthCondition().equals("0")) {
			familyMember.setHealthCondition("12");
		} else {
			familyMember.setHealthCondition(dto.getHealthCondition());
		}
		
		
		familyMember.setDateOfBirthAD(dto.getDateOfBirthAD());
		familyMember.setDateOfBirthBS(dto.getDateOfBirthBS());
		familyMember.setIsDead(dto.getIsDead());
		familyMember.setAge(Integer.parseInt(dto.getAge()));
		familyMember.setDisability(dto.getDisability());
		familyMember.setEducationalInstitute(dto.getEducationalInstitute());
		
		return familyMember;
	}

	@Override
	public FamilyMemberDTO fromEntity(FamilyMember entity) {

		FamilyMemberDTO  familyMemberDTO = new FamilyMemberDTO();
		
		familyMemberDTO.setMemberId(entity.getMemberId());
		familyMemberDTO.setMainId(entity.getMainId());
		familyMemberDTO.setName(entity.getName());
		familyMemberDTO.setOccupation(entity.getOccupation());
		familyMemberDTO.setRelation(entity.getRelation());
		familyMemberDTO.setEducation(entity.getEducation());
		familyMemberDTO.setGender(entity.getGender());
		familyMemberDTO.setMaritalStatus(entity.getMaritalStatus());
		familyMemberDTO.setVoterCard(String.valueOf(entity.getVoterCard()));
		familyMemberDTO.setHealthCondition(entity.getHealthCondition());
		familyMemberDTO.setDateOfBirthAD(entity.getDateOfBirthAD());
		familyMemberDTO.setDateOfBirthBS(entity.getDateOfBirthBS());
		familyMemberDTO.setIsDead(entity.getIsDead());
		familyMemberDTO.setAge(AgeCalculatorUtil.calculateAge(entity.getDateOfBirthAD()));
//		familyMemberDTO.setAge(String.valueOf(entity.getAge()));;
		familyMemberDTO.setDisability(entity.getDisability());
		familyMemberDTO.setEducationalInstitute(entity.getEducationalInstitute());
		
		return familyMemberDTO;
	}
	
	@Override
	public List<FamilyMember> fromDto(List<FamilyMemberDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}

	
	@Override
	public List<FamilyMemberDTO> fromEntity(List<FamilyMember> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
	
}
