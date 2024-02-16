package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import com.ishanitech.ipalika.model.Option;
import com.ishanitech.ipalika.model.Question;

public interface QuestionOptionsDAO {

	@CreateSqlObject
	QuestionDAO createQuestionDao();
	
	@CreateSqlObject
	OptionDao createOptionDao();
	
	@Transaction
	default void insertQuestionAndOptions(List<Question> questions, int formId) {
		questions.forEach(question -> {
			int questionId = createQuestionDao().addQuestion(question, formId);
			if(question.getOptions() != null && !question.getOptions().isEmpty()) {
				List<Option> options = question.getOptions();
				for(int i = 0; i < options.size(); i++) {
					options.get(i).setOptionId(i + 1);
					options.get(i).setQuestionId(questionId);
				}
				createOptionDao().addOptions(options);
			}
		});
	}
}
