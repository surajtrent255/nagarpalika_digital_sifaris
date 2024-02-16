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

public interface ReportDAO {
	
	@CreateSqlObject
	FavouritePlaceDAO favPlaceDao();
	
	@SqlQuery("SELECT q.id, qt.type_name FROM question q INNER JOIN question_type qt ON q.type_id = qt.type_id WHERE q.reportable = TRUE")
	@KeyColumn("id")
	@ValueColumn("type_name")
	Map<Integer, String> getAllAnswer();
	
	@SqlQuery("SELECT <column> FROM answer WHERE deleted = 0")
	List<String> getAllAnswerByAnswerId(@Define String column);
	
	@SqlQuery("SELECT count(*) AS total FROM family_member fm WHERE fm.is_dead = 0 AND fm.deleted = 0")
	double getTotalPopulation();
	
	@SqlQuery("SELECT g.gender_id, COUNT(id) AS total " + 
			"	FROM family_member fa " + 
			"	INNER JOIN gender g " + 
			"	ON g.gender_id = fa.gender_id " + 
			"	WHERE fa.deleted = 0 AND fa.is_dead = 0 " +
			"	GROUP BY fa.gender_id " + 
			"	ORDER BY g.gender_id ASC;")
	@KeyColumn("gender_id")
	@ValueColumn("total")
	Map<Integer, Double> getPopulationByGenderId();
	
	@SqlUpdate("REPLACE INTO population_report(based_on, option_1, option_2, option_3, option_4, option_5, option_6, total) " + 
			"	VALUES (\"AgeGroup\", (SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age < 6 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " + 
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age BETWEEN 6 AND 16 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " + 
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age BETWEEN 17 AND 32 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " + 
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age BETWEEN 33 AND 54 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " + 
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age BETWEEN 55 AND 65 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " + 
			"	(SELECT COUNT(*) FROM family_member fm INNER JOIN answer a ON a.filled_id = fm.family_id WHERE age > 65 AND fm.is_dead = 0 AND fm.deleted = 0 AND a.deleted = 0 AND a.filled_id IS NOT NULL), " +
			"   :total)")
	void insertAgeGroupReport(@Bind("total") double total);
	
	@SqlQuery("SELECT aq.qualification_id, COUNT(*) AS total FROM family_member fm " + 
			"INNER JOIN academic_qualification aq " + 
			"ON aq.qualification_id = fm.qualification_id WHERE fm.deleted = 0 AND fm.is_dead = 0 GROUP BY aq.qualification_nep ORDER BY aq.qualification_id ASC;")
	@KeyColumn("qualification_id")
	@ValueColumn("total")
	Map<Integer, Double> getPopulationByQualificationId();
	
	@SqlBatch("REPLACE INTO question_report (question_id, option_1, option_2, option_3, option_4, option_5, option_6, option_7, option_8, option_9, option_10, option_11, option_12, option_13, option_14, option_15, total) "
			+ " VALUES (:questionId, :option1, :option2, :option3, :option4, :option5, :option6, :option7, :option8, :option9, :option10, :option11, :option12, :option13, :option14, :option15, :total)")
	void insertQuestionReports(@BindBean List<QuestionReport> answerReports);
	
	/**
	 * @param populationReports
	 */
	@SqlBatch("REPLACE INTO population_report(based_on, option_1, option_2, option_3, option_4, option_5, option_6, option_7, option_8, option_9, option_10, option_11, option_12, option_13, option_14, option_15, total) "
			+ " VALUES "
			+ " (:basedOn, :option1, :option2, :option3, :option4, :option5, :option6, :option7, :option8, :option9, :option10, :option11, :option12, :option13, :option14, :option15, :total)")
	void insertPopulationReports(@BindBean List<PopulationReport> populationReports);
	
	@SqlQuery("SELECT * FROM population_report")
	@RegisterBeanMapper(PopulationReport.class)
	List<PopulationReport> getAllPopulationReports();
	
	@SqlQuery("SELECT * FROM question_report")
	@RegisterBeanMapper(QuestionReport.class)
	List<QuestionReport> getAllQuestionReport();
	
