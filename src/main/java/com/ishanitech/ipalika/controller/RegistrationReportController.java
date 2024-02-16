package com.ishanitech.ipalika.controller;

import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.security.CustomUserDetails;
import com.ishanitech.ipalika.service.RegistrationReportService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RequestMapping("/registration-report")
//@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
@RestController
public class RegistrationReportController {
    private final RegistrationReportService registrationReportService;

    public RegistrationReportController(RegistrationReportService registrationReportService) {
        this.registrationReportService = registrationReportService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseDTO<List<RegistrationReport>> getRegistrationReport(HttpServletRequest request) {
//    	registrationReportService.populateRegistrationCount();
        return new ResponseDTO<List<RegistrationReport>>(registrationReportService.getRegistrationReports());
    }

    @GetMapping("/sifarisList/{id}")
    public ResponseDTO<List<OnlineSifarisDetailsFieldDTO>> getSifarisListByFormId(
            @PathVariable("id") Integer id
    ){
        return new ResponseDTO<List<OnlineSifarisDetailsFieldDTO>>(this.registrationReportService.getSifarisListByFormId(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/form/status/{tokenId}")
    public void updateCertificateStatusByTokenId(
            @PathVariable("tokenId") String tokenId,
            @RequestBody SifarisMetaDataDTO sifarisMetaData,
            @AuthenticationPrincipal CustomUserDetails user
    )
            throws EntityNotFoundException {
        registrationReportService.updateCertificateStatus(sifarisMetaData, user);
    }

    @GetMapping("/sifaris/{tokenId}/{formId}")
    public ResponseDTO<List<SifarisDetailDTO>> getSifarisDetailViewByFormId(
            @PathVariable("tokenId") String tokenId,
            @PathVariable("formId") Integer formId
    ){
        return new ResponseDTO<List<SifarisDetailDTO>>(this.registrationReportService.getSifarisByTokenAndFormId(tokenId, formId));
    }

    @PutMapping("/sifaris/edit")
    public void addOnlineSifarisFormByFormId(@RequestBody OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers){
        registrationReportService.editOnlineSifarisFormData(onlineSIfarisFormAnswers);
    }

    @GetMapping("/sifaris/status/{tokenId}/{formId}")
    public ResponseDTO<Integer> getSifarisPresentStatus(
            @PathVariable("formId") Integer formId,
            @PathVariable("tokenId") String tokenId
    ){
        return new ResponseDTO<Integer>(this.registrationReportService.getSifarisPresentStatus(formId, tokenId));
    }

    @GetMapping("/sifaris/letter/metadata/{tokenId}/{formId}")
    public ResponseDTO<Map<String, String>> getSifarisLetterMetaData(
            @PathVariable("formId") Integer formId,
            @PathVariable("tokenId") String tokenId
    ){
        return new ResponseDTO<Map<String,String>>(this.registrationReportService.getSifarisLetterMetaData(formId, tokenId));
    }
}
