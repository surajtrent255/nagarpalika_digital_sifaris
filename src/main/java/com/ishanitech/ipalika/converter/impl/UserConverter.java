package com.ishanitech.ipalika.converter.impl;

import java.util.Date;
import java.util.stream.Collectors;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.converter.BaseConverter;
import com.ishanitech.ipalika.dto.UserDTO;
import com.ishanitech.ipalika.model.User;
import com.ishanitech.ipalika.utils.ImageUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *Converter class to convert from and to User and UserDto
 *@author <b> Umesh Bhujel
 *@since 1.0
 */
@Component
public class UserConverter extends BaseConverter<User, UserDTO> {

	@Autowired
	private RestBaseProperty restUrlProperty;
	/**
	 * Converts {@code UserDTO} object to {@code User} entity object.
	 * @param dto UserDTO object
	 * @return {@code User} entity object
	 */
	@Override
	public User fromDto(UserDTO dto) {
		User user = new User();
		user.setFullName(dto.getFullName());
		user.setWardNo(dto.getWardNo());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setMobileNumber(dto.getMobileNumber());
		user.setRegisteredDate(new Date());
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setLocked(false);
		user.setExpired(false);
		return user;
	}

	/**
	 * Converts {@code User} entity object to {@code UserDTO} object.
	 * @param entity User entity object
	 * @return {@code UserDTO} object.
	 */
	@Override
	public UserDTO fromEntity(User entity) {
		RestBaseProperty rb = new RestBaseProperty();
		rb.setDomain("localhost");
		rb.setPort("8888");
		rb.setProtocol("http");
		rb.setResourceLocation("resource");
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		userDTO.setEmail(entity.getEmail());
		userDTO.setFullName(entity.getFullName());
		userDTO.setMobileNumber(entity.getMobileNumber());
		userDTO.setLocked(entity.isLocked());
		userDTO.setEnabled(entity.isEnabled());
		userDTO.setExpired(entity.isExpired());
		userDTO.setFirstLogin(entity.isFirstLogin());
		userDTO.setWardNo(entity.getWardNo());
		userDTO.setRoles(entity.getRole().stream().map(role -> role.getRole()).collect(Collectors.toList()));
		userDTO.setRegisteredDate(entity.getRegisteredDate());
		userDTO.setStamp(ImageUtilService.makeFullImageurl(rb, entity.getStamp()));
		userDTO.setSignature(ImageUtilService.makeFullImageurl(rb, entity.getSignature()));
		return userDTO;
	}

	//Splits first name, middle name and last name from full name string 
	//and puts it into the string array
	private String[] splitFirstMiddleAndLastName(String fullName) {
		return fullName.split(" ");
	}
}
