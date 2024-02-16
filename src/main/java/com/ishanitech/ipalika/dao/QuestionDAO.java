package com.ishanitech.ipalika.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.ishanitech.ipalika.model.Question;

public interface QuestionDAO {
	@SqlUpdate("INSERT INTO question(`question_id`, `form_id`, `group`, `description`, `required`, `type_id`) VALUES(:questionId, :formId, :group, :description, :required, :typeId)")
	@GetGeneratedKeys
	public int addQuestion(@BindBean Question question, @Bind("formId") Integer formId);
}
