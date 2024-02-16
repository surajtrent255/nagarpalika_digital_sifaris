package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.dto.VerificationTokenDTO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface VerificationTokenDAO {
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO activation_key(user_id, validation_key) VALUES (:userId, :validationKey)")
    int addVerificationToken(@BindBean VerificationTokenDTO verificationToken);

    @SqlQuery("SELECT * FROM activation_key WHERE validation_key = :validationKey")
    @RegisterBeanMapper(VerificationTokenDTO.class)
    VerificationTokenDTO getVerificationToken(@Bind("validationKey") String validationKey);

    @SqlUpdate("DELETE FROM activation_key WHERE validation_key = :validationKey")
    void deleteToken(@Bind("validationKey") String token);
}