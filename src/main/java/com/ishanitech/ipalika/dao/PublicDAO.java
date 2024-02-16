package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.ishanitech.ipalika.dto.ResidentDTO;

public interface PublicDAO {
	
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
	
	
}
