package com.ishanitech.ipalika.service;

import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Map;

public interface RegistrationReportService {
    void getCounts();
    List<RegistrationReport> getRegistrationReports();
    void populateRegistrationCount();

    List<OnlineSifarisDetailsFieldDTO> getSifarisListByFormId(Integer id);

    void updateCertificateStatus(SifarisMetaDataDTO sifarisMetaData, @AuthenticationPrincipal CustomUserDetails user);

    List<SifarisDetailDTO> getSifarisByTokenAndFormId(String tokenId, Integer formId);

    Integer getSifarisPresentStatus(Integer formId, String tokenId);

    void editOnlineSifarisFormData(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers);

    Map<String, String> getSifarisLetterMetaData(Integer formId, String tokenId);
}
