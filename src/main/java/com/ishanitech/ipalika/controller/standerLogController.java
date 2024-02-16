package com.ishanitech.ipalika.controller;
import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.dto.StanderLogDTO;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.service.StanderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
@RequestMapping("/online-sifaris")
@RestController
public class standerLogController {
    private final StanderLogService standerLogService;
    public standerLogController(StanderLogService standerLogService) {
        this.standerLogService = standerLogService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/standardlog")
    public ResponseDTO<List<StanderLogDTO>> getStanderLoglist(HttpServletRequest request) {
        return new ResponseDTO<>(standerLogService.getStanderLoglist(request));
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/form/{formId}")
    public ResponseDTO<List<StanderLogDTO>> getStanderLoglistBYFormId(@PathVariable("formId") int formId)
            throws EntityNotFoundException {

        return new ResponseDTO<List<StanderLogDTO>>(standerLogService.getStanderLoglistBYFormId(formId));
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/token/{tokenId}")
    public ResponseDTO<List<StanderLogDTO>> getStanderLogByTokenId(@PathVariable("tokenId") String tokenId)
            throws EntityNotFoundException {
        return new ResponseDTO<List<StanderLogDTO>>(standerLogService.getStanderLogByTokenId(tokenId));
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/{status}")
    public ResponseDTO<List<StanderLogDTO>> getStanderLoglistBYstatus(@PathVariable("status") int status)
            throws EntityNotFoundException {
        return new ResponseDTO<List<StanderLogDTO>>(standerLogService.getStanderLoglistBYstatus(status));
    }
}