package com.ishanitech.ipalika.mapppers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ishanitech.ipalika.dto.LgDistrictDTO;
import com.ishanitech.ipalika.model.LgDistrict;

@Mapper
public interface LgDistrictMapper {
	
	LgDistrictMapper INSTANCE = Mappers.getMapper(LgDistrictMapper.class);
	
	LgDistrictDTO entityToDto(LgDistrict lgDistrict);
	
	List<LgDistrictDTO> entityToDto(List<LgDistrict> lgDistricts);
	
	LgDistrict dtoToEntity(LgDistrictDTO lgDistrict);
	
	List<LgDistrict> dtoToEntity(List<LgDistrictDTO> lgDistrict);
	
}
