package com.ishanitech.ipalika.mapppers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ishanitech.ipalika.dto.LgProvinceDTO;
import com.ishanitech.ipalika.model.LgProvince;

@Mapper
public interface LgProvinceMapper {

	LgProvinceMapper INSTANCE = Mappers.getMapper(LgProvinceMapper.class);
	
	LgProvinceDTO entityToDto(LgProvince lgProvince);
	
	List<LgProvinceDTO> entityToDto(List<LgProvince> lgProvinces);
	
}
