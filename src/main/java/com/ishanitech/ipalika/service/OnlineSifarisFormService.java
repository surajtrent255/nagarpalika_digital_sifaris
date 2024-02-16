package com.ishanitech.ipalika.service;

import com.ishanitech.ipalika.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormFieldsDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormRecordsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OnlineSifarisFormService {


    List<OnlineSifarisFormFieldsDTO> getFullFormDetailById(int id);

    void addOnlineSifarisFormData(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers);

    void addOnlineSifarisImage(MultipartFile image);

    void editOnlineSifarisImage(MultipartFile image);
}
