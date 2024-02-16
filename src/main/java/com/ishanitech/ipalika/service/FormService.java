package com.ishanitech.ipalika.service;

import com.ishanitech.ipalika.model.Form;

public interface FormService {
	/**
	 *
	 * @param form object
	 */
	public void addForm(Form form);

	/**
	 * @param id integer value id
	 */
	public Form getFormById(Integer id);

	/**
	 * @param id integer id
	 */
	public void deleteById(Integer id);

	/**
	 * @param id
	 * @param formDTO
	 * @return void
	 */
	public void updateFormById(Integer id, Form form);
}
