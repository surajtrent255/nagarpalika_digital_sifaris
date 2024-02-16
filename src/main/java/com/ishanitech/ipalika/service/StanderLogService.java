package com.ishanitech.ipalika.service;


import com.ishanitech.ipalika.dto.StanderLogDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StanderLogService {


    List<StanderLogDTO> getStanderLoglist(HttpServletRequest request);

    List<StanderLogDTO> getStanderLoglistBYFormId(int formId);


    List<StanderLogDTO> getStanderLogByTokenId(String tokenId);

    List<StanderLogDTO> getStanderLoglistBYstatus(int status);
}