
package com.ishanitech.ipalika.controller;

import java.util.List;

import com.ishanitech.ipalika.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.ExtraReport;
import com.ishanitech.ipalika.model.FavouritePlaceReport;
import com.ishanitech.ipalika.model.PopulationReport;
import com.ishanitech.ipalika.model.QuestionReport;
import com.ishanitech.ipalika.service.ReportService;


/**
 * {@code ReportController} handles the request for Report.
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
@RequestMapping("/report")
@RestController
public class ReportController {
	private final ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	
	/**
	 * Generates Report
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{wardNo}")
	public void generateReport(@PathVariable("wardNo") int wardNo) {
		reportService.generateReport(wardNo);
	}
	
	
	/**
	 * Gets population report
	 * @return {@code ResponseDTO<List<PopulationReport>} object
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@GetMapping("/population/{wardNo}")
	ResponseDTO<List<PopulationReport>> getPopulationReport(@PathVariable("wardNo") int wardNo) {
		return new ResponseDTO<List<PopulationReport>>(reportService.getAllPopulationReports(wardNo));
	}


	/**
	 * Get Report of Specific age Group
	 * @return{@code ResponseDTO<List<AgeGoupDTO>>}</AgeGoupDTO> object
	 * @author <b>Suraj Adhikari</b>
	 * @throws CustomSqlException
	 */
	@GetMapping("/sisu")
	ResponseDTO<List<AgeGroupDTO>> getSisuReport() throws CustomSqlException{
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getSisuReport());
	}

	@GetMapping("/balbalika")
	ResponseDTO<List<AgeGroupDTO>> getBalBalikaReport() throws CustomSqlException{
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getBalBalikaReport());
	}

	@GetMapping("/yuwa")
	ResponseDTO<List<AgeGroupDTO>> getYuwaReport() throws CustomSqlException {
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getYuwaReport());
	}

	@GetMapping("/adhBaisa")
	ResponseDTO<List<AgeGroupDTO>> getAdhBaisaReport() throws CustomSqlException {
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getAdhBaisaReport());
	}

	@GetMapping("/briddha")
	ResponseDTO<List<AgeGroupDTO>> getBriddhaReport() throws CustomSqlException {
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getBriddhaReport());
	}

	@GetMapping("/jesthaNagarik")
	ResponseDTO<List<AgeGroupDTO>> getJesthaNagarkiReport() throws CustomSqlException {
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getJesthaNagarikReport());
	}


	@GetMapping("/academicQualification/{qualificationType}")
	ResponseDTO<List<AgeGroupDTO>> getAcademicQualificationReport(@PathVariable("qualificationType") String qualType) throws CustomSqlException {
		String qualificationType = "";
		for (String camelSplited : qualType.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
			qualificationType += " "+camelSplited.toLowerCase();
		}
		return new ResponseDTO<List<AgeGroupDTO>>(reportService.getAcademicQualificationReport(qualificationType.trim()));
	}


	@GetMapping("/annualIncomeExpenses/{id}/{type}")
	public ResponseDTO<List<LifeStyleAndCultureDTO>> getAnnualIncomeExpReportView(@PathVariable("id") String id, @PathVariable("type") String type){
		return new ResponseDTO<List<LifeStyleAndCultureDTO>> (reportService.getAnnualIncomeExpReport(Integer.parseInt(id), type));

	}

	@GetMapping("/houseHoldsMedicalApproach/{typeId}")
	public ResponseDTO<List<LifeStyleAndCultureDTO>> getHouseHoldMedicalApproachReportView(@PathVariable("typeId") String id){
		return new ResponseDTO<List<LifeStyleAndCultureDTO>>(reportService.getHouseholdMedicalApproachesReport(Integer.parseInt(id)));
	}

	@GetMapping("/houseHoldInfantMortality/{statusId}")
	public ResponseDTO<List<LifeStyleAndCultureDTO>> getHouseHoldInfantMortalityReportView(@PathVariable("statusId") String id){
		return new ResponseDTO<List<LifeStyleAndCultureDTO>>(reportService.getHouseHoldInfantMortalityReport(Integer.parseInt(id)));
	}
	@GetMapping("/casteGroup/{casteNo}")
	ResponseDTO<List<LifeStyleAndCultureDTO>> getCasteGroupHouseHoldReport(@PathVariable("casteNo") Integer casteNo) throws CustomSqlException{
		return new ResponseDTO<List<LifeStyleAndCultureDTO>>(reportService.getCasteGroupHouseholdReport(casteNo));
	}

	@GetMapping("/religionGroup/{religionNo}")
	ResponseDTO<List<LifeStyleAndCultureDTO>> getReligionGroupHouseholdReport(@PathVariable("religionNo") Integer religionNo) throws CustomSqlException{
		return new ResponseDTO<List<LifeStyleAndCultureDTO>>(reportService.getReligionGroupHouseholdReport(religionNo));
	}

	@GetMapping("/motherTongueGroup/{motherTongueNo}")
	ResponseDTO<List<LifeStyleAndCultureDTO>> getMotherTongueHouseholdReport(@PathVariable("motherTongueNo") Integer motherTongueNo) throws CustomSqlException{
		return new ResponseDTO<List<LifeStyleAndCultureDTO>>(reportService.getMotherTongueHouseholdReport(motherTongueNo));
	}

	@GetMapping("/favourite-place-type/{typeId}")
	ResponseDTO<List<FavouritePlaceTypeDTO>> getFavouritePlaceTypeReport(@PathVariable("typeId") String typeId){
		return new ResponseDTO<List<FavouritePlaceTypeDTO>>(reportService.getTypeWiseFavouritePlaceReport(typeId));
	}

	/**
	 * Gets question report
	 * @return {@code ResponseDTO<List<QuestionReport>} object
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@GetMapping("/question/{wardNo}")
	ResponseDTO<List<QuestionReport>> getQuestionReports(@PathVariable("wardNo") int wardNo) {
		return new ResponseDTO<List<QuestionReport>>(reportService.getAllQuestionReports(wardNo));
	}
	
	
	/**
	 * Gets Extra report
	 * @return {@code ResponseDTO<List<ExtraReport>} object
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@GetMapping("/extra/{wardNo}")
	ResponseDTO<List<ExtraReport>> getExtraReports(@PathVariable("wardNo") int wardNo) {
		return new ResponseDTO<>(reportService.getExtraReports(wardNo));
	}
	
	
	@GetMapping("/favouritePlace/{wardNo}")
	ResponseDTO<List<FavouritePlaceReport>> getFavouritePlaceReports(@PathVariable("wardNo") int wardNo) {
		return new ResponseDTO<>(reportService.getFavouritePlaceReports(wardNo));
	}
	
	@GetMapping("/beekeeping/{wardNo}") 
	public ResponseDTO<List<BeekeepingDTO>> getBeekeepingInfo(@PathVariable("wardNo") int wardNo) throws CustomSqlException {
		return new ResponseDTO<List<BeekeepingDTO>>(reportService.getBeekeepingInfo(wardNo));
	}
	
	
	@GetMapping("/agriculturalFarm/{wardNo}") 
	public ResponseDTO<List<AgriculturalFarmDTO>> getAgriculturalFarmInfo(@PathVariable("wardNo") int wardNo) throws CustomSqlException {
		return new ResponseDTO<List<AgriculturalFarmDTO>>(reportService.getAgriculturaFarmInfo(wardNo));
	}
	
}