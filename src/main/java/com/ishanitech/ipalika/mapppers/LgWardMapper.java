package com.ishanitech.ipalika.mapppers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ishanitech.ipalika.dto.LgWardDTO;
import com.ishanitech.ipalika.model.LgWard;

@Mapper
public interface LgWardMapper {
	
	LgWardMapper iNSTANCE = Mappers.getMapper(LgWardMapper.class);
	
	LgWardDTO entityToDto(LgWard lgWard);
	
	List<LgWardDTO> entityToDto(List<LgWard> lgWard);

}
