package com.ishanitech.ipalika.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * {@code User} represents the user entity.
 * User entity also represents the authenticated user on the system.
 * @author Umesh Bhujel
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 3484530113208289531L;
	private int id;
	private String username;
	private String fullName;
	private String email;
	private boolean expired;
	private boolean firstLogin;
	
	private Date registeredDate;
	private String mobileNumber;
	private int wardNo;
	
	private String password;
	private boolean enabled;
	private boolean locked;
	private String stamp;
	private String signature;
	private Set<Role> role = new HashSet<>();
}
