package com.ishanitech.ipalika.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.AnswerDTO;
import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.model.Answer;

public class AnswerConverter extends BaseConverter<Answer, AnswerDTO> {
	@Override
	public Answer fromDto(AnswerDTO dto) {
		Answer answer = new Answer();
		answer.setFormId(dto.getFormId());
		answer.setFilledId(dto.getFilledId());
		answer.setAddedBy(dto.getAddedBy());
		answer.setDuration(dto.getDuration());
		answer.setEntryDate(dto.getEntryDate());
		answer.setAnswer1(dto.getAnswer1());
		answer.setAnswer2(dto.getAnswer2());
		answer.setAnswer3(dto.getAnswer3());
		answer.setAnswer4(dto.getAnswer4());
		answer.setAnswer5(dto.getAnswer5());
		answer.setAnswer6(dto.getAnswer6());
		answer.setAnswer7(dto.getAnswer7());
		answer.setAnswer8(dto.getAnswer8());
		answer.setAnswer9(dto.getAnswer9());
		answer.setAnswer10(dto.getAnswer10());
		answer.setAnswer11(dto.getAnswer11());
		answer.setAnswer12(dto.getAnswer12());
		answer.setAnswer13(dto.getAnswer13());
		answer.setAnswer14(dto.getAnswer14());
		answer.setAnswer15(dto.getAnswer15());
		answer.setAnswer16(dto.getAnswer16());
		answer.setAnswer17(dto.getAnswer17());
		answer.setAnswer18(dto.getAnswer18());
		answer.setAnswer19(dto.getAnswer19());
		answer.setAnswer20(dto.getAnswer20());
		answer.setAnswer21(dto.getAnswer21());
		answer.setAnswer22(dto.getAnswer22());
		answer.setAnswer23(dto.getAnswer23());
		answer.setAnswer24(dto.getAnswer24());
		answer.setAnswer25(dto.getAnswer25());
		answer.setAnswer26(dto.getAnswer26());
		answer.setAnswer27(dto.getAnswer27());
		answer.setAnswer28(dto.getAnswer28());
		answer.setAnswer29(dto.getAnswer29());
		answer.setAnswer30(dto.getAnswer30());
		answer.setAnswer31(dto.getAnswer31());
		answer.setAnswer32(dto.getAnswer32());
		answer.setAnswer33(dto.getAnswer33());
		answer.setAnswer34(dto.getAnswer34());
		answer.setAnswer35(dto.getAnswer35());
		answer.setAnswer36(dto.getAnswer36());
		answer.setAnswer37(dto.getAnswer37());
		answer.setAnswer38(dto.getAnswer38());
		answer.setAnswer39(dto.getAnswer39());
		answer.setAnswer40(dto.getAnswer40());
		answer.setAnswer41(dto.getAnswer41());
		answer.setAnswer42(dto.getAnswer42());
		answer.setAnswer43(dto.getAnswer43());
		answer.setAnswer44(dto.getAnswer44());
		answer.setAnswer45(dto.getAnswer45());
		answer.setAnswer46(dto.getAnswer46());
		answer.setAnswer47(dto.getAnswer47());
		answer.setAnswer48(dto.getAnswer48());
		answer.setAnswer49(dto.getAnswer49());
		answer.setAnswer50(dto.getAnswer50());
		answer.setAnswer51(dto.getAnswer51());
		answer.setAnswer52(dto.getAnswer52());
		answer.setAnswer53(dto.getAnswer53());
		answer.setAnswer54(dto.getAnswer54());
		answer.setAnswer55(dto.getAnswer55());
		answer.setAnswer56(dto.getAnswer56());
		answer.setAnswer57(dto.getAnswer57());
		answer.setAnswer58(dto.getAnswer58());
		answer.setAnswer59(dto.getAnswer59());
		answer.setAnswer60(dto.getAnswer60());
		answer.setAnswer61(dto.getAnswer61());
		answer.setAnswer62(dto.getAnswer62());
		answer.setAnswer63(dto.getAnswer63());
		answer.setAnswer64(dto.getAnswer64());
		answer.setAnswer65(dto.getAnswer65());
		answer.setAnswer66(dto.getAnswer66());
		answer.setAnswer67(dto.getAnswer67());
		answer.setAnswer68(dto.getAnswer68());
		answer.setAnswer69(dto.getAnswer69());
		answer.setAnswer70(dto.getAnswer70());
		answer.setAnswer71(dto.getAnswer71());
		answer.setAnswer72(dto.getAnswer72());
		answer.setAnswer73(dto.getAnswer73());
		answer.setAnswer74(dto.getAnswer74());
		answer.setAnswer75(dto.getAnswer75());
		answer.setAnswer76(dto.getAnswer76());
		answer.setAnswer77(dto.getAnswer77());
		answer.setAnswer78(dto.getAnswer78());
		answer.setAnswer79(dto.getAnswer79());
		answer.setAnswer80(dto.getAnswer80());
		answer.setAnswer81(dto.getAnswer81());
		answer.setAnswer82(dto.getAnswer82());
		answer.setAnswer83(dto.getAnswer83());
		answer.setAnswer84(dto.getAnswer84());
		answer.setAnswer85(dto.getAnswer85());
		answer.setAnswer86(dto.getAnswer86());
		answer.setAnswer87(dto.getAnswer87());
		answer.setAnswer88(dto.getAnswer88());
		answer.setAnswer89(dto.getAnswer89());
		answer.setAnswer90(dto.getAnswer90());
		answer.setAnswer91(dto.getAnswer91());
		answer.setAnswer92(dto.getAnswer92());
		answer.setAnswer93(dto.getAnswer93());
		answer.setAnswer94(dto.getAnswer94());
		answer.setAnswer95(dto.getAnswer95());
		answer.setAnswer96(dto.getAnswer96());
		answer.setAnswer97(dto.getAnswer97());
		answer.setAnswer98(dto.getAnswer98());
		answer.setAnswer99(dto.getAnswer99());
		answer.setAnswer100(dto.getAnswer100());
		return answer;
	}

	@Override
	public AnswerDTO fromEntity(Answer entity) {
		AnswerDTO answerDTO = new AnswerDTO();
		return answerDTO;
	}

	@Override
	public List<Answer> fromDto(List<AnswerDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}

	@Override
	public List<AnswerDTO> fromEntity(List<Answer> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
	
	public ResidentDTO entityToResident(Answer entity) {
		ResidentDTO residentDto = new ResidentDTO();
		residentDto.setFilledId(entity.getFilledId());
		residentDto.setHouseOwner(entity.getAnswer1()); 	//house owner's name
		residentDto.setTole(entity.getAnswer2()); 			//tole
		residentDto.setHouseNo(entity.getAnswer4()); 		//house number
		residentDto.setKittaNo(entity.getAnswer13()); 		//kitta number
		residentDto.setPhoneNo(entity.getAnswer5()); 		//phone number
		residentDto.setImageUrl(String.format("%s/%s", "http://localhost:8888/resource", entity.getAnswer52())); //image url
		return residentDto;
	}
	
	public List<ResidentDTO> entityListToResidentList(List<Answer> answers) {
		return answers.stream().map(this::entityToResident).collect(Collectors.toList());
	}

}
