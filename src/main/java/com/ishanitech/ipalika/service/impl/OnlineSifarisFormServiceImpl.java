package com.ishanitech.ipalika.service.impl;

import com.ishanitech.ipalika.dao.OnlinSifarisFormDAO;
import com.ishanitech.ipalika.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormFieldsDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormRecordsDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.MailService;
import com.ishanitech.ipalika.service.OnlineSifarisFormService;
import com.ishanitech.ipalika.utils.FileUtilService;
import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OnlineSifarisFormServiceImpl implements OnlineSifarisFormService {

    private DbService dbService;
    private final MailService mailService;
    private final FileUtilService fileUtilService;
    public OnlineSifarisFormServiceImpl(DbService dbService, FileUtilService fileUtilService, MailService mailService) {
        this.dbService = dbService;
        this.fileUtilService = fileUtilService;
        this.mailService = mailService;
    }
    @Override
    public List<OnlineSifarisFormFieldsDTO> getFullFormDetailById(int id) {
        OnlinSifarisFormDAO onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
        List<OnlineSifarisFormFieldsDTO> onlineSifarisFormFields;
        try{
            onlineSifarisFormFields = onlinSifarisFormDAO.getFormsElementByFormId(id);
        } catch (JdbiException jEx){
            throw new CustomSqlException("Something went wrong while inserting questions and options.");

        }
        return  onlineSifarisFormFields;
    }

    @Override
    public void addOnlineSifarisFormData(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers) {
        int formId= onlineSIfarisFormAnswers.getFormId();
        OnlinSifarisFormDAO onlinSifarisFormDAO;
        try{
            onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
            String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(formId);
            onlinSifarisFormDAO.addNewEntity(databaseTableName, onlineSIfarisFormAnswers);
            String message = "";
            String email = onlinSifarisFormDAO.getUserRequiredFieldAnsFromSifarisFormFieldMapper(onlineSIfarisFormAnswers.getFormId(),databaseTableName, onlineSIfarisFormAnswers.getTokenId(),"EMAIL");

            message = "तपाई को विद्युत जडान सिफारिस स्वीकार  भय को छ | तोक  लागुने  समय सम कुर्नु होस् .... <br>"
                    + " तपाई को  सिफारिस तोक लागे को आर्को इमेल  मा आउने छ | कार्यालय  आउदा यो टोकन नम्बर  लेदै आउनु होस् <br>"
                    + " तपाई को टोकन नम्बर  " + onlineSIfarisFormAnswers.getTokenId() + ".";
            mailService.sendEmail(message, email);
        } catch(JdbiException jEx){
            throw new CustomSqlException("something went wrong while inserting form data " + jEx.getMessage());
        }
    }

    @Override
    public void addOnlineSifarisImage(MultipartFile image) {
        fileUtilService.storeFile(image);
    }

    @Override
    public void editOnlineSifarisImage(MultipartFile image) {
        fileUtilService.storeEditedFile(image);
    }


}
