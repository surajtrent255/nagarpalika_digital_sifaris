package com.ishanitech.ipalika.converter.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.SurveyAnswerDTO;
import com.ishanitech.ipalika.model.Answer;
import com.ishanitech.ipalika.model.SurveyAnswer;


public class SurveyAnswerConverter extends BaseConverter<SurveyAnswer, SurveyAnswerDTO> {

	@Override
	public List<SurveyAnswer> fromDto(List<SurveyAnswerDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}

	@Override
	public List<SurveyAnswerDTO> fromEntity(List<SurveyAnswer> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public SurveyAnswer fromDto(SurveyAnswerDTO dto) {
		SurveyAnswer surveyAnswer = new SurveyAnswer();
		surveyAnswer.setQuestionId(dto.getQuestionId());
		surveyAnswer.setAnswerText(dto.getAnswer());
		surveyAnswer.setFilledId(dto.getFilledId());
		return surveyAnswer;
	}

	@Override
	public SurveyAnswerDTO fromEntity(SurveyAnswer entity) {
		SurveyAnswerDTO surveyAnswerDTO = new SurveyAnswerDTO();
		return surveyAnswerDTO;
	}
	
	//converts the survey answer to answer model
	public List<Answer> fromSuveyAnswersToAnswersList(List<SurveyAnswer> surveyAnswers) {
		List<List<SurveyAnswer>> survAnswers = surveyAnswers.stream().map(ans -> ans.getFilledId()).distinct()
			.map(filledId -> surveyAnswers.stream().filter(sas -> sas.getFilledId().equals(filledId)).collect(Collectors.toList()))
			.collect(Collectors.toList());
		List<Answer> answers = survAnswers.stream().map(ans -> {
			Answer answer = new Answer();
			for(int i = 0; i < ans.size(); i++) {
				try {
					answer.setFilledId(ans.get(i).getFilledId());
					Method method = Answer.class.getMethod(String.format("setAnswer%d", i+1), String.class);
					method.invoke(answer, ans.get(i).getAnswerText());
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return answer;
		}).collect(Collectors.toList());
		return answers;
	}
}
