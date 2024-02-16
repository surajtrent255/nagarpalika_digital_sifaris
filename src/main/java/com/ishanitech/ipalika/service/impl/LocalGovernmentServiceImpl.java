package com.ishanitech.ipalika.service.impl;

import com.ishanitech.ipalika.converter.impl.LgDistrictConverter;
import com.ishanitech.ipalika.converter.impl.LgMunicipalityConverter;
import com.ishanitech.ipalika.converter.impl.LgProvinceConverter;
import com.ishanitech.ipalika.converter.impl.LgWardConverter;
import com.ishanitech.ipalika.dao.LgDistrictDAO;
import com.ishanitech.ipalika.dao.LgMunicipalityDAO;
import com.ishanitech.ipalika.dao.LgProvinceDAO;
import com.ishanitech.ipalika.dao.LgWardDAO;
import com.ishanitech.ipalika.dto.LgDistrictDTO;
import com.ishanitech.ipalika.dto.LgMunicipalityDTO;
import com.ishanitech.ipalika.dto.LgProvinceDTO;
import com.ishanitech.ipalika.dto.LgWardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.model.LgDistrict;
import com.ishanitech.ipalika.model.LgMunicipality;
import com.ishanitech.ipalika.model.LgProvince;
import com.ishanitech.ipalika.model.LgWard;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.LocalGovernmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.JdbiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class LocalGovernmentServiceImpl implements LocalGovernmentService {
    private DbService dbService;

//	private final LgProvinceMapper provinceMapper = Mappers.getMapper(LgProvinceMapper.class);
//	private final LgDistrictMapper districtMapper = Mappers.getMapper(LgDistrictMapper.class);
//	private final LgMunicipalityMapper municipalityMapper = Mappers.getMapper(LgMunicipalityMapper.class);
//	private final LgWardMapper wardMapper = Mappers.getMapper(LgWardMapper.class);

//	LgProvinceDAO provinceDao = dbService.getDao(LgProvinceDAO.class);
//	LgDistrictDAO districtDao = dbService.getDao(LgDistrictDAO.class);
//	LgMunicipalityDAO municipalityDao = dbService.getDao(LgMunicipalityDAO.class);
//	LgWardDAO wardDao = dbService.getDao(LgWardDAO.class);

    @Override
    public List<LgProvinceDTO> getAllProvinces() {
        LgProvinceDAO provinceDao = dbService.getDao(LgProvinceDAO.class);

        List<LgProvinceDTO> provinceDtos = new ArrayList<>();
        List<LgProvince> provinceInfo;

        try {
            provinceInfo = provinceDao.getAllProvinces();
            provinceDtos = new LgProvinceConverter().fromEntity(provinceInfo);
            return provinceDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgDistrictDTO> getAllDistricts() {
        LgDistrictDAO districtDao = dbService.getDao(LgDistrictDAO.class);

        List<LgDistrictDTO> districtDtos = new ArrayList<>();
        List<LgDistrict> districtInfo;

        try {
            districtInfo = districtDao.getDistrictsByProvinceId(3);
            districtDtos = new LgDistrictConverter().fromEntity(districtInfo);
            return districtDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception:  districtDtos :" + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgMunicipalityDTO> getAllMunicipalities() {
        LgMunicipalityDAO municipalityDao = dbService.getDao(LgMunicipalityDAO.class);

        List<LgMunicipalityDTO> municipalityDtos = new ArrayList<>();
        List<LgMunicipality> municipalityInfo;

        try {
            municipalityInfo = municipalityDao.getAllMunicipalities();
            municipalityDtos = new LgMunicipalityConverter().fromEntity(municipalityInfo);
            return municipalityDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception:municipalityInfo: " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgWardDTO> getAllWards() {
        LgWardDAO wardDao = dbService.getDao(LgWardDAO.class);

        List<LgWardDTO> wardDtos = new ArrayList<>();
        List<LgWard> wardInfo;

        try {
            wardInfo = wardDao.getAllWards();
            wardDtos = new LgWardConverter().fromEntity(wardInfo);
            return wardDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception:  wardInfo " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgDistrictDTO> getDistrictsByProvinceId(int provinceId) {
        LgDistrictDAO districtDao = dbService.getDao(LgDistrictDAO.class);

        List<LgDistrictDTO> districtDtos = new ArrayList<>();
        List<LgDistrict> districtInfo;

        try {
            districtInfo = districtDao.getDistrictsByProvinceId(provinceId);
            districtDtos = new LgDistrictConverter().fromEntity(districtInfo);
            return districtDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgMunicipalityDTO> getMunicipalitiesByDistrictId(int districtId) {
        LgMunicipalityDAO municipalityDao = dbService.getDao(LgMunicipalityDAO.class);

        List<LgMunicipalityDTO> municipalityDtos = new ArrayList<>();
        List<LgMunicipality> municipalityInfo;

        try {
            municipalityInfo = municipalityDao.getMunicipalitiesByDistrictId(districtId);
            municipalityDtos = new LgMunicipalityConverter().fromEntity(municipalityInfo);
            return municipalityDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
        }
    }

    @Override
    public List<LgWardDTO> getWardsByMunicipalityId(int municipalityId) {
        LgWardDAO wardDao = dbService.getDao(LgWardDAO.class);

        List<LgWardDTO> wardDtos = new ArrayList<>();
        List<LgWard> wardInfo;

        try {
            wardInfo = wardDao.getWardsByMunicipalityId(municipalityId);
            wardDtos = new LgWardConverter().fromEntity(wardInfo);
            return wardDtos;
        } catch (JdbiException jex) {
            throw new CustomSqlException("Exception: LocalGovernmentServiceImpl : " + jex.getLocalizedMessage());
        }
    }

}
