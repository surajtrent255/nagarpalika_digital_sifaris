package com.ishanitech.ipalika.service.impl;

import java.util.List;

import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.converter.impl.QuestionConverter;
import com.ishanitech.ipalika.dao.QuestionOptionsDAO;
import com.ishanitech.ipalika.dto.QuestionOptionRequestDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.Question;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.QuestionOptionsService;

/**
 * {@code QuestionOptionServiceImpl} implementation class of {@code QuestionOptionService}.
 * Does Business logic related to Question and options.
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
@Service
public class QuestionOptionsServiceImpl implements QuestionOptionsService {
	private DbService dbService;
	
	public QuestionOptionsServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}

	/**
	 * Inserts questions and option.
	 * @param questionOptionRequest object
	 * @author <b> Umesh Bhujel
	 * @since 1.0
	 */
	@Override
	public void addQuestionOptions(QuestionOptionRequestDTO  questionOptionDTO) throws CustomSqlException {
		QuestionOptionsDAO questionOptionsDAO = dbService.getDao(QuestionOptionsDAO.class);
		int formId = questionOptionDTO.getFormId();
		QuestionConverter questionConverter = new QuestionConverter();
		List<Question> questions = questionConverter.fromDto(questionOptionDTO.getQuestions());
		try{
			questionOptionsDAO.insertQuestionAndOptions(questions, formId);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Something went wrong while inserting questions and options.");
		}
	}

}
