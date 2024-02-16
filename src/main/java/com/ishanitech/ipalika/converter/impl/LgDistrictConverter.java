package com.ishanitech.ipalika.converter.impl;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.LgDistrictDTO;
import com.ishanitech.ipalika.model.LgDistrict;

import java.util.List;
import java.util.stream.Collectors;

public class LgDistrictConverter extends BaseConverter<LgDistrict, LgDistrictDTO> {

    @Override
    public LgDistrict fromDto(LgDistrictDTO dto) {
        LgDistrict district = new LgDistrict();
        district.setDistrictId(dto.getDistrictId());
        district.setDistrictName(dto.getDistrictName());
        district.setDistrictNameNep(dto.getDistrictNameNep());
        district.setProvinceId(dto.getProvinceId());

        return district;
    }

    @Override
    public LgDistrictDTO fromEntity(LgDistrict entity) {
        LgDistrictDTO districtDTO = new LgDistrictDTO();
        districtDTO.setDistrictId(entity.getDistrictId());
        districtDTO.setDistrictName(entity.getDistrictName());
        districtDTO.setDistrictNameNep(entity.getDistrictNameNep());
        districtDTO.setProvinceId(entity.getProvinceId());

        return districtDTO;
    }

    @Override
    public List<LgDistrict> fromDto(List<LgDistrictDTO> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }


    @Override
    public List<LgDistrictDTO> fromEntity(List<LgDistrict> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}