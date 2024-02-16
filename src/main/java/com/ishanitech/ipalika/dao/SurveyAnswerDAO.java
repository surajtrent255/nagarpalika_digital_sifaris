package com.ishanitech.ipalika.dao;

import java.util.List;
import java.util.Map;

import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.model.Answer;
import com.ishanitech.ipalika.model.Option;
import com.ishanitech.ipalika.model.QuestionOption;

/**
 * {@code SurveyAnswerDAO} does database operations for SurveyAnswers.
 * @author <b> Umesh Bhujel
 * @since 1.0
 */

public interface SurveyAnswerDAO {
	
	@SqlQuery("SELECT filled_id FROM answer")
	List<String> getAllFilledIds();
	/**
	 * Adds answers to answer table.
	 * @param answers
	 */
	@UseClasspathSqlLocator
	@SqlBatch("insert_answers")
	public void addAnswers(@BindBean List<Answer> answers);
	
	@Transaction
	default void addAnswerList(List<Answer> answers) {
		addAnswers(answers);
	}
	
	
	@UseClasspathSqlLocator
	@SqlUpdate("update_answer")
	public void updateAnswer(@BindBean Answer answers);
	
	@Transaction
	default void updateSurveyAnswer(Answer answerModel) {
		updateAnswer(answerModel);
	}

	/**
	 * @return
	 */
	@SqlQuery("SELECT id as id, filled_id as filledId, answer_1 AS houseOwner, answer_2 AS tole, answer_4 AS houseNo, answer_5 AS phoneNo, answer_13 AS kittaNo, answer_51 as imageUrl, " + 
	 "(SELECT COUNT(*) FROM family_member fm WHERE fm.family_id = a.filled_id AND fm.is_dead = 0 AND fm.deleted = 0) AS totalFamilyMembers " + 
			" FROM answer a WHERE a.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(ResidentDTO.class)
	List<ResidentDTO> getResidents(@Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT id as id, filled_id as filledId, answer_1 AS houseOwner, answer_2 AS tole, answer_4 AS houseNo, answer_5 AS phoneNo, answer_13 AS kittaNo, answer_51 as imageUrl, " + 
			" (SELECT COUNT(*) FROM family_member fm WHERE fm.family_id = a.filled_id AND fm.is_dead = 0 AND fm.deleted = 0) AS totalFamilyMembers " + 
			" FROM answer a WHERE a.answer_1 LIKE CONCAT('%', :searchKey, '%') AND a.deleted = 0 AND a.answer_3 LIKE :wardNo <caseQuery>")
	@RegisterBeanMapper(ResidentDTO.class)
	List<ResidentDTO> searchResidentByKey(@Bind("searchKey") String searchKey, @Bind("wardNo") String wardNo, @Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT id as id, filled_id as filledId, answer_1 AS houseOwner, answer_2 AS tole, answer_4 AS houseNo, answer_5 AS phoneNo, answer_13 AS kittaNo, answer_51 as imageUrl, " + 
			" (SELECT COUNT(*) FROM family_member fm WHERE fm.family_id = a.filled_id AND fm.is_dead = 0 AND fm.deleted = 0) AS totalFamilyMembers " + 
			" FROM answer a WHERE a.answer_1 LIKE CONCAT('%', :searchKey, '%') AND a.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(ResidentDTO.class)
	List<ResidentDTO> searchAllResidentByKey(@Bind("searchKey") String searchKey, @Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT id as id, filled_id as filledId, answer_1 AS houseOwner, answer_2 AS tole, answer_4 AS houseNo, answer_5 AS phoneNo, answer_13 AS kittaNo, answer_51 as imageUrl, " + 
			" (SELECT COUNT(*) FROM family_member fm WHERE fm.family_id = a.filled_id AND fm.is_dead = 0 AND fm.deleted = 0) AS totalFamilyMembers " + 
			" FROM answer a WHERE a.deleted = 0 <caseQuery>")
	@RegisterBeanMapper(ResidentDTO.class)
	List<ResidentDTO> searchAllResidentByWard(@Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT id as id, filled_id as filledId, answer_1 AS houseOwner, answer_2 AS tole, answer_4 AS houseNo, answer_5 AS phoneNo, answer_13 AS kittaNo, answer_51 as imageUrl, " + 
			" (SELECT COUNT(*) FROM family_member fm WHERE fm.family_id = a.filled_id AND fm.is_dead = 0 AND fm.deleted = 0) AS totalFamilyMembers " + 
			" FROM answer a WHERE a.deleted = 0 AND a.answer_3 LIKE :wardNo <caseQuery>")
	@RegisterBeanMapper(ResidentDTO.class)
	List<ResidentDTO> searchResidentByWard(@Bind("wardNo") String wardNo, @Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT * FROM answer WHERE filled_id = :filledId")
	@RegisterBeanMapper(Answer.class)
	Answer getAnswerByFilledId(@Bind("filledId") String filledId);
	
	@SqlQuery(" SELECT q.id as q_question_id, "+
			" qt.type_name as q_question_type,"+
			" o.option_id as o_option_id, " +
			" o.option_text as o_option_text " + 
			"	FROM question_type qt " + 
			"	INNER JOIN question q " + 
			"	ON qt.type_id = q.type_id " + 
			"	INNER JOIN options o " + 
			"	ON o.question_id = q.id ;")
	
	@RegisterBeanMapper(value = QuestionOption.class, prefix = "q")
	@RegisterBeanMapper(value = Option.class, prefix = "o")
	@UseRowReducer(QuestionOptionReducer.class)
	List<QuestionOption> getAllQuestionWithOptions();
	
	class QuestionOptionReducer implements LinkedHashMapRowReducer<Integer, QuestionOption>  {

		@Override
		public void accumulate(Map<Integer, QuestionOption> container, RowView rowView) {
			QuestionOption questionOption = container.computeIfAbsent(rowView.getColumn("q_question_id", Integer.class), id -> {
				return rowView.getRow(QuestionOption.class);
			});
			if(rowView.getColumn("o_option_text", String.class) != null) {
				Option option = rowView.getRow(Option.class);
				questionOption.getOptions().add(option);
			}
		}
		
	}
	
}
