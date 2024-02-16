package com.ishanitech.ipalika.controller;

import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.service.LocalGovernmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/lg")
@RestController
public class LocalGovernmentController {
    private final LocalGovernmentService localGovernmentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/provinces")
    public Response<?> getAllProvinces() {
        try {
//			log.info(localGovernmentService.getAllProvinces().toString());
            return new Response<List<LgProvinceDTO>>(localGovernmentService.getAllProvinces());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving provinces!");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/provinces/{provinceId}/districts")
    public Response<?> getDistrictsByProvinceId(@PathVariable("provinceId") int provinceId) {
        try {
            return new Response<List<LgDistrictDTO>>(localGovernmentService.getDistrictsByProvinceId(provinceId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving district!");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/districts")
    public Response<?> getAllDistricts() {
        try {
//			log.info(localGovernmentService.getAllDistricts().toString());
            return new Response<List<LgDistrictDTO>>(localGovernmentService.getAllDistricts());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving district!");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/districts/{districtId}/municipalities")
    public Response<?> getMunicipalitiesByDistrictId(@PathVariable("districtId") int districtId) {
        try {
            return new Response<List<LgMunicipalityDTO>>(localGovernmentService.getMunicipalitiesByDistrictId(districtId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving municipalities!");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/municipalities")
    public Response<?> getAllMunicipalities() {
        try {
			log.info(localGovernmentService.getAllMunicipalities().toString());
            return new Response<List<LgMunicipalityDTO>>(localGovernmentService.getAllMunicipalities());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving municipalities!");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/wards")
    public Response<?> getAllWards() {
        try {
            log.info(localGovernmentService.getAllWards().toString());
            return new Response<List<LgWardDTO>>(localGovernmentService.getAllWards());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Something went wrong while retrieving wards!");
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/municipalities/{municipalityId}/wards")
    public Response<?> getWardsByMunicipalityId(@PathVariable("municipalityId") int municipalityId) {
        try {

            return new Response<List<LgWardDTO>>(localGovernmentService.getWardsByMunicipalityId(municipalityId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EntityNotFoundException("Local Government controller Something went wrong while retrieving wards!");
        }
    }


//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/wards")
//    public Response<?> getAllWards() {
//        try {
//			log.info(localGovernmentService.getAllWards().toString());
//            return new Response<List<LgWardDTO>>(localGovernmentService.getAllWards());
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            throw new EntityNotFoundException("Something went wrong while retrieving wards!");
//        }
//
//    }
}
