/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 20, 2019
 */
package com.ishanitech.ipalika.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question {
	private int id;
	private String questionId;
	private String description;
	private String group;
	private boolean required;
	private Integer typeId;
	private Integer formId;
	private List<Option> options;
}
