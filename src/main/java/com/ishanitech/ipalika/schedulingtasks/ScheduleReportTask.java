package com.ishanitech.ipalika.schedulingtasks;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ishanitech.ipalika.service.ReportService;
import com.ishanitech.ipalika.servicer.WardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleReportTask {
	private final ReportService reportService;
	private final WardService wardService;
	
	public ScheduleReportTask(ReportService reportService, WardService wardService) {
		this.reportService = reportService;
		this.wardService = wardService;
	}

	//Run Report Generation Service Everyday at midnight
	@Scheduled(cron = "0 0 0 * * ?")
	//@Scheduled(fixedRate = 60000)
	public void generateReport() {
		log.info("Generate Report Called");
		reportService.generateReport(0);
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	//@Scheduled(fixedRate = 60000)
	public void generateReportWard() {
		List<Integer> wardNos = wardService.getAllWardNumbers();
		wardNos.stream().forEach((wardNo) -> reportService.generateReport(wardNo));
	}
}
