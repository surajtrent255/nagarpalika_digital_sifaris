package com.ishanitech.ipalika.converter.impl;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.LgMunicipalityDTO;
import com.ishanitech.ipalika.model.LgMunicipality;

import java.util.List;
import java.util.stream.Collectors;

public class LgMunicipalityConverter extends BaseConverter<LgMunicipality, LgMunicipalityDTO> {

    @Override
    public LgMunicipality fromDto(LgMunicipalityDTO dto) {
        LgMunicipality municipality = new LgMunicipality();
        municipality.setMunicipalityId(dto.getMunicipalityId());
        municipality.setMunicipalityName(dto.getMunicipalityName());
        municipality.setMunicipalityNameNep(dto.getMunicipalityNameNep());
        municipality.setProvinceId(dto.getProvinceId());
        municipality.setDistrictId(dto.getDistrictId());

        return municipality;
    }

    @Override
    public LgMunicipalityDTO fromEntity(LgMunicipality entity) {
        LgMunicipalityDTO municipalityDTO = new LgMunicipalityDTO();
        municipalityDTO.setMunicipalityId(entity.getMunicipalityId());
        municipalityDTO.setMunicipalityName(entity.getMunicipalityName());
        municipalityDTO.setMunicipalityNameNep(entity.getMunicipalityNameNep());
        municipalityDTO.setProvinceId(entity.getProvinceId());
        municipalityDTO.setDistrictId(entity.getDistrictId());

        return municipalityDTO;
    }

    @Override
    public List<LgMunicipality> fromDto(List<LgMunicipalityDTO> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }


    @Override
    public List<LgMunicipalityDTO> fromEntity(List<LgMunicipality> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}