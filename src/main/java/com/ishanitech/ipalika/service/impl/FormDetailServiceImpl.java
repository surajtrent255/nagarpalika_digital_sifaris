/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 20, 2019
 */
package com.ishanitech.ipalika.service.impl;

import java.util.List;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.dao.FormDetailDAO;
import com.ishanitech.ipalika.dao.OptionDao;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.model.FormDetail;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.FormDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FormDetailServiceImpl implements FormDetailService {
	DbService dbService;
	
	public FormDetailServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}

	
	@Override
	public List<FormDetail> getFormDetailById(Integer formId) throws EntityNotFoundException {	
		FormDetailDAO dao = dbService.getDao(FormDetailDAO.class);
		OptionDao optionDao = dbService.getDao(OptionDao.class);
		try {
			List<FormDetail> formDetails = dao.getAllFormDetails(formId);
			formDetails.forEach(formDetail -> {
				formDetail.setOptions(optionDao.getAllOptionByQuestionId(formDetail.getQuestionId()));
			});
			
			if(formDetails.size() > 0) {
				return formDetails;
			}
		} catch(UnableToExecuteStatementException ex) {
			log.info("#### Error: " + ex.getMessage());
		}
		
		throw new EntityNotFoundException("NO RESULTS!");
	}


	

}