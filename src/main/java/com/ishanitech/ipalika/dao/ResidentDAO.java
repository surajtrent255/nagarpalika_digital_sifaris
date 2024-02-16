package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import com.ishanitech.ipalika.dto.DeathRecordDTO;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.model.FamilyMember;

/**
 * 
 * 	@author Tanchhowpa
 *	@email rev.x17@gmail.com
 *	@since 1.0
 */

public interface ResidentDAO {

	
	@SqlQuery("SELECT member_id FROM family_member")
	List<String> getAllMemberIds();
	
	@UseClasspathSqlLocator
	@SqlBatch("insert_family_members")
	void addFamilyMembers(@BindBean List<FamilyMember> familyMembers);

	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " WHERE fm.family_id = :familyId AND fm.deleted = 0 AND fm.is_dead = 0")
	@RegisterBeanMapper(FamilyMember.class)
	List<FamilyMember> getAllFamilyMembersFromFamilyId(@Bind("familyId") String familyId);
	
	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " WHERE fm.member_id = :memberId ")
	@RegisterBeanMapper(FamilyMember.class)
	FamilyMember getMemberDetailsFromMemberId(@Bind("memberId") String memberId);
	
	@SqlUpdate("INSERT INTO family_member (family_id, full_name, relation_id, age, gender_id, marital_status, qualification_id, occupation, has_voter_id, health_status, member_id, dob_ad, dob_bs, educational_institute, disability) VALUE(:mainId, :name, :relation, :age, :gender, :maritalStatus, :education, :occupation, :voterCard, :healthCondition, :memberId, :dateOfBirthAD, :dateOfBirthBS, :educationalInstitute, :disability)")
	void addFamilyMemberSingle(@BindBean FamilyMember familyMembers);
	
	@SqlQuery("SELECT relation_nepali FROM family_relation")
	List<String> getListofRelation();

	@SqlQuery("SELECT qualification_nep FROM academic_qualification")
	List<String> getListofQualification();

	@SqlQuery("SELECT gender_nepali FROM gender")
	List<String> getListofGender();

	@SqlQuery("SELECT marital_status_nep FROM marital_status")
	List<String> getListofMaritalStatus();
	
	@SqlQuery("SELECT educational_institute_nep FROM educational_institute")
	List<String> getListofEducationalInstitute();
	
	@SqlQuery("SELECT occupation_nep FROM occupation")
	List<String> getListofOccupation();
	
	@SqlQuery("SELECT differently_abled_nep FROM differently_abled")
	List<String> getListofDifferentlyAbled();
	
	@SqlQuery("SELECT health_status_nep FROM health_status")
	List<String> getListofHealthStatus();

	@SqlUpdate("UPDATE family_member SET"
			+ " full_name =:name, "
			+ " relation_id =:relation, "
			+ " age =:age, "
			+ " gender_id =:gender, "
			+ " marital_status =:maritalStatus, "
			+ " qualification_id =:education, "
			+ " occupation =:occupation, "
			+ " has_voter_id =:voterCard, "
			+ " health_status =:healthCondition, "
			+ " educational_institute =:educationalInstitute, "
			+ " disability =:disability, "
			+ " dob_ad =:dateOfBirthAD, "
			+ " dob_bs =:dateOfBirthBS "
			+ " WHERE member_id =:memberId")
	void editFamilyMemberInfo(@BindBean FamilyMember familyMember, @Bind("memberId") String memberId);

	@SqlUpdate("UPDATE family_member fm SET fm.deleted = 1 WHERE fm.member_id =:memberId")
	void deleteMemberByMemberId(@Bind("memberId") String memberId);

	@SqlUpdate("UPDATE family_member SET is_dead = TRUE WHERE member_id = :memberId")
	void setFamilyMemberDead(@Bind("memberId") String memberId);
	
	@SqlUpdate("UPDATE answer a SET a.deleted = 1 WHERE a.filled_id =:familyId")
	void deleteFamilyDataByFamilyId(@Bind("familyId") String familyId);
	
	@SqlUpdate("UPDATE family_member fm SET fm.deleted = 1 WHERE fm.family_id =:familyId")
	void deleteFamilyMembersByFamilyId(@Bind("familyId") String familyId);
	
	@Transaction
	default void deleteResidentByFamilyId(String familyId) {
		deleteFamilyDataByFamilyId(familyId);
		deleteFamilyMembersByFamilyId(familyId);
	}
	
	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fm.relation_id AS relation, "
			+ " fm.age AS age, "
			+ " fm.gender_id AS gender, "
			+ " fm.marital_status AS maritalStatus, "
			+ " fm.qualification_id AS education, "
			+ " fm.occupation AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " fm.health_status AS healthCondition,"
			+ " fm.educational_institute AS educationalInstitute, "
			+ " fm.disability AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " WHERE fm.member_id = :memberId ")
	@RegisterBeanMapper(FamilyMember.class)
	FamilyMember getMemberRawDataFromMemberId(@Bind("memberId") String memberId);

	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " INNER JOIN answer a"
			+ " ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0 <caseQuery>")
	@RegisterBeanMapper(FamilyMemberDTO.class)
	List<FamilyMemberDTO> getMembers(@Define("caseQuery")String caseQuery);

	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " INNER JOIN answer a"
			+ " ON fm.family_id = a.filled_id WHERE fm.full_name LIKE CONCAT('%', :searchKey, '%') AND fm.deleted = 0 AND fm.is_dead = 0 <caseQuery>")
	@RegisterBeanMapper(FamilyMemberDTO.class)
	List<FamilyMemberDTO> searchAllMemberByKey(@Bind("searchKey") String searchKey, @Define("caseQuery") String caseQuery);

	
	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " INNER JOIN answer a"
			+ " ON fm.family_id = a.filled_id WHERE fm.full_name LIKE CONCAT('%', :searchKey, '%') AND a.answer_3 LIKE :wardNo AND fm.deleted = 0 AND fm.is_dead = 0 <caseQuery>")
	@RegisterBeanMapper(FamilyMemberDTO.class)
	List<FamilyMemberDTO> searchMemberByKey(@Bind("searchKey") String searchKey, @Bind("wardNo") String wardNo, @Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " INNER JOIN answer a"
			+ " ON fm.family_id = a.filled_id WHERE a.answer_3 LIKE :wardNo AND fm.deleted = 0 AND fm.is_dead = 0 <caseQuery>")
	@RegisterBeanMapper(FamilyMemberDTO.class)
	List<FamilyMemberDTO> searchMemberByWard(@Bind("wardNo") String wardNo,@Define("caseQuery") String caseQuery);

	@SqlQuery("SELECT fm.id AS id, "
			+ " fm.full_name AS name, "
			+ " fr.relation_nepali AS relation, "
			+ " fm.age AS age, "
			+ " g.gender_nepali AS gender, "
			+ " ms.marital_status_nep AS maritalStatus, "
			+ " aq.qualification_nep AS education, "
			+ " o.occupation_nep AS occupation,"
			+ " fm.has_voter_id AS voterCard,"
			+ " hs.health_status_nep AS healthCondition,"
			+ " ei.educational_institute_nep AS educationalInstitute, "
			+ " da.differently_abled_nep AS disability, "
			+ " fm.member_id AS memberId, "
			+ " fm.dob_ad AS dateOfBirthAD, "
			+ " fm.dob_bs AS dateOfBirthBS, "
			+ " fm.is_dead AS isDead, "
			+ " fm.family_id AS mainId "
			+ " FROM family_member fm "
			+ " INNER JOIN family_relation fr "
			+ " ON fm.relation_id = fr.relation_id "
			+ " INNER JOIN academic_qualification aq "
			+ " ON fm.qualification_id = aq.qualification_id "
			+ " INNER JOIN gender g "
			+ " ON fm.gender_id = g.gender_id"
			+ " INNER JOIN marital_status ms"
			+ " ON fm.marital_status = ms.marital_status_id"
			+ " INNER JOIN occupation o"
			+ " ON fm.occupation = o.id"
			+ " INNER JOIN health_status hs"
			+ " ON fm.health_status = hs.id"
			+ " INNER JOIN educational_institute ei"
			+ " ON fm.educational_institute = ei.id"
			+ " INNER JOIN differently_abled da"
			+ " ON fm.disability = da.id"
			+ " INNER JOIN answer a"
			+ " ON fm.family_id = a.filled_id WHERE a.answer_3 LIKE :wardNo AND fm.deleted = 0 AND fm.is_dead = 0 <caseQuery>")
	@RegisterBeanMapper(FamilyMemberDTO.class)
	List<FamilyMemberDTO> searchAllMemberByWard(@Define("caseQuery") String caseQuery);

	
	@SqlUpdate("INSERT INTO death_record(registration_number, member_id, death_cause, demise_date, place) VALUES (:registrationNumber, :memberId, :deathCause, :demiseDate, :place)")
	void addDeathRecord(@BindBean DeathRecordDTO deathRecord);
	
	@Transaction
	default void markMemberDeadAndAddDeathRecord(DeathRecordDTO deathRecord) {
		System.out.println("MemberID : " + deathRecord.getMemberId());
		setFamilyMemberDead(deathRecord.getMemberId());
		addDeathRecord(deathRecord);
	}
	
}
