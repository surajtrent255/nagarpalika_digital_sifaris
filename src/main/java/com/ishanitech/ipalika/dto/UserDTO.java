package com.ishanitech.ipalika.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 2989312298454528014L;
	private int userId;
	private String username;
	private String fullName;
	private String email;
	@JsonIgnore
	private String password;
	private String mobileNumber;
	private boolean isLocked;
	private boolean enabled;
	private boolean firstLogin;
	private boolean expired;
	private int wardNo;
	private Date registeredDate;
	private List<String> roles;
	private String stamp;
	private String signature;
}
