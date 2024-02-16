/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 20, 2019
 */
package com.ishanitech.ipalika.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.ishanitech.ipalika.model.FormDetail;

public class FormDetailMapper implements RowMapper<FormDetail>{

	@Override
	public FormDetail map(ResultSet rs, StatementContext ctx) throws SQLException {
		FormDetail formDetail = new FormDetail();
		formDetail.setDesc(rs.getString("q_desc"));
		formDetail.setGrouping(rs.getString("q_grouping"));
		formDetail.setIsRequired(rs.getInt("q_is_required"));
		formDetail.setType(rs.getString("q_type"));
		formDetail.setId(rs.getString("q_id"));
		formDetail.setQuestionId(rs.getInt("id"));
		return formDetail;
	}

}
