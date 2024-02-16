package com.ishanitech.ipalika.utils;

import java.util.stream.Stream;

import com.ishanitech.ipalika.dto.QuestionType;
import com.ishanitech.ipalika.model.QuestionReport;

public class ReportUtil {
	public static double PIF = 1.0;
	public static QuestionReport generateReport(int questionId, String[] fullData, QuestionType type) {
		QuestionReport report = new QuestionReport();
		report.setQuestionId(questionId);
		report.setTotal(0);
		report.setOption1(0);
		report.setOption2(0);
		report.setOption3(0);
		report.setOption4(0);
		report.setOption5(0);
		report.setOption6(0);
		report.setOption7(0);
		report.setOption8(0);
		report.setOption9(0);
		report.setOption10(0);
		report.setOption11(0);
		report.setOption12(0);
		report.setOption13(0);
		report.setOption14(0);
		report.setOption15(0);
		
		switch(type) {
			case CHECKBOX_N:
				Stream.of(fullData).forEach(dat -> {
					String treamedData = dat.trim();
					if(treamedData.startsWith("0")) {
						report.setOption1(report.getOption1() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("1")) {
						report.setOption2(report.getOption2() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("2")) {
						report.setOption3(report.getOption3() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("3")) {
						report.setOption4(report.getOption4() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("4")) {
						report.setOption5(report.getOption5() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("5")) {
						report.setOption6(report.getOption6() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("6")) {
						report.setOption7(report.getOption7() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("7")) {
						report.setOption8(report.getOption8() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("8")) {
						report.setOption9(report.getOption9() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("9")) {
						report.setOption10(report.getOption10() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("10")) {
						report.setOption11(report.getOption11() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("11")) {
						report.setOption12(report.getOption12() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("12")) {
						report.setOption13(report.getOption13() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("13")) {
						report.setOption14(report.getOption14() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.startsWith("14")) {
						report.setOption15(report.getOption15() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
					
					if(treamedData.contains(":")) {
						report.setTotal(report.getTotal() + Integer.parseInt(treamedData.substring(treamedData.indexOf(":") + 1)));
					}
				});
				break;
			case CHECKBOX:
			case RADIO:
			case RADIO_D:
			case DROPDOWN:
				Stream.of(fullData).forEach(dat -> {
					if(!dat.trim().equals(""))
					{
					double treamedData = Integer.parseInt(dat.trim());
					if(treamedData == 0) {
						report.setOption1(report.getOption1() + 1);
					}
					
					if(treamedData == 1) {
						report.setOption2(report.getOption2() + 1);
					}
					
					if(treamedData == 2) {
						report.setOption3(report.getOption3() + 1);
					}
					
					if(treamedData == 3) {
						report.setOption4(report.getOption4() + 1);
					}
					
					if(treamedData == 4) {
						report.setOption5(report.getOption5() + 1);
					}
					
					if(treamedData == 5) {
						report.setOption6(report.getOption6() + 1);
					}
					
					if(treamedData == 6) {
						report.setOption7(report.getOption7() + 1);
					}
					
					if(treamedData == 7) {
						report.setOption8(report.getOption8() + 1);
					}
					
					if(treamedData == 8) {
						report.setOption9(report.getOption9() + 1);
					}
					
					if(treamedData == 9) {
						report.setOption10(report.getOption10() + 1);
					}
					
					if(treamedData == 10) {
						report.setOption11(report.getOption11() + 1);
					}
					
					if(treamedData == 11) {
						report.setOption12(report.getOption12() + 1);
					}
					
					if(treamedData == 12) {
						report.setOption13(report.getOption13() + 1);
					}
					
					if(treamedData == 13) {
						report.setOption14(report.getOption14() + 1);
					}
					
					if(treamedData == 14) {
						report.setOption15(report.getOption15() + 1);
					}
					} 
					
					report.setTotal(report.getTotal() + 1);
				});
				break;
			default:
				break;
		}
		
		return report;
	}
}
