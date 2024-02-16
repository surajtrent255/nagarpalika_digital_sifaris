package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.ishanitech.ipalika.dto.SurveyorDTO;

public interface SurveyorDAO {

	@RegisterBeanMapper(SurveyorDTO.class)
	@SqlQuery("SELECT u.id AS surveyorId, u.full_name AS fullName FROM user u "
			+ " INNER JOIN user_role ur ON u.id = ur.user_id "
			+ " WHERE ur.role_id = 4 AND u.enabled = 1 AND u.locked = 0 AND u.expired = 0")
	List<SurveyorDTO> getAllSurveyors();
	
}
