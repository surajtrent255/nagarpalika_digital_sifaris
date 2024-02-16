/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 24, 2019
 */
package com.ishanitech.ipalika.converter.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.QuestionDTO;
import com.ishanitech.ipalika.model.Option;
import com.ishanitech.ipalika.model.Question;

public class QuestionConverter extends BaseConverter<Question, QuestionDTO> {

	@Override
	public Question fromDto(QuestionDTO dto) {
		Question question = new Question();
		question.setGroup(dto.getGroup());
		question.setDescription(dto.getQuestion());
		question.setTypeId(dto.getQuestionType());
		question.setRequired(true);
		question.setQuestionId(UUID.randomUUID().toString()); //change this if you want to use your own question id.
		if(dto.getOptions() != null && dto.getOptions().length > 0) {
			List<Option> options = Stream.of(dto.getOptions()).map(option -> {
				Option opt = new Option();
				opt.setOptionText(option);
				return opt;
			}).collect(Collectors.toList());
			
			question.setOptions(options);
		}
		return question;
	}

	@Override
	public QuestionDTO fromEntity(Question entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> fromDto(List<QuestionDTO> dtos) {
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}

	@Override
	public List<QuestionDTO> fromEntity(List<Question> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
}
