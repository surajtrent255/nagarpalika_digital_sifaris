package com.ishanitech.ipalika.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.dto.*;
import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.ValueColumn;
import org.jdbi.v3.sqlobject.customizer.AllowUnusedBindings;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.ExtraReport;
import com.ishanitech.ipalika.model.FavouritePlaceReport;
import com.ishanitech.ipalika.model.PopulationReport;
import com.ishanitech.ipalika.model.QuestionReport;
import com.ishanitech.ipalika.utils.ReportUtil;

public interface ReportWardDAO {

	@CreateSqlObject
	FavouritePlaceDAO favPlaceDao();

	@SqlQuery("SELECT q.id, qt.type_name FROM question q INNER JOIN question_type qt ON q.type_id = qt.type_id WHERE q.reportable = TRUE")
	@KeyColumn("id")
	@ValueColumn("type_name")
	Map<Integer, String> getAllAnswer();

	@SqlQuery("SELECT <column> FROM answer WHERE deleted = 0 AND answer_3 =:wardNo")
	List<String> getAllAnswerByAnswerId(@Define String column, @Bind("wardNo") int wardNo);

	@SqlQuery("SELECT count(*) AS total FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo")
	double getTotalPopulation(@Bind("wardNo") int wardNo);

	@SqlQuery("SELECT g.gender_id, COUNT(*) AS total " +
			"	FROM family_member fa " +
			"	INNER JOIN gender g " +
			"	ON g.gender_id = fa.gender_id " +
			"	INNER JOIN answer a " +
			"	ON fa.family_id = a.filled_id " +
			"	WHERE fa.deleted = 0 AND fa.is_dead = 0 AND a.answer_3 =:wardNo " +
			"	GROUP BY fa.gender_id " +
			"	ORDER BY g.gender_id ASC;")
	@KeyColumn("gender_id")
	@ValueColumn("total")
	Map<Integer, Double> getPopulationByGenderId(@Bind("wardNo") int wardNo);

	@SqlUpdate("REPLACE INTO population_report(based_on, option_1, option_2, option_3, option_4, option_5, option_6, total, ward) " +
			"	VALUES (CONCAT((\"AgeGroup\"), :wardNo), (SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age < 6 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age BETWEEN 6 AND 16 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age BETWEEN 17 AND 32 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age BETWEEN 33 AND 54 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age BETWEEN 55 AND 65 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON fm.family_id = a.filled_id WHERE age > 65 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.answer_3 =:wardNo AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"   :total, :wardNo)")
	void insertAgeGroupReport(@Bind("total") double total, @Bind("wardNo") int wardNo);

	@SqlQuery("SELECT aq.qualification_id, COUNT(*) AS total FROM family_member fm " +
			"INNER JOIN academic_qualification aq " +
			"ON aq.qualification_id = fm.qualification_id INNER JOIN answer a ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0 AND a.answer_3 =:wardNo GROUP BY aq.qualification_nep ORDER BY aq.qualification_id ASC;")
	@KeyColumn("qualification_id")
	@ValueColumn("total")
	Map<Integer, Double> getPopulationByQualificationId(@Bind("wardNo") int wardNo);

	@SqlBatch("REPLACE INTO question_report (question_id, option_1, option_2, option_3, option_4, option_5, option_6, option_7, option_8, option_9, option_10, option_11, option_12, option_13, option_14, option_15, total, ward) "
			+ " VALUES (CONCAT(:wardNo,:questionId), :option1, :option2, :option3, :option4, :option5, :option6, :option7, :option8, :option9, :option10, :option11, :option12, :option13, :option14, :option15, :total, :wardNo)")
	void insertQuestionReports(@BindBean List<QuestionReport> answerReports, @Bind("wardNo") int wardNo);

