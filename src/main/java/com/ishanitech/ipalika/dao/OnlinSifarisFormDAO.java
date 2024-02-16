package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.dao.mapper.FormDetailMapper;
import com.ishanitech.ipalika.dao.mapper.SifarisMetaDataMapper;
import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.FormDetail;
import com.ishanitech.ipalika.model.QuestionReport;
import com.ishanitech.ipalika.model.SifarisLetterMetaData;
import com.ishanitech.ipalika.utils.ApprovalStatusUtil;
import com.ishanitech.ipalika.utils.FormInfoUtil;
import com.ishanitech.ipalika.utils.ReportUtil;
import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.config.ValueColumn;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface OnlinSifarisFormDAO {


    @SqlQuery("SELECT fd.form_id, fd.element_id as field_id, fd.element_name as fieldName, qt.type_name as fieldType, fd.fillable_by_system as fillable_by_system, fd.is_required as required FROM " +
            "forms_details fd INNER JOIN question_type qt " +
            "ON fd.element_type = qt.type_id where fd.form_id = :formId;")
    @RegisterBeanMapper(OnlineSifarisFormFieldsDTO.class)
    List<OnlineSifarisFormFieldsDTO> getFormsElementByFormId(@Bind("formId") int id);

    @SqlQuery("SELECT form_database_table FROM forms_table WHERE form_id = :formId")
    String getDatabaseTableName(@Bind("formId") int id);

    @UseClasspathSqlLocator
    @SqlUpdate("insert_online_sifaris_form")
    void addSifarisFormFieldValues(@Define("table") String databasename, @BindBean OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers);

    @SqlUpdate("INSERT INTO standard_log (form_id, token_id, status_1_in) VALUE ( :formId, :token, :dateOfApplication)")
    int createStandardLog(@Bind int formId, @Bind String token, Date dateOfApplication);

    @Transaction
    default void addNewEntity(String databaseName, OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers) {
        addSifarisFormFieldValues(databaseName, onlineSIfarisFormAnswers);
        updateRegistrationReportForm(1, onlineSIfarisFormAnswers.getFormId());
        createStandardLog(onlineSIfarisFormAnswers.getFormId(), onlineSIfarisFormAnswers.getTokenId(), new Date());
    }
    @SqlUpdate("UPDATE sifaris_report SET <currentStatus> = <currentStatus> -1 , <futureStatus> = <futureStatus> + 1 WHERE form_id = :formId ")
    void updateRegistrationReports(@Define String currentStatus, @Define String futureStatus, Integer formId);

    @SqlUpdate("UPDATE sifaris_report SET <futureStatus> = <futureStatus> + 1, total = total + 1 WHERE form_id = :formId  ")
    void updateRegistrationReports( @Define String futureStatus, Integer formId);

    @Transaction
    default void updateRegistrationReportForm(Integer futureStatus, Integer formId) {
        switch (futureStatus) {
            case 1:
                updateRegistrationReports(ApprovalStatusUtil.UNVERIFIED.getStatusLevel(), formId);
                break;
            case 2:
                updateRegistrationReports(ApprovalStatusUtil.UNVERIFIED.getStatusLevel(), ApprovalStatusUtil.STAMPED.getStatusLevel(), formId);
                break;
            case 3:
                updateRegistrationReports(ApprovalStatusUtil.STAMPED.getStatusLevel(), ApprovalStatusUtil.REGISTERED.getStatusLevel(), formId);
                break;
            case 4:
                updateRegistrationReports(ApprovalStatusUtil.REGISTERED.getStatusLevel(), ApprovalStatusUtil.PROCESSED.getStatusLevel(), formId);
                break;
            case 5:
                updateRegistrationReports(ApprovalStatusUtil.PROCESSED.getStatusLevel(), ApprovalStatusUtil.VERIFIED.getStatusLevel(), formId);
                break;

        }
    }

    @SqlUpdate("UPDATE standard_log SET registration_number = :regNo WHERE form_id = :formId AND token_id = :tokenId")
    void updateRegistrationNumber(@Bind("formId") Integer formId, @Bind("regNo") String regNo, @Bind("tokenId") String tokenId);

    @SqlUpdate("UPDATE standard_log SET status = :status WHERE token_id = :tokenId AND form_id = :formId")
    void updateEntityStatus(@Bind("tokenId") String tokenId, @Bind("status") Integer status, @Bind("formId") Integer formId);

    @Transaction
    default	void updateStatus(String tokenId, Integer status, Integer formId, String regNo){
        updateEntityStatus(tokenId, status, formId);
        updateRegistrationReportForm(status, formId);
        if(status == 3){
            updateRegistrationNumber(formId, regNo, tokenId);
        }
    }

    @SqlUpdate("UPDATE standard_log SET <column1> = :value1, <column2> = :value2 WHERE form_id = :formId AND token_id = :tokenId")
    void updateAuthorityColumn(@Define String column1, String value1, @Define String column2 , Date value2, int formId, String tokenId);

    @SqlUpdate("UPDATE standard_log SET approved = 1 WHERE form_id = :formId AND token_id = :tokenId")
    int approveSifaris(int formId, String tokenId);

    @UseClasspathSqlLocator
    @SqlQuery("fetch_online_sifaris_fields")
    @RegisterBeanMapper(OnlineSifarisDetailsFieldDTO.class)
    OnlineSifarisDetailsFieldDTO getSifarisByTokenId(@Define String databaseTableName, String tokenId);

//    @SqlQuery("SELECT fd.form_id, fd.element_id as field_id, fd.element_name as fieldName, qt.type_name as fieldType FROM " +
//            "forms_details fd INNER JOIN question_type qt " +
//            "ON fd.element_type = qt.type_id where fd.form_id = :formId;")
    @SqlQuery("SELECT fd.form_id, fd.element_id as field_id, fd.element_name as fieldName, qt.type_name as fieldType, fd.fillable_by_system as fillable_by_system, fd.is_required as required FROM " +
            "forms_details fd INNER JOIN question_type qt " +
            "ON fd.element_type = qt.type_id where fd.form_id = :formId;")
    @RegisterBeanMapper(SifarisDetailDTO.class)
    List<SifarisDetailDTO> getSifarisDetailBluePrint(Integer formId);

    @SqlQuery("SELECT sl.status FROM standard_log sl where form_id = :formId AND token_id = :tokenId")
    Integer getSifarisPresentStatus(Integer formId, String tokenId);

//    @SqlQuery("SELECT sfm.element_id FROM sifaris_form_fields_mapper sfm WHERE form_id = :formId AND element_placeholder = :elementPlaceholder")
    @SqlQuery("SELECT <column> FROM <table> WHERE token_id = :tokenId")
    String getUserReqFieldAnsFromSifarisAnswerTable(@Define("column") String column, @Define("table") String table, @Bind("tokenId") String tokenId);

    @SqlQuery("SELECT sfm.element_id FROM sifaris_form_fields_mapper sfm WHERE form_id = :formId AND element_placeholder = :reqField")
    String getElementIdFromSifarisFormFieldsMapper(@Bind("formId") int formId, String reqField);

    @Transaction
    default String getUserRequiredFieldAnsFromSifarisFormFieldMapper(int formId, String table, String tokenId, String reqField){
        String elementId = getElementIdFromSifarisFormFieldsMapper(formId, reqField);
        String columnName = "answer_"+ elementId;
        String reqFieldAns = getUserReqFieldAnsFromSifarisAnswerTable(columnName, table, tokenId);
        return reqFieldAns;
    }

    @SqlQuery("SELECT registration_number FROM standard_log WHERE form_id = :formId AND token_id = :tokenId")
    String getRegistrationNumber(int formId, String tokenId);

    @SqlQuery("SELECT status_3_in FROM standard_log WHERE form_id = :formId AND token_id = :tokenId")
    String getRegistrationDate(int formId, String tokenId);
    @UseClasspathSqlLocator
    @SqlUpdate("update_online_sifaris_form")
    void editSifarisForm(@Define String databaseTableName, @BindBean OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers);


    @SqlQuery("SELECT sffm.element_id, sffm.element_placeholder FROM sifaris_form_fields_mapper sffm "+
            "WHERE sffm.form_id = :formId")
    @KeyColumn("element_id")
    @ValueColumn("element_placeholder")
    Map<Integer, String> getAllElementIdAndPlaceholders( int formId);

    @SqlQuery("SELECT <column> FROM <databaseTable> WHERE token_id = :token")
    String getSifarisMetaDataAnswer(@Define String column, @Bind String token, @Define  String databaseTable);

    @Transaction
    default Map<String, String>  getSifarisLetterMetaData(int formId, String tokenId, String databaseTableName){
        Map<Integer, String> elementIdAndPlaceholders = getAllElementIdAndPlaceholders(formId);
        Map<String, String> elementPlaceholderAndValues = new HashMap<>();
        elementIdAndPlaceholders.keySet().stream()
                .map(item -> {
                    String column = String.format("answer_%s", item);
                    String answer = getSifarisMetaDataAnswer(column, tokenId, databaseTableName);
                    elementPlaceholderAndValues.put(elementIdAndPlaceholders.get(item), answer);
                    return elementPlaceholderAndValues;
                }).collect(Collectors.toList());
        return elementPlaceholderAndValues;
    }

//    @SqlQuery("SELECT sffm.element_id, sffm.element_placeholder FROM sifaris_form_fields_mapper sffm " +
//            "WHERE form_id = :formId")
//    @RegisterRowMapper(SifarisMetaDataMapper.class)
//    public List<SifarisLetterMetaData> getAllFormDetails(@Bind("formID") Integer formId, String tokenId);
//
//    @SqlQuery("SELECT <column> FROM <databaseTable> WHERE token_id = :token")
//    String getSifarisMetaDataAnswer(String column, String token, String databaseTable);
}
