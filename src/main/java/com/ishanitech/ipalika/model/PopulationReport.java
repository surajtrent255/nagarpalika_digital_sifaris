package com.ishanitech.ipalika.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationReport {
	private int id;
	private String basedOn;
	@ColumnName("opt_1")
	private Double option1;
	
	@ColumnName("opt_2")
	private Double option2;
	
	@ColumnName("opt_3")
	private Double option3;
	
	@ColumnName("opt_4")
	private Double option4;
	
	@ColumnName("opt_5")
	private Double option5;
	
	@ColumnName("opt_6")
	private Double option6;
	
	@ColumnName("opt_7")
	private Double option7;
	
	@ColumnName("opt_8")
	private Double option8;
	
	@ColumnName("opt_9")
	private Double option9;
	
	@ColumnName("opt_10")
	private Double option10;
	
	@ColumnName("opt_11")
	private Double option11;
	
	@ColumnName("opt_12")
	private Double option12;
	
	@ColumnName("opt_13")
	private Double option13;
	
	@ColumnName("opt_14")
	private Double option14;
	
	@ColumnName("opt_15")
	private Double option15;
	private Double total;
}
