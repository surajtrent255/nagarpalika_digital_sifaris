package com.ishanitech.ipalika.converter.impl;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.LgWardDTO;
import com.ishanitech.ipalika.model.LgWard;

import java.util.List;
import java.util.stream.Collectors;

public class LgWardConverter extends BaseConverter<LgWard, LgWardDTO> {

    @Override
    public LgWard fromDto(LgWardDTO dto) {
        LgWard ward = new LgWard();
        ward.setWardId(dto.getWardId());
        ward.setWardDescription(dto.getWardDescription());
        ward.setMunicipalityId(dto.getMunicipalityId());

        return ward;
    }

    @Override
    public LgWardDTO fromEntity(LgWard entity) {
        LgWardDTO wardDTO = new LgWardDTO();
        wardDTO.setWardId(entity.getWardId());
        wardDTO.setWardDescription(entity.getWardDescription());
        wardDTO.setMunicipalityId(entity.getMunicipalityId());

        return wardDTO;
    }

    @Override
    public List<LgWard> fromDto(List<LgWardDTO> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }


    @Override
    public List<LgWardDTO> fromEntity(List<LgWard> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}