	/**
	 * @param populationReports
	 */
	@SqlBatch("REPLACE INTO population_report(based_on, option_1, option_2, option_3, option_4, option_5, option_6, option_7, option_8, option_9, option_10, option_11, option_12, option_13, option_14, option_15, total, ward) "
			+ " VALUES "
			+ " (CONCAT(:basedOn, :wardNo), :option1, :option2, :option3, :option4, :option5, :option6, :option7, :option8, :option9, :option10, :option11, :option12, :option13, :option14, :option15, :total, :wardNo)")
	void insertPopulationReports(@BindBean List<PopulationReport> populationReports, @Bind("wardNo") int wardNo);

	@SqlQuery("SELECT * FROM population_report WHERE ward =:wardNo")
	@RegisterBeanMapper(PopulationReport.class)
	List<PopulationReport> getAllPopulationReports(@Bind("wardNo") int wardNo);

	@SqlQuery("SELECT * FROM question_report WHERE ward =:wardNo")
	@RegisterBeanMapper(QuestionReport.class)
	List<QuestionReport> getAllQuestionReport(@Bind("wardNo") int wardNo);

	@SqlQuery("SELECT * FROM favourite_place_report WHERE ward =:wardNo")
	@RegisterBeanMapper(FavouritePlaceReport.class)
	List<FavouritePlaceReport> getAllFavouritePlaceReports(@Bind("wardNo") int wardNo);

	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_death', :wardNo), (SELECT COUNT(*) FROM death_record dr INNER JOIN family_member fm ON dr.member_id = fm.member_id INNER JOIN answer a ON fm.family_id = a.filled_id WHERE a.answer_3 =:wardNo), :wardNo)")
	void generateDeathRecordCount(@Bind("wardNo") int wardNo);

	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_household', :wardNo), (SELECT COUNT(*) FROM answer WHERE deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalHouseholdCount(@Bind("wardNo") int wardNo);

	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_animal_husbandary_household', :wardNo), (Select COUNT(*) from answer " +
			"WHERE (answer_42 NOT LIKE '%11:1%' AND answer_42 NOT LIKE '') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalAnimalHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_agriculture_household', :wardNo), (SELECT COUNT(*) from answer " +
			"WHERE (answer_80 NOT LIKE '' OR answer_81 NOT LIKE '' OR answer_82 NOT LIKE '' " +
			"OR answer_83 NOT LIKE '' OR answer_84 NOT LIKE '' OR answer_85 NOT LIKE '') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalAgricultureHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_beekeeping_household', :wardNo), (SELECT COUNT(*) from answer " +
			"WHERE (answer_89 NOT LIKE '%1:1%' AND answer_89 NOT LIKE '' AND answer_89 NOT LIKE '1:%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalBeeKeepingHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_agriculture_firm', :wardNo), (SELECT COUNT(*) from answer " +
			"WHERE (answer_75 NOT LIKE '' AND answer_75 NOT LIKE '*' AND answer_75 NOT LIKE '%0%' AND answer_75 NOT LIKE ',' " +
			"AND answer_75 NOT LIKE '%1%' AND answer_75 NOT LIKE 'a' AND answer_75 NOT LIKE '.' AND answer_75 NOT LIKE 'छैन%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalAgricultureFirmCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_foodcrop_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_80 NOT LIKE '' AND answer_80 NOT LIKE '6' AND answer_80 NOT LIKE '%6%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalFoodCropHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_cashcrop_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_81 NOT LIKE '' AND answer_81 NOT LIKE '10' AND answer_81 NOT LIKE '%10%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalCashCropHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_dahacrop_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_82 NOT LIKE '' AND answer_82 NOT LIKE '6' AND answer_82 NOT LIKE '%6%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalDahaCropHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_fruit_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_83 NOT LIKE '' AND answer_83 NOT LIKE '8' AND answer_83 NOT LIKE '%8%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalFruitHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_vegetable_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_84 NOT LIKE '' AND answer_84 NOT LIKE '14' AND answer_84 NOT LIKE '%14%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalVegetableHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO extra_report(report_name, data, ward) VALUE (CONCAT('total_mushroom_household', :wardNo), (SELECT COUNT(*) from answer WHERE (answer_85 NOT LIKE '' AND answer_85 NOT LIKE '5' AND answer_85 NOT LIKE '%5%') AND deleted = 0 AND answer_3 =:wardNo), :wardNo)")
	void generateTotalMushroomHouseholdCount(@Bind("wardNo") int wardNo);


	@SqlQuery("SELECT id AS id, a.answer_1 AS ownerName, a.answer_3 AS wardNo, a.answer_89 AS beeHiveNo, a.answer_90 AS beeSpecies, a.answer_91 AS yearlyProduction from answer a WHERE (a.answer_89 NOT LIKE '%1:1%' AND a.answer_89 NOT LIKE '' AND a.answer_89 NOT LIKE '1:%') AND a.deleted = 0 AND a.answer_3 =:wardNo")
	@RegisterBeanMapper(BeekeepingDTO.class)
	List<BeekeepingDTO> getBeekeepingInfo(@Bind("wardNo") int wardNo);


	@SqlQuery("SELECT id AS id, a.answer_3 AS wardNo, a.answer_75 AS farmName, a.answer_76 AS registration, a.answer_77 AS insurance from answer a WHERE (a.answer_75 NOT LIKE '' AND a.answer_75 NOT LIKE '*' AND a.answer_75 NOT LIKE '%0%' AND a.answer_75 NOT LIKE ',' AND a.answer_75 NOT LIKE '%1%' AND a.answer_75 NOT LIKE 'a' AND a.answer_75 NOT LIKE '०' AND a.answer_75 NOT LIKE '.' AND a.answer_75 NOT LIKE 'छैन%') AND a.deleted = 0 AND a.answer_3 =:wardNo")
	@RegisterBeanMapper(AgriculturalFarmDTO.class)
	List<AgriculturalFarmDTO> getAgriculturalFarmInfo(@Bind("wardNo") int wardNo);


	@SqlUpdate("REPLACE INTO favourite_place_report(place_type, data, ward) "
			+ " VALUES "
			+ " (CONCAT((SELECT place_type_eng from favourite_place_type WHERE type_id =:typeId),:wardNo), (SELECT COUNT(*) FROM favourite_place WHERE fav_place_type =:typeId AND deleted = 0 AND fav_place_ward =:wardNo), :wardNo)")
	void insertFavouritePlaceReports(@Bind("typeId") String placeTypeId, @Bind("wardNo") int wardNo);

	@Transaction
	default void generateReport(int wardNo) {
		Map<Integer, String> answerAndTypes = getAllAnswer();
		double totalPopulation = getTotalPopulation(wardNo);
		Map<Integer, Double> populationByGender = getPopulationByGenderId(wardNo);
		Map<Integer, Double> populationByQualification = getPopulationByQualificationId(wardNo);
		generateDeathRecordCount(wardNo);
		generateTotalHouseholdCount(wardNo);
		generateTotalAnimalHouseholdCount(wardNo);
		generateTotalAgricultureHouseholdCount(wardNo);
		generateTotalBeeKeepingHouseholdCount(wardNo);
		generateTotalAgricultureFirmCount(wardNo);
		generateTotalFoodCropHouseholdCount(wardNo);
		generateTotalCashCropHouseholdCount(wardNo);
		generateTotalDahaCropHouseholdCount(wardNo);
		generateTotalFruitHouseholdCount(wardNo);
		generateTotalVegetableHouseholdCount(wardNo);
		generateTotalMushroomHouseholdCount(wardNo);
		generateFavouritePlaceReport(wardNo);
		List<PopulationReport> populationReports = new ArrayList<>();
		insertAgeGroupReport(totalPopulation, wardNo); //inserts age group report
		PopulationReport genderReport = new PopulationReport();
		genderReport.setBasedOn("Gender");
		genderReport.setOption1(Double.valueOf(0));
		genderReport.setOption2(Double.valueOf(0));
		genderReport.setOption3(Double.valueOf(0));
		genderReport.setOption4(Double.valueOf(0));
		genderReport.setOption5(Double.valueOf(0));
		genderReport.setOption6(Double.valueOf(0));
		genderReport.setOption7(Double.valueOf(0));
		genderReport.setOption8(Double.valueOf(0));
		genderReport.setOption9(Double.valueOf(0));
		genderReport.setOption10(Double.valueOf(0));
		genderReport.setOption11(Double.valueOf(0));
		genderReport.setOption12(Double.valueOf(0));
		genderReport.setOption13(Double.valueOf(0));
		genderReport.setOption14(Double.valueOf(0));
		genderReport.setOption15(Double.valueOf(0));
		genderReport.setTotal(totalPopulation);
		populationByGender.keySet().forEach(key -> {
			try {
				Method method = PopulationReport.class.getMethod(String.format("setOption%d", key), Double.class);
				method.invoke(genderReport, populationByGender.get(key));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
					 InvocationTargetException e) {
				e.printStackTrace();
			}
		});

		PopulationReport qualificationReport = new PopulationReport();
		qualificationReport.setBasedOn("Qualification");
		qualificationReport.setOption1(Double.valueOf(0));
		qualificationReport.setOption2(Double.valueOf(0));
		qualificationReport.setOption3(Double.valueOf(0));
		qualificationReport.setOption4(Double.valueOf(0));
		qualificationReport.setOption5(Double.valueOf(0));
		qualificationReport.setOption6(Double.valueOf(0));
		qualificationReport.setOption7(Double.valueOf(0));
		qualificationReport.setOption8(Double.valueOf(0));
		qualificationReport.setOption9(Double.valueOf(0));
		qualificationReport.setOption10(Double.valueOf(0));
		qualificationReport.setOption11(Double.valueOf(0));
		qualificationReport.setOption12(Double.valueOf(0));
		qualificationReport.setOption13(Double.valueOf(0));
		qualificationReport.setOption14(Double.valueOf(0));
		qualificationReport.setOption15(Double.valueOf(0));
		qualificationReport.setTotal(totalPopulation);
		populationByQualification.keySet().forEach(key -> {
			try {
				Method method = PopulationReport.class.getMethod(String.format("setOption%d", key), Double.class);
				method.invoke(qualificationReport, populationByQualification.get(key));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
					 InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		populationReports.add(genderReport);
		populationReports.add(qualificationReport);
		insertPopulationReports(populationReports, wardNo);

		List<QuestionReport> rawAnswers = answerAndTypes.keySet().stream()
				.map(item -> {
					String table = String.format("answer_%s", item);
					List<String> answer = getAllAnswerByAnswerId(table, wardNo);
					if (!answer.isEmpty()) {
						String[] list = answer.toString().replace("[", "").replace("]", "").split(",");
						QuestionReport report = ReportUtil.generateReport(item, list, QuestionType.valueOf(answerAndTypes.get(item)));
						return report;
					}
					throw new CustomSqlException(String.format("No answers found for question no. %s.", item));
				}).collect(Collectors.toList());
		insertQuestionReports(rawAnswers, wardNo);
	}

	@RegisterBeanMapper(ExtraReport.class)
	@SqlQuery("SELECT report_name, data FROM extra_report WHERE ward =:wardNo")
	List<ExtraReport> getExtraReports(@Bind("wardNo") int wardNo);

	@Transaction
	default void generateFavouritePlaceReport(int wardNo) {
		List<String> favPlaceTypeIds = favPlaceDao().getTypeIdofFavouritePlaces();
		favPlaceTypeIds.stream().forEach((placeTypeId) -> insertFavouritePlaceReports(placeTypeId, wardNo));
	}


	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0  AND  age < 6 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllSisusReport();

	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0 AND age BETWEEN 6 and 16 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllBalBalikaReport();

	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0  and age BETWEEN 17 and 32 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllYuwaReport();

	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0  and age BETWEEN 33 and 54 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllAdhBaisaReport();


	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0 AND age BETWEEN 55 and 65 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllBriddhaReport();

	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted = 0 AND fm.is_dead = 0  and age > 65 AND a.deleted = 0 AND a.filled_id IS NOT NULL")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAllJesthaNagarikReport();

	@SqlQuery("SELECT  fm.id AS id," +
			" fm.full_name AS full_name, " +
			" fr.relation_nepali AS relation, " +
			" fm.age AS age, " +
			" g.gender_nepali AS gender, " +
			" ms.marital_status_nep AS maritalStatus, " +
			" aq.qualification_nep AS education, " +
			" occ.occupation_nep AS occupation," +
			" fm.has_voter_id AS voterCard," +
			" hs.health_status_nep AS healthCondition, " +
			" ei.educational_institute_nep AS educationalInstitute, " +
			" da.differently_abled_nep AS disability, " +
			" fm.member_id AS memberId, " +
			" fm.dob_ad AS dateOfBirthAD, " +
			" fm.dob_bs AS dateOfBirthBS, " +
			" fm.is_dead AS isDead, " +
			" fm.family_id AS mainId " +
			" FROM family_member fm " +
			" INNER JOIN family_relation fr " +
			" ON fm.relation_id = fr.relation_id " +
			" INNER JOIN academic_qualification aq " +
			" ON fm.qualification_id = aq.qualification_id " +
			" INNER JOIN gender g " +
			" ON fm.gender_id = g.gender_id " +
			" INNER JOIN differently_abled da" +
			" ON fm.disability = da.id" +
			" INNER JOIN occupation occ" +
			" ON fm.occupation=occ.id" +
			" INNER JOIN health_status hs" +
			" ON fm.health_status = hs.id" +
			" INNER JOIN educational_institute ei" +
			" ON fm.educational_institute=ei.id" +
			" INNER JOIN marital_status ms" +
			" ON fm.marital_status = ms.marital_status_id" +
			" INNER JOIN answer a" +
			" ON fm.family_id = a.filled_id WHERE fm.deleted=0 AND fm.is_dead=0" +
			" AND a.deleted = 0 AND a.filled_id IS NOT NULL" +
			" AND aq.qualification_eng=:qualType")
	@RegisterBeanMapper(AgeGroupDTO.class)
	List<AgeGroupDTO> getAcademicQualificationReport(@Bind("qualType") String qualType);

	@SqlQuery(" select a.answer_4 AS house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no, a.answer_8 AS mother_tongue, ops.option_text AS caste, a.answer_10 AS religion from answer a " +
			" inner join options ops on a.answer_9+1 = ops.option_id where  ops.question_id=9 and a.answer_9 =:casteNo " +
			" and  a.deleted = 0 and a.filled_id is not null")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	@AllowUnusedBindings(value = true)
	List<LifeStyleAndCultureDTO> getCasteGroupHouseholdReport(@Bind Integer casteNo);

	@SqlQuery(" select a.answer_4 AS house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no, a.answer_8 AS mother_tongue, a.answer_9 AS caste, ops.option_text AS religion from answer a " +
			"            inner join options ops on a.answer_10+1 = ops.option_id where  ops.question_id=10 and a.answer_10 =:religionNo " +
			"            and  a.deleted = 0 and a.filled_id is not null")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	List<LifeStyleAndCultureDTO> getReligionGroupHouseHoldReport(@Bind Integer religionNo);

	@SqlQuery(" select a.answer_4 AS house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no, ops.option_text AS mother_tongue, a.answer_9 AS caste, a.answer_10 AS religion from answer a " +
			"            inner join options ops on a.answer_8+1 = ops.option_id where  ops.question_id=8 and a.answer_8 =:motherTongueNo " +
			"            and  a.deleted = 0 and a.filled_id is not null")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	List<LifeStyleAndCultureDTO> getMotherTongueHouseHoldReport(@Bind Integer motherTongueNo);


}