/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 18, 2020
 */
package com.ishanitech.ipalika.servicer;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.dto.ToleDTO;
import com.ishanitech.ipalika.dto.WardDTO;

public interface WardService {
	List<Integer> getAllWardNumbers();

	void addWard(WardDTO wardInfo);

	void updateWardInfoByWardNumber(WardDTO wardInfo, int wardNo);

	void deleteWardByWardNumber(int wardNo);

	WardDTO getWardByWardNumber(int wardNo);

	List<WardDTO> getAllwardsInfo();
	
	Integer getHouseCountByWard(int wardNo, String toleName);

	void addWardBuilginImage(MultipartFile image);

	List<ToleDTO> getAllToles();
}
