package com.ishanitech.ipalika.dao;

import java.util.Collection;
import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.ishanitech.ipalika.model.Option;

public interface OptionDao {
	
	@SqlBatch("INSERT INTO options(option_id, option_text, question_id) VALUES (:optionId, :optionText, :questionId)")
	public void addOptions(@BindBean Collection<Option> options);
	
	@SqlQuery("SELECT o.option_text FROM options o WHERE o.question_id = :questionID")
	public List<String> getAllOptionByQuestionId(@Bind("questionID") Integer questionId);

}
