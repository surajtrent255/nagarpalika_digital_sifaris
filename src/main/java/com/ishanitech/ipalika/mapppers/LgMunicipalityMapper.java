package com.ishanitech.ipalika.mapppers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ishanitech.ipalika.dto.LgMunicipalityDTO;
import com.ishanitech.ipalika.model.LgMunicipality;

@Mapper
public interface LgMunicipalityMapper {

	LgMunicipalityMapper iNSTANCE = Mappers.getMapper(LgMunicipalityMapper.class);
	
	LgMunicipalityDTO entityToDto(LgMunicipality lgMunicipality);
	
	List<LgMunicipalityDTO> entityToDto(List<LgMunicipality> lgMunicipalites);

}