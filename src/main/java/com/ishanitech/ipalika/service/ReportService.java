package com.ishanitech.ipalika.service;

import java.util.List;

import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.model.ExtraReport;
import com.ishanitech.ipalika.model.FavouritePlaceReport;
import com.ishanitech.ipalika.model.PopulationReport;
import com.ishanitech.ipalika.model.QuestionReport;

public interface ReportService {
	public void generateReport(int wardNo);
	public List<PopulationReport> getAllPopulationReports(int wardNo);
	public List<QuestionReport> getAllQuestionReports(int wardNo);
	public List<ExtraReport> getExtraReports(int wardNo);
	public List<BeekeepingDTO> getBeekeepingInfo(int wardNo);
	public List<AgriculturalFarmDTO> getAgriculturaFarmInfo(int wardNo);
	public List<FavouritePlaceReport> getFavouritePlaceReports(int wardNo);

	public List<AgeGroupDTO> getSisuReport();

    public List<AgeGroupDTO> getBalBalikaReport();

	public List<AgeGroupDTO> getYuwaReport();

	public List<AgeGroupDTO> getAdhBaisaReport();

	public List<AgeGroupDTO> getBriddhaReport();

	public List<AgeGroupDTO> getJesthaNagarikReport();

	public List<AgeGroupDTO> getAcademicQualificationReport(String qualType);

	public List<LifeStyleAndCultureDTO> getCasteGroupHouseholdReport(Integer casteNo);

	List<LifeStyleAndCultureDTO> getReligionGroupHouseholdReport(Integer religionNo);

	List<LifeStyleAndCultureDTO> getMotherTongueHouseholdReport(Integer motherTongueNo);

	List<LifeStyleAndCultureDTO> getAnnualIncomeExpReport(int id, String type);

	List<LifeStyleAndCultureDTO> getHouseholdMedicalApproachesReport(int id);

	List<LifeStyleAndCultureDTO> getHouseHoldInfantMortalityReport(int id);

	List<FavouritePlaceTypeDTO> getTypeWiseFavouritePlaceReport(String typeId);
}
