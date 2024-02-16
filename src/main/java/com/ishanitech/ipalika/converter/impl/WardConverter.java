package com.ishanitech.ipalika.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.WardDTO;
import com.ishanitech.ipalika.model.Ward;
import com.ishanitech.ipalika.utils.ImageUtilService;

public class WardConverter extends BaseConverter<Ward, WardDTO> {

	private RestBaseProperty restBaseProperty;
	
	
	
	public WardConverter(RestBaseProperty restBaseProperty) {
		super();
		this.restBaseProperty = restBaseProperty;
	}
	
	

	public WardConverter() {
		super();
	}



	@Override
	public Ward fromDto(WardDTO dto) {
		Ward ward = new Ward();
		ward.setWardNumber(dto.getWardNumber());
		ward.setWardLocation(dto.getWardLocation());
		ward.setWardName(dto.getWardName());
		ward.setWardDescription(dto.getWardDescription());
		ward.setMainPerson(dto.getMainPerson());
		ward.setContactNumber(dto.getContactNumber());
		ward.setBuildingImage(dto.getBuildingImage());
		return ward;
	}

	@Override
	public WardDTO fromEntity(Ward entity) {
		WardDTO wardDTO = new WardDTO();
		wardDTO.setWardNumber(entity.getWardNumber());
		wardDTO.setWardLocation(entity.getWardLocation());
		wardDTO.setWardName(entity.getWardName());
		wardDTO.setWardDescription(entity.getWardDescription());
		wardDTO.setMainPerson(entity.getMainPerson());
		wardDTO.setContactNumber(entity.getContactNumber());
		//ImageUtilService.makeFullImageurl(restBaseProperty, entity.getBuildingImage());
		if(restBaseProperty != null)
		wardDTO.setBuildingImage(ImageUtilService.makeFullImageurl(restBaseProperty, entity.getBuildingImage()));
		return wardDTO;
	}
	
	
	@Override
	public List<Ward> fromDto(List<WardDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}
	
	@Override
	public List<WardDTO> fromEntity(List<Ward> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}

}
