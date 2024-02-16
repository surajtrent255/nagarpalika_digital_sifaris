/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 20, 2019
 */
package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.ishanitech.ipalika.dao.mapper.FormDetailMapper;
import com.ishanitech.ipalika.model.FormDetail;

public interface FormDetailDAO {

	@SqlQuery("SELECT DISTINCT q.id as id, q.question_id AS q_id, q.description AS q_desc, " + 
			"	q.group AS q_grouping, q.required AS q_is_required, " + 
			"	qt.type_name AS q_type " + 
			"	FROM question q" + 
			"	INNER JOIN question_type qt ON q.type_id = qt.type_id " + 
			"	WHERE q.form_id = :formID ORDER BY indx ASC")
	@RegisterRowMapper(FormDetailMapper.class)
	public List<FormDetail> getAllFormDetails(@Bind("formID") Integer formId);
	
}
