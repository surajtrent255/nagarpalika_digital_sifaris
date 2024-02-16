package com.ishanitech.ipalika.service.impl;

import com.ishanitech.ipalika.config.properties.MicroServiceUrl;
import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.dao.OnlinSifarisFormDAO;
import com.ishanitech.ipalika.dao.RegistrationReportDAO;
import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.security.CustomUserDetails;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.MailService;
import com.ishanitech.ipalika.service.RegistrationReportService;
import com.ishanitech.ipalika.utils.HttpUtils;
import com.ishanitech.ipalika.utils.ImageUtilService;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.JdbiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.client.RestTemplate;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
@Service
@Slf4j
public class RegistrationReportServiceImpl implements  RegistrationReportService{
    private final RegistrationReportDAO registrationReportDAO;
    private final MailService mailService;
    @Autowired
    private RestBaseProperty restUrlProperty;
    private DbService dbService;
    private HashMap<String, String> tableList = new HashMap<>();
    private final RestTemplate restTemplate;
    @Value("${letter.bluePrint}")
    private String letterBluePrintPathValue;
    @Value("${letter.html}")
    private String htmlPathValue;
    @Value("${letter.pdf}")
    private String pdfPathValue;

    private MicroServiceUrl microServiceUrl;
    public RegistrationReportServiceImpl(DbService dbService, MailService mailService, MicroServiceUrl microServiceUrl, RestTemplate restTemplate) {
        this.dbService = dbService;
        this.registrationReportDAO = dbService.getDao(RegistrationReportDAO.class);
        this.mailService = mailService;
        this.microServiceUrl = microServiceUrl;
        this.restTemplate = restTemplate;
    }


    public void saveRegistrationReports(List<RegistrationReport> registrationReports) {

        registrationReportDAO.saveRegistrationReport(registrationReports);
    }

    // just for testing
    @Override
    public void getCounts() {
        int count = registrationReportDAO.getTotalRegistrationCount(tableList.get(0));
        System.out.println("........................................");
        System.out.println(count);
    }

    @Override
    public List<RegistrationReport> getRegistrationReports() {
        return registrationReportDAO.getAllRegistrationReport();
    }

    @Override
    public void populateRegistrationCount() {

    }

    @Override
    public List<OnlineSifarisDetailsFieldDTO> getSifarisListByFormId(Integer id) {
        OnlinSifarisFormDAO onlinSifarisFormDAO;
        List<OnlineSifarisDetailsFieldDTO> allCertificates;
        try{
            onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
            String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(id);
            allCertificates = registrationReportDAO.getAllCertificates(databaseTableName);
            allCertificates.stream().forEach(c ->{
                c.setEmail(onlinSifarisFormDAO.getUserRequiredFieldAnsFromSifarisFormFieldMapper(c.getFormId(),databaseTableName, c.getTokenId(),"EMAIL"));
                c.setPhone(onlinSifarisFormDAO.getUserRequiredFieldAnsFromSifarisFormFieldMapper(c.getFormId(), databaseTableName, c.getTokenId(), "PHONE"));
            });


        } catch(JdbiException jEx){
            throw new CustomSqlException(jEx.getMessage());
        }
        return allCertificates;
    }

