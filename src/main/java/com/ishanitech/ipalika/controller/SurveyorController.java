package com.ishanitech.ipalika.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.dto.SurveyorDTO;
import com.ishanitech.ipalika.service.SurveyorService;

@RestController
@RequestMapping("/surveyor")
public class SurveyorController {

	private final SurveyorService surveyorService;

	public SurveyorController(SurveyorService surveyorService) {
		this.surveyorService = surveyorService;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseDTO<List<SurveyorDTO>> getAllSurveyors() {
		return new ResponseDTO<List<SurveyorDTO>>(surveyorService.getAllSurveyors());
	}
}
