package com.ishanitech.ipalika.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.FormDTO;
import com.ishanitech.ipalika.model.Form;

public class FormConverter extends BaseConverter<Form, FormDTO> {

	@Override
	public Form fromDto(FormDTO dto) {
		Form form = new Form();
		form.setFormName(dto.getFormName());
		return form;
	}

	@Override
	public FormDTO fromEntity(Form entity) {
		FormDTO formDTO = new FormDTO();
		formDTO.setId(entity.getId());
		formDTO.setFormId(entity.getFormId());
		formDTO.setFormName(entity.getFormName());
		return formDTO;
	}

	@Override
	public List<Form> fromDto(List<FormDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}

	@Override
	public List<FormDTO> fromEntity(List<Form> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}

}