    private static String convertToDevanagari(String englishText) {
        return englishText.replaceAll("0", "०").replaceAll("1", "१").replaceAll("2", "२").replaceAll("3", "३").replaceAll("4", "४")
                .replaceAll("5", "५").replaceAll("6", "६").replaceAll("7", "७").replaceAll("8", "८").replaceAll("9", "९");
    }
    @Override
    public void updateCertificateStatus(SifarisMetaDataDTO sifarisMetaData, @AuthenticationPrincipal CustomUserDetails user) {
        OnlinSifarisFormDAO onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
        String username = user.getUser().getEmail();
        Date date = new Date();
        String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(sifarisMetaData.getFormId());
        try {
             onlinSifarisFormDAO.updateStatus(sifarisMetaData.getTokenId(), sifarisMetaData.getStatus(), sifarisMetaData.getFormId(), sifarisMetaData.getRegNumber());
             switch(sifarisMetaData.getStatus()) {
                case 2:
                    onlinSifarisFormDAO.updateAuthorityColumn("status_2_by", username, "status_2_in", date, sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
                    break;
                case 3:
                    onlinSifarisFormDAO.updateAuthorityColumn("status_3_by", username, "status_3_in", date, sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
                    break;
                case 4:
                    onlinSifarisFormDAO.updateAuthorityColumn("status_4_by", username, "status_4_in", date, sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
                    break;
                case 5:
                    onlinSifarisFormDAO.updateAuthorityColumn("status_5_by", username, "status_5_in", date, sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
//                    int approved = onlinSifarisFormDAO.approveSifaris(sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
                    int approved = 1;
                    if(approved == 1){
                        String name = onlinSifarisFormDAO.getUserRequiredFieldAnsFromSifarisFormFieldMapper(sifarisMetaData.getFormId(),databaseTableName, sifarisMetaData.getTokenId(),"NAME");
                        OnlineSifarisDetailsFieldDTO sifarisInfo;
                        sifarisInfo = onlinSifarisFormDAO.getSifarisByTokenId(databaseTableName, sifarisMetaData.getTokenId());
//                        for fetching email and token dynamically from sifaris_form_field_mapper
                        String registrationNumber = onlinSifarisFormDAO.getRegistrationNumber(sifarisMetaData.getFormId(), sifarisMetaData.getTokenId());
                        String email = onlinSifarisFormDAO.getUserRequiredFieldAnsFromSifarisFormFieldMapper(sifarisMetaData.getFormId(),databaseTableName, sifarisMetaData.getTokenId(),"EMAIL");

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String registeredDate = simpleDateFormat.format(new SimpleDateFormat("yyyy-ss-dd HH:mm:ss").parse(onlinSifarisFormDAO.getRegistrationDate(sifarisMetaData.getFormId(), sifarisMetaData.getTokenId())));

                        Date todaysDate = new Date();
                        String todaysFormattedDate = simpleDateFormat.format(todaysDate);
//                        for generating pdf;
                        Path letterBluePrintPath = Paths.get(letterBluePrintPathValue + "letter"+sifarisMetaData.getFormId()+".html");
                        Charset charset = StandardCharsets.UTF_8;

                        String content = new String(Files.readAllBytes(letterBluePrintPath), charset);
                        content = content.replaceAll("%letName%", name);
                        content = content.replaceAll("%date%", convertToDevanagari(todaysFormattedDate));
                        content = content.replaceAll("%registeredDate%", convertToDevanagari(registeredDate));
                        content = content.replaceAll("%signaturePic%", user.getUser().getSignature());
                        content = content.replaceAll("%stampPic%",user.getUser().getStamp());
                        Path htmlPath = Paths.get(htmlPathValue+sifarisMetaData.getTokenId()+"form"+sifarisMetaData.getFormId()+".html");
                        Path pdfPath = Paths.get(pdfPathValue + sifarisMetaData.getTokenId()+"form"+sifarisMetaData.getFormId()+".pdf");
                        Files.write(htmlPath, content.getBytes(charset));

                        String command = "wkhtmltopdf " + htmlPath + " " + pdfPath;
                        Runtime.getRuntime().exec(command);
                        System.out.println("PDF created successfully!");
//                        HtmlConverter.convertToPdf(new FileInputStream(htmlPath.toString()),
//                                new FileOutputStream(pdfPath.toString()));

//                        code finished
//                        anoterh1
                        String message = "";
//                        String registrationNumber = online;
                        message = "तपाई को विद्युत जडान सिफारिस स्वीकृत भयो "
                                +" कृपया  कायालय आउदा तल  इमेल मा भयको pdf फिले लेदइ आउनु होसे  "
                                + " तपाई को दर्ता नम्बर "+ registrationNumber +"."
                                + " तपाई को  टोकन  नम्बर " + sifarisInfo.getTokenId() + ".";
//                        Map<String, String> letterInfo = new HashMap<>();
//                        letterInfo.put("fileName", sifarisMetaData.getTokenId()+"form"+sifarisMetaData.getFormId());
//                        letterInfo.put("email", email);
//                        letterInfo.put("message", message);
//                        letterInfo.put("signaturePic", user.getUser().getSignature());
//                        letterInfo.put("stampPic", user.getUser().getStamp());
//                        letterInfo.put("registrationNumber",registrationNumber);
//                        letterInfo.put("tokenId",sifarisInfo.getTokenId() );
//                        String url = HttpUtils.createRequestUrl(microServiceUrl, "prepareApplication", null);
//                        RequestEntity<Map<String, String>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, letterInfo, MediaType.APPLICATION_JSON, null, url);
//                        ResponseEntity<String> status= restTemplate.exchange(requestEntity, String.class);
//                        String rsMsg = status.getBody();
//                        int rsStcodeValue = status.getStatusCodeValue();
//                        if(rsStcodeValue == 200 && rsMsg.equalsIgnoreCase("true")){
//                            String testing = "some code here";
//
//                        }
                        mailService.sendAccountVerifyEmail(message, email, pdfPath.toString());

                    }
                    break;
            }


        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception : " + jex.getLocalizedMessage());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SifarisDetailDTO> getSifarisByTokenAndFormId(String tokenId, Integer formId) {
        OnlinSifarisFormDAO onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
        String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(formId);
        OnlineSifarisDetailsFieldDTO onlineSifaris;
        List<SifarisDetailDTO> sifarisDetailDTOS ;
        try{
            sifarisDetailDTOS = onlinSifarisFormDAO.getSifarisDetailBluePrint(formId);
            System.out.println("formId = "+formId + " databaseName = " + databaseTableName + " tokenId= "+tokenId);
            onlineSifaris = onlinSifarisFormDAO.getSifarisByTokenId(databaseTableName, tokenId);
            System.out.println("onlineSifaris^^^^^^^^^^^^^^^^^^ = " + onlineSifaris);

            sifarisDetailDTOS.forEach(e->{
                try {
                    Method method = OnlineSifarisDetailsFieldDTO.class.getDeclaredMethod(String.format("getAnswer%d", e.getFieldId()));
                    String ans = (String) method.invoke(onlineSifaris);
                    if(e.getFieldType().equals("IMAGE")){
                        e.setFieldAnswer(ImageUtilService.makeFullImageurl(restUrlProperty, ans));
                    } else{
                        e.setFieldAnswer(ans);
                    }

                } catch (NoSuchMethodException | SecurityException  | IllegalArgumentException | InvocationTargetException
                          ex) {
                    ex.printStackTrace();
                }  catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch(Exception jEx){
            throw new CustomSqlException("Exception : " + jEx.getLocalizedMessage());
        }

        return sifarisDetailDTOS;
    }

    @Override
    public Integer getSifarisPresentStatus(Integer formId, String tokenId) {
        OnlinSifarisFormDAO  onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
        Integer status = null;
        try{
             status =  onlinSifarisFormDAO.getSifarisPresentStatus(formId, tokenId);
        } catch (JdbiException jEx){
            throw  new CustomSqlException("Exception: " + jEx.getLocalizedMessage());
        }
        return status;
    }

    @Override
    public void editOnlineSifarisFormData(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers) {
        int formId= onlineSIfarisFormAnswers.getFormId();
        OnlinSifarisFormDAO onlinSifarisFormDAO;
        try{
            onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
            String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(formId);
            onlinSifarisFormDAO.editSifarisForm(databaseTableName, onlineSIfarisFormAnswers);

        } catch(JdbiException jEx){
            throw new CustomSqlException("something went wrong while inserting form data " + jEx.getMessage());
        }
    }

    @Override
    public Map<String, String> getSifarisLetterMetaData(Integer formId, String tokenId) {
        OnlinSifarisFormDAO onlinSifarisFormDAO;
        Map<String, String> sifarisMetaData;
        try{
            onlinSifarisFormDAO = dbService.getDao(OnlinSifarisFormDAO.class);
            String databaseTableName = onlinSifarisFormDAO.getDatabaseTableName(formId);
            sifarisMetaData = onlinSifarisFormDAO.getSifarisLetterMetaData(formId, tokenId, databaseTableName);
        } catch(JdbiException jEx){
            throw new CustomSqlException("Something went wrong while " + jEx.getMessage());
        }
        return sifarisMetaData;
    }
}


