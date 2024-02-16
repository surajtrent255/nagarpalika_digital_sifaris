package com.ishanitech.ipalika.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Contains the information for new user registration. 
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class UserRegistrationDTO {
	private String fullName;
	private String username;
	private String email;
	private String mobileNumber;
	private String password;
	private int accountType;
	private int wardNo;
}
