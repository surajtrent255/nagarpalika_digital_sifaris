package com.ishanitech.ipalika.service.impl;


import com.ishanitech.ipalika.converter.impl.UserConverter;
import com.ishanitech.ipalika.dao.UserDAO;
import com.ishanitech.ipalika.dto.EmailDTO;
import com.ishanitech.ipalika.dto.UserDTO;
import com.ishanitech.ipalika.dto.UserRegistrationDTO;
import com.ishanitech.ipalika.dto.VerificationTokenDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.EntityNotFoundException;
import com.ishanitech.ipalika.exception.model.UserRegistrationException;
import com.ishanitech.ipalika.model.Role;
import com.ishanitech.ipalika.model.User;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.EmailService;
import com.ishanitech.ipalika.service.UserService;
import com.ishanitech.ipalika.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * {@code UserServiceImpl} is an implementation class of 
 * {@link com.ishanitech.ipalika.service.UserService} interface which does all the 
 * user related CRUD operations.
 * @author Umesh Bhujel
 * @since 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private DbService dbService;
	private PasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	@Value("${webservice.host-url}")
	private String hostUrl;

	@Autowired
	private VerificationTokenService verificationTokenService;

	public UserServiceImpl(DbService dbService, PasswordEncoder encoder) {
		this.dbService = dbService;
		this.encoder = encoder;
	}

	@Override
	public User getUserByUsername(String username) throws UsernameNotFoundException {
		UserDAO userDao = dbService.getDao(UserDAO.class);
		User user = null;
		try {
			user = userDao.getUserByUsername(username);
		} catch(UnableToExecuteStatementException ex) {
			log.info("Exception occured: " + ex.getMessage());
		}
		
		if(user != null) {
			return user;
		}
		
		throw new UsernameNotFoundException("Incorrect Credentials!");
	}

	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@Override
	public void addUser(UserRegistrationDTO userDto) {
		UserDAO userDao = dbService.getDao(UserDAO.class);
		User user = new User(); //UserConverter().fromDto(userDto);
		user.setEmail(userDto.getEmail());
		user.setEnabled(true);
		user.setExpired(false);
		user.setFirstLogin(false);
		user.setUsername(userDto.getUsername());
		user.setMobileNumber(userDto.getMobileNumber());
		user.setWardNo(userDto.getWardNo());
		user.setRegisteredDate(new Date());
		user.setLocked(false);
		Role role = new Role();
		role.setId(userDto.getAccountType());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setFullName(userDto.getFullName());
		
		 try {
			 userDao.addUserAndRole(user, userDto.getAccountType());
		 } catch (JdbiException jex) {
			 log.error(String.format("Error occured while inserting a user: %s", jex.getLocalizedMessage()));
		 }
	}

	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@Override
	public void deleteUser(int userId) {
		try {
			dbService.getDao(UserDAO.class).deleteUser(userId);
		} catch (JdbiException jex) {
			log.error(String.format("Error occured while deleting a user: userId%d %s", userId, jex.getLocalizedMessage()));
		}
	}

	@PreAuthorize("#userId == authentication.principal.user.userId")
	@Override
	public void updateUserInfoByUserId(Map<String, Object> user, int userId) {
		try {
			User userInfo = this.getUserById(userId);
			if(user.containsKey("mobileNumber")) {
				userInfo.setMobileNumber((String)user.get("mobileNumber"));
			}
			
			if(user.containsKey("fullName")) {
				userInfo.setFullName((String)user.get("fullName"));
			}
			dbService.getDao(UserDAO.class).updateUserInfo(userInfo, userId);
		} catch(JdbiException jex) {
			log.error(String.format("Error occured while updating userinfo %s", jex.getLocalizedMessage()));
		}
	}

	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@Override
	public void disableUser(int userId) {
		try {
			dbService.getDao(UserDAO.class).disableUser(userId);
		} catch (JdbiException jex) {
			log.error(String.format("Error occured while disabling a user: userId%d %s", userId, jex.getLocalizedMessage()));
		}
	}

	@PreAuthorize("#userId == authentication.principal.user.userId")
	@Override
	public void changePassword(String newPassword, int userId) {
		String encryptedPassword = this.encoder.encode(newPassword.replaceAll("\"", ""));
		try {
			dbService.getDao(UserDAO.class).changePassword(encryptedPassword, userId);
		} catch(JdbiException jex) {
			log.error(String.format("Something went wrong while changing user password: %s", jex.getLocalizedMessage()));
		}
	}

	@Override
	public User getUserById(int userId) {
		User user = null;
		try {
			user = dbService.getDao(UserDAO.class).getUserById(userId);
		} catch(JdbiException jex) {
			log.error(String.format("Something went wrong while getting userinfo %s", jex.getLocalizedMessage()));
		}
		
		if(user != null) {
			return user; 
		}
		
		throw new EntityNotFoundException("User not found");
	}
	
	@Override
	public List<UserDTO> getAllUserInfo(int userId) {
		try {
		List<UserDTO> users;
		int roleId = dbService.getDao(UserDAO.class).getRoleIdFromUserId(userId);
		System.out.println("roleid====> "+ dbService.getDao(UserDAO.class) );
		List<User> userInfo =  dbService.getDao(UserDAO.class).getAllUserInfo(roleId, userId);
		users = new UserConverter().fromEntity(userInfo);
		return users;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception : " + jex.getLocalizedMessage());
		}
	}
	
	@Override
	public void updateUserInfoByAdmin(Map<String, Object> user, int userId) {
		try {
			User userInfo = this.getUserById(userId);
			if(user.containsKey("mobileNumber")) {
				userInfo.setMobileNumber((String)user.get("mobileNumber"));
			}

			if(user.containsKey("userName")) {
				userInfo.setUsername((String)user.get("userName"));
			}
			
			if(user.containsKey("email")) {
				userInfo.setEmail((String)user.get("email"));
			}
			
			if(user.containsKey("fullName")) {
				userInfo.setFullName((String)user.get("fullName"));
			}
			
			if(user.containsKey("wardNo")) {
				userInfo.setWardNo(Integer.parseInt((String) user.get("wardNo")));
			}
			if(user.containsKey("role")) {
				int roleId = Integer.parseInt((String) user.get("role"));
				dbService.getDao(UserDAO.class).updateRoleInfo(roleId, userId);
			}		

			dbService.getDao(UserDAO.class).updateUserInfo(userInfo, userId);
		} catch(JdbiException jex) {
			log.error(String.format("Error occured while updating userinfo %s", jex.getLocalizedMessage()));
		}
	}

	@Override
	public UserDTO getUserInfoByUserId(int userId) {
		try {
		UserDTO user;
		User userInfo =  dbService.getDao(UserDAO.class).getUserInfoByUserId(userId);
		user = new UserConverter().fromEntity(userInfo);
		return user;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception : " + jex.getLocalizedMessage());
		}
	}


	// Checks the userParameter for potential duplicate values in user table.
	@Override
	public Map<String, Boolean> checkDuplicateEntryParams(Map<String, String> userParameters) {
		Map<String, Boolean> result = new HashMap<>();
		
		// userParameters key must match with column name in user table.
		for (Map.Entry<String, String> entry : userParameters.entrySet()) {
			Boolean res = dbService.getDao(UserDAO.class).checkDuplicateUserParams(entry.getKey(), entry.getValue());
			result.put(entry.getValue(), res != null ? true : false);
		}
		
		return result;
	}
	
	@Override
	public void changePasswordByAdmin(String newPassword, int userId) {
		String encryptedPassword = this.encoder.encode(newPassword.replaceAll("\"", ""));
		try {
			dbService.getDao(UserDAO.class).changePassword(encryptedPassword, userId);
		} catch(JdbiException jex) {
			log.error(String.format("Something went wrong while changing user password: %s", jex.getLocalizedMessage()));
		}
	}


	@Override
	public void registerNormalUser(UserRegistrationDTO userDto) {

		UserDAO userDao = dbService.getDao(UserDAO.class);
		User user = new User(); //UserConverter().fromDto(userDto);
		user.setEmail(userDto.getEmail());
		user.setEnabled(false);
		user.setExpired(false);
		user.setFirstLogin(false);
		user.setUsername(userDto.getUsername());
		user.setMobileNumber(userDto.getMobileNumber());
		user.setWardNo(userDto.getWardNo());
		user.setRegisteredDate(new Date());
		user.setLocked(false);
		Role role = new Role();
		role.setId(userDto.getAccountType());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setFullName(userDto.getFullName());

		try {

			Integer registeredUserId = userDao.addUserAndRole(user, userDto.getAccountType());
			if( registeredUserId != null) {
				String token = UUID.randomUUID().toString();
				VerificationTokenDTO verificationToken = new VerificationTokenDTO(token, registeredUserId);

				//insert into VerificationToken table
				verificationTokenService.addVerificationToken(verificationToken);

				EmailDTO emailingDetail = new EmailDTO();
				emailingDetail.setFrom("no-reply@ishanitech.com");
				emailingDetail.setTo(user.getEmail());
				emailingDetail.setSubject("Account Activation Email");
				Map<String, Object> map = new HashMap<>();
				map.put("cname", user.getFullName());
				map.put("activationLink", hostUrl + "registration/registrationConfirm?token=" + token);
//				map.put("signature", "http://www.ishanitech.com/ishanitech");
				emailingDetail.setModel(map);
				emailService.sendAccountActivationEmail(emailingDetail, "normalUserRegistrationMsg");
//				return user;
			}
		} catch(Exception ex){
			log.error(ex.getMessage());
			throw new UserRegistrationException("Something went wrong while registring user");
		}
	}

	@Override
	public boolean checkEmail(String email) {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		UserDTO user = userDAO.getUserByEmail(email);
		return user != null ? true : false;
	}

	@Override
	public String resetPassword(String email) {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		UserDTO user = userDAO.getUserByEmail(email);
		try {
			Integer userId =  user.getUserId();
			if( userId != null) {
				String token = UUID.randomUUID().toString();
				VerificationTokenDTO verificationToken = new VerificationTokenDTO(token, userId);

				//insert into VerificationToken table
				verificationTokenService.addVerificationToken(verificationToken);

				EmailDTO emailingDetail = new EmailDTO();
				emailingDetail.setFrom("no-reply@ishanitech.com");
				emailingDetail.setTo(user.getEmail());
				emailingDetail.setSubject("Reset Password");
				Map<String, Object> map = new HashMap<>();
				map.put("cname", user.getFullName());
				map.put("resetLink", hostUrl +"forgot-password/verifyPasswordToken?token=" + token);
//				map.put("signature", "http://www.ishanitech.com/ishanitech");
				emailingDetail.setModel(map);
				emailService.sendAccountActivationEmail(emailingDetail, "pw");
				return "Success";
			}
			throw new UserRegistrationException("Could not send reset email to the user.");
		} catch(Exception ex){
			log.error(ex.getMessage());
			throw new UserRegistrationException("Something went wrong while resetting password");
		}
	}

	@Override
	public void updateUserPassword(Integer userId, String password) {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		userDAO.updateUserPassword(encoder.encode(password.replaceAll("\"", "")), userId);
	}
}