	@SqlQuery("SELECT * FROM favourite_place_report")
	@RegisterBeanMapper(FavouritePlaceReport.class)
	List<FavouritePlaceReport> getAllFavouritePlaceReports();
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_death', (SELECT COUNT(*) FROM death_record))")
	void generateDeathRecordCount();
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_household', (SELECT COUNT(*) FROM answer WHERE deleted = 0))")
	void generateTotalHouseholdCount();
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_animal_husbandary_household', (Select COUNT(*) from answer " + 
			"WHERE answer_42 NOT LIKE '%11:1%' AND answer_42 NOT LIKE '' AND deleted = 0))")
	void generateTotalAnimalHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_agriculture_household', (SELECT COUNT(*) from answer " + 
			"WHERE (answer_80 NOT LIKE '' OR answer_81 NOT LIKE '' OR answer_82 NOT LIKE '' " + 
			"OR answer_83 NOT LIKE '' OR answer_84 NOT LIKE '' OR answer_85 NOT LIKE '') AND deleted = 0))")
	void generateTotalAgricultureHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_beekeeping_household', (SELECT COUNT(*) from answer " + 
			"WHERE answer_89 NOT LIKE '%1:1%' AND answer_89 NOT LIKE '' AND answer_89 NOT LIKE '1:%' AND deleted = 0))")
	void generateTotalBeeKeepingHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_agriculture_firm', (SELECT COUNT(*) from answer " + 
			"WHERE answer_75 NOT LIKE '' AND answer_75 NOT LIKE '*' AND answer_75 NOT LIKE '%0%' AND answer_75 NOT LIKE ',' " + 
			"AND answer_75 NOT LIKE '%1%' AND answer_75 NOT LIKE 'a' AND answer_75 NOT LIKE '.' AND answer_75 NOT LIKE 'छैन%' AND deleted = 0))")
	void generateTotalAgricultureFirmCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_foodcrop_household', (SELECT COUNT(*) from answer WHERE answer_80 NOT LIKE '' AND answer_80 NOT LIKE '6' AND answer_80 NOT LIKE '%6%' AND deleted = 0))")
	void generateTotalFoodCropHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_cashcrop_household', (SELECT COUNT(*) from answer WHERE answer_81 NOT LIKE '' AND answer_81 NOT LIKE '10' AND answer_81 NOT LIKE '%10%' AND deleted = 0))")
	void generateTotalCashCropHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_dahacrop_household', (SELECT COUNT(*) from answer WHERE answer_82 NOT LIKE '' AND answer_82 NOT LIKE '6' AND answer_82 NOT LIKE '%6%' AND deleted = 0))")
	void generateTotalDahaCropHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_fruit_household', (SELECT COUNT(*) from answer WHERE answer_83 NOT LIKE '' AND answer_83 NOT LIKE '8' AND answer_83 NOT LIKE '%8%' AND deleted = 0))")
	void generateTotalFruitHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_vegetable_household', (SELECT COUNT(*) from answer WHERE answer_84 NOT LIKE '' AND answer_84 NOT LIKE '14' AND answer_84 NOT LIKE '%14%' AND deleted = 0))")
	void generateTotalVegetableHouseholdCount();
	
	
	@SqlUpdate("REPLACE INTO extra_report(report_name, data) VALUE ('total_mushroom_household', (SELECT COUNT(*) from answer WHERE answer_85 NOT LIKE '' AND answer_85 NOT LIKE '5' AND answer_85 NOT LIKE '%5%' AND deleted = 0))")
	void generateTotalMushroomHouseholdCount();
	
	
	@SqlQuery("SELECT id AS id, a.answer_1 AS ownerName, a.answer_3 AS wardNo, a.answer_89 AS beeHiveNo, a.answer_90 AS beeSpecies, a.answer_91 AS yearlyProduction from answer a WHERE a.answer_89 NOT LIKE '%1:1%' AND a.answer_89 NOT LIKE '' AND a.answer_89 NOT LIKE '1:%' AND a.deleted = 0")
	@RegisterBeanMapper(BeekeepingDTO.class)
	List<BeekeepingDTO> getBeekeepingInfo();

	
	@SqlQuery("SELECT id AS id, a.answer_3 AS wardNo, a.answer_75 AS farmName, a.answer_76 AS registration, a.answer_77 AS insurance from answer a WHERE a.answer_75 NOT LIKE '' AND a.answer_75 NOT LIKE '*' AND a.answer_75 NOT LIKE '%0%' AND a.answer_75 NOT LIKE ',' AND a.answer_75 NOT LIKE '%1%' AND a.answer_75 NOT LIKE 'a' AND a.answer_75 NOT LIKE '०' AND a.answer_75 NOT LIKE '.' AND a.answer_75 NOT LIKE 'छैन%' AND a.deleted = 0 ")
	@RegisterBeanMapper(AgriculturalFarmDTO.class)
	List<AgriculturalFarmDTO> getAgriculturalFarmInfo();
	
	
	@SqlUpdate("REPLACE INTO favourite_place_report(place_type, data) "
			+ " VALUES "
			+ " ((SELECT place_type_eng from favourite_place_type WHERE type_id =:typeId), (SELECT COUNT(*) FROM favourite_place WHERE fav_place_type =:typeId AND deleted = 0))")
	void insertFavouritePlaceReports(@Bind("typeId") String placeTypeId);
	
	@Transaction
	default void generateReport() {
		Map<Integer, String> answerAndTypes = getAllAnswer();
		double totalPopulation = getTotalPopulation();
		Map<Integer, Double> populationByGender = getPopulationByGenderId();
		Map<Integer, Double> populationByQualification = getPopulationByQualificationId();
		generateDeathRecordCount();
		generateTotalHouseholdCount();
		generateTotalAnimalHouseholdCount();
		generateTotalAgricultureHouseholdCount();
		generateTotalBeeKeepingHouseholdCount();
		generateTotalAgricultureFirmCount();
		generateTotalFoodCropHouseholdCount();
		generateTotalCashCropHouseholdCount();
		generateTotalDahaCropHouseholdCount();
		generateTotalFruitHouseholdCount();
		generateTotalVegetableHouseholdCount();
		generateTotalMushroomHouseholdCount();
		generateFavouritePlaceReport();
		List<PopulationReport> populationReports = new ArrayList<>();
		insertAgeGroupReport(totalPopulation); //inserts age group report
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
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		populationReports.add(genderReport);
		populationReports.add(qualificationReport);
		insertPopulationReports(populationReports);
		
		List<QuestionReport> rawAnswers = answerAndTypes.keySet().stream()
			.map(item -> {
				String table = String.format("answer_%s", item);
				List<String> answer = getAllAnswerByAnswerId(table);
				if(!answer.isEmpty()) {
					String[] list = answer.toString().replace("[", "").replace("]", "").split(",");
					QuestionReport report = ReportUtil.generateReport(item, list, QuestionType.valueOf(answerAndTypes.get(item)));
					return report;
				} 
				throw new CustomSqlException(String.format("No answers found for question no. %s.", item));
			}).collect(Collectors.toList());
		insertQuestionReports(rawAnswers);
	}

	@RegisterBeanMapper(ExtraReport.class)
	@SqlQuery("SELECT report_name, data FROM extra_report")
	List<ExtraReport> getExtraReports();

	@Transaction
	default void generateFavouritePlaceReport() {
		List<String> favPlaceTypeIds = favPlaceDao().getTypeIdofFavouritePlaces();
		favPlaceTypeIds.stream().forEach((placeTypeId) -> insertFavouritePlaceReports(placeTypeId));
	}


	@SqlQuery(" SELECT  a.answer_4 AS house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no,"+
			" ops.option_text as annual_income FROM  answer a INNER  JOIN options   ops" +
			" ON  a.answer_40+1 = ops.option_id " +
			" WHERE  a.answer_40=:id AND  ops.question_id = 40 " +
			" AND   a.deleted = 0 AND  a.filled_id IS  NOT  NULL ")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
    List<LifeStyleAndCultureDTO> getAnnualIncomeReport(@Bind int id);

	@SqlQuery(" SELECT  a.answer_4 AS house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no,"+
			" ops.option_text as annual_expense FROM  answer a INNER  JOIN options   ops" +
			" ON  a.answer_41+1 = ops.option_id " +
			" WHERE  a.answer_41=:id AND  ops.question_id = 41 " +
			" AND   a.deleted = 0 AND  a.filled_id IS  NOT  NULL ")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	List<LifeStyleAndCultureDTO> getAnnualExpenseReport(@Bind  int id);

	@SqlQuery("SELECT  a.answer_4 AS  house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no,  ops.option_text AS  medical_approach FROM  answer a " +
			" INNER  JOIN  options   ops ON  a.answer_54+1 = ops.option_id WHERE   ops.question_id=54 AND  a.answer_54 =:id" +
			" AND   a.deleted = 0 AND  a.filled_id IS  NOT  NULL ")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	List<LifeStyleAndCultureDTO> getHouseholdsMedicalApproachesReport(@Bind int id);

	@SqlQuery("SELECT  a.answer_4 AS  house_no, a.answer_1 AS owner_name, a.answer_2 AS tole, a.answer_3 AS resident_ward_no,  ops.option_text AS  infant_mortality FROM  answer a " +
			" INNER  JOIN  options   ops ON  a.answer_61+1 = ops.option_id WHERE   ops.question_id=61 AND  a.answer_61 =:id" +
			" AND   a.deleted = 0 AND  a.filled_id IS  NOT  NULL ")
	@RegisterBeanMapper(LifeStyleAndCultureDTO.class)
	List<LifeStyleAndCultureDTO> getHouseholdInfantMortalityReport(@Bind int id);


	@SqlQuery("SELECT fp.fav_place_name AS placeName, fp.fav_place_desc AS placeDescription, fp.fav_place_photo AS placeImage, fp.fav_place_location AS placeGPS, " +
			" fp.fav_place_ward AS placeWard, fpt.place_type_nep as placeType,  u.username AS addedBy FROM favourite_place fp" +
			" INNER JOIN favourite_place_type fpt ON fpt.type_id = fp.fav_place_type " +
			" INNER JOIN user u ON fp.added_by = u.id" +
			" WHERE fp.fav_place_type =:typeId  AND fp.deleted = 0")
	@RegisterBeanMapper(FavouritePlaceTypeDTO.class)
	List<FavouritePlaceTypeDTO> getTypeWiseFavouritePlaces(@Bind String typeId);
}
