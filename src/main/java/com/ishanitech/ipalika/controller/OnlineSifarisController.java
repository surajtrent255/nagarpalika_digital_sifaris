package com.ishanitech.ipalika.controller;

import com.ishanitech.ipalika.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormFieldsDTO;
import com.ishanitech.ipalika.dto.OnlineSifarisFormRecordsDTO;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.exception.FileStorageException;
import com.ishanitech.ipalika.service.OnlineSifarisFormService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/online-sifaris")
public class OnlineSifarisController {

    private final OnlineSifarisFormService onlineSifarisFormService;

    public OnlineSifarisController(OnlineSifarisFormService onlineSifarisFormService){
        this.onlineSifarisFormService = onlineSifarisFormService;
    }

    @GetMapping("/formData/{formId}")
    public ResponseDTO<List<OnlineSifarisFormFieldsDTO>> getAllFormFieldsByFormId(@PathVariable("formId") int formId){
        return new ResponseDTO<List<OnlineSifarisFormFieldsDTO>>(this.onlineSifarisFormService.getFullFormDetailById(formId));
    }

    @PostMapping("/formData")
    public void addOnlineSifarisFormByFormId(@RequestBody OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswers){
        onlineSifarisFormService.addOnlineSifarisFormData(onlineSIfarisFormAnswers);
    }

    @PostMapping("/formData/image")
    public void uploadSifarisImage(@RequestParam("picture") MultipartFile image) throws FileStorageException {
        onlineSifarisFormService.addOnlineSifarisImage(image);
    }

    @PostMapping("/formData/image/edit")
    public void editSifarisImage(@RequestParam("picture") MultipartFile image) throws FileStorageException {
        System.out.println("********************************************* suraj");
        onlineSifarisFormService.editOnlineSifarisImage(image);
    }
}
