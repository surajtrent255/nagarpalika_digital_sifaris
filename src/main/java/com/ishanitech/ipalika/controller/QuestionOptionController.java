package com.ishanitech.ipalika.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.QuestionOptionRequestDTO;
import com.ishanitech.ipalika.service.QuestionOptionsService;

/**
 * {@code QuestionOptionController} handles the request for Question and Options entry.
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
@RestController
@RequestMapping("/question-option")
public class QuestionOptionController {
	private final QuestionOptionsService questionOptionsService;
	
	public QuestionOptionController(QuestionOptionsService questionOptionsService) {
		this.questionOptionsService = questionOptionsService;
	}

	
	/**
	 * Handles the post request coming to {@code QuestionOptionController} on its root url {@link /question-option}
	 * For entry of Question and options
	 * @param response HttpServletResponse object 
	 * @param questionOptionsRequest QuestionOptionRequestDTO object
	 * @return void 
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addQuestionAndOptions(HttpServletResponse response, @RequestBody QuestionOptionRequestDTO questionOptionsRequest) {
		questionOptionsService.addQuestionOptions(questionOptionsRequest);
	}
}