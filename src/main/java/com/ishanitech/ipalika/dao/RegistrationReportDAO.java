package com.ishanitech.ipalika.dao;
import com.ishanitech.ipalika.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisDetailsFieldDTO;
import com.ishanitech.ipalika.dto.RegistrationReport;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;


public interface RegistrationReportDAO {


    @SqlQuery("SELECT COUNT(*) FROM <tableName>")
    int getTotalRegistrationCount(@Define("tableName") String tableName);

    @SqlQuery("SELECT COUNT(*) FROM <tableName> WHERE status = 1")
    int getUnverifiedRegistrationCount(@Define("tableName") String tableName);

    @SqlQuery("SELECT COUNT(*) FROM <tableName> WHERE status = 2")
    int getStampedRegistrationCount(@Define("tableName") String tableName);

    @SqlQuery("SELECT COUNT(*) FROM <tableName> WHERE status = 3")
    int getRegisteredRegistrationCount(@Define("tableName") String tableName);

    @SqlQuery("SELECT COUNT(*) FROM <tableName> WHERE status = 4")
    int getInProcessRegistrationCount(@Define("tableName") String tableName);

    @SqlQuery("SELECT COUNT(*) FROM <tableName> WHERE status = 5")
    int getVerifiedRegistrationCount(@Define("tableName") String tableName);

    @SqlBatch("REPLACE INTO registration_report(form_type, form_title, unverified, stamped, registration, processing, verified, total)"
            + " VALUES"
            + " (:formType, :formTitle, :unverified, :stamped, :registration, :processing, :verified,:total)")
    void saveRegistrationReport(@BindBean List<RegistrationReport> registrationReports);

    @SqlQuery("SELECT * FROM sifaris_report")
    @RegisterBeanMapper(RegistrationReport.class)
    List<RegistrationReport> getAllRegistrationReport();

    @UseClasspathSqlLocator
    @SqlQuery("detail_filled_online_sifaris_form")
    @RegisterBeanMapper(OnlineSifarisDetailsFieldDTO.class)
    List<OnlineSifarisDetailsFieldDTO> getAllCertificates(@Define String databaseTableName);
}

