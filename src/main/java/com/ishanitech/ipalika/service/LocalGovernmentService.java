
package com.ishanitech.ipalika.service;

import com.ishanitech.ipalika.dto.LgDistrictDTO;
import com.ishanitech.ipalika.dto.LgMunicipalityDTO;
import com.ishanitech.ipalika.dto.LgProvinceDTO;
import com.ishanitech.ipalika.dto.LgWardDTO;

import java.util.List;

public interface LocalGovernmentService {
    List<LgProvinceDTO> getAllProvinces();

    List<LgDistrictDTO> getAllDistricts();

    List<LgMunicipalityDTO> getAllMunicipalities();

    List<LgWardDTO> getAllWards();

    List<LgDistrictDTO> getDistrictsByProvinceId(int provinceId);

    List<LgMunicipalityDTO> getMunicipalitiesByDistrictId(int districtId);

    List<LgWardDTO> getWardsByMunicipalityId(int municipalityId);
}
