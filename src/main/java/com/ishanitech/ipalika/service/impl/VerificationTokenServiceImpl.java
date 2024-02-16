package com.ishanitech.ipalika.service.impl;
import com.ishanitech.ipalika.dao.UserDAO;
import com.ishanitech.ipalika.dao.VerificationTokenDAO;
import com.ishanitech.ipalika.dto.VerificationTokenDTO;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);
    private DbService dbService;
    public VerificationTokenServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int addVerificationToken(VerificationTokenDTO tokenDTO) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        return verificationDao.addVerificationToken(tokenDTO);
    }

    @Override
    public VerificationTokenDTO getVerificationToken(String validationToken) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        return verificationDao.getVerificationToken(validationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);

        VerificationTokenDTO tokenDetails = verificationDao.getVerificationToken(token);

        if(tokenDetails == null) {
            return "INVALID_TOKEN";
        }

        // Calendar calendar = Calendar.getInstance();
        // if((tokenDetails.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
        //     return "TOKEN_EXPIRED";
        // }

        UserDAO userDAO = dbService.getDao(UserDAO.class);
        userDAO.enableUserAccount(tokenDetails.getUserId());
        verificationDao.deleteToken(token);
        return "VALID_TOKEN";
    }

    //Service to check if the token is valid or not
    @Override
    public Integer resetPasswordTokenVerification(String token) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        VerificationTokenDTO tokenDetails = verificationDao.getVerificationToken(token);
        if(tokenDetails != null) {
            verificationDao.deleteToken(token);
            return tokenDetails.getUserId();
        }else {
            return null;
        }
    }

}