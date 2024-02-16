/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 18, 2020
 */
package com.ishanitech.ipalika.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.dto.ToleDTO;
import com.ishanitech.ipalika.dto.WardDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.FileStorageException;
import com.ishanitech.ipalika.servicer.WardService;

@RestController
@RequestMapping("/ward")
public class WardController {
	private final WardService wardService;
	
	public WardController(WardService wardService) {
		this.wardService = wardService;
	}

	@GetMapping
	public ResponseDTO<List<Integer>> getAllWardsNumbers() {
		List<Integer> wards = wardService.getAllWardNumbers();
		return new ResponseDTO<List<Integer>>(wards);
	}
	
	@GetMapping("/toles")
	public ResponseDTO<List<ToleDTO>> getAllToles() {
		List<ToleDTO> tolesMap = wardService.getAllToles();
		return new ResponseDTO<List<ToleDTO>>(tolesMap);
	}
	
	@PostMapping
	public void addWard(HttpServletResponse http, @RequestBody WardDTO wardInfo) {
		wardService.addWard(wardInfo);
	}
	
	@PutMapping("/{wardNumber}")
	public void updateWardInfoByWardNumber(@RequestBody WardDTO wardInfo, @PathVariable("wardNumber") int wardNo) {
		wardService.updateWardInfoByWardNumber(wardInfo, wardNo);
	}
	
	@DeleteMapping("/{wardNumber}")
	public void deleteWardByWardNumber(@PathVariable("wardNumber") int wardNo) {
		wardService.deleteWardByWardNumber(wardNo);
	}
	
	@GetMapping("/{wardNumber}")
	public ResponseDTO<WardDTO> getWardByWardNumber(@PathVariable("wardNumber") int wardNo) throws CustomSqlException {
		return new ResponseDTO<WardDTO>(wardService.getWardByWardNumber(wardNo));
	}
	
	@GetMapping("/all") 
	public ResponseDTO<List<WardDTO>> getAllWardsInfo() throws CustomSqlException {
		return new ResponseDTO<List<WardDTO>>(wardService.getAllwardsInfo());
	}
	
	@GetMapping("/totalHouseCount/{wardNumber}") 
	public ResponseDTO<Integer> getTotalHouseCountByWard(@PathVariable("wardNumber") int wardNo, @RequestParam(name= "toleName", required=false) String toleName) throws CustomSqlException {
		System.out.println("endpoint called ward--->" + wardNo);
		if(toleName != null)
		toleName = toleName.replace("spacex", " ");
		return new ResponseDTO<Integer>(wardService.getHouseCountByWard(wardNo, toleName));
	}
	
	/**
	 * Uploads the image for ward building using MultipartFile
	 * @param picture MultipartFile image
	 * @return void
	 * @throws FileStorageException if there is error on storing file
	 * @author <b> Pujan KC </b>
	 * @since 1.0
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/image")
	public void uploadImageWardBuilding(@RequestParam("picture") MultipartFile image) throws FileStorageException {
		wardService.addWardBuilginImage(image);
	}
}
