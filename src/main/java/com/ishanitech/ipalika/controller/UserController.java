package com.ishanitech.ipalika.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.ishanitech.ipalika.dto.*;
import com.ishanitech.ipalika.exception.model.UserRegistrationException;
import com.ishanitech.ipalika.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.security.CustomUserDetails;
import com.ishanitech.ipalika.service.UserService;


@RequestMapping("/user")
@RestController
public class UserController {
	
	private final UserService userService;
	@Autowired
	private VerificationTokenService verificationTokenService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void createUser(@RequestBody UserRegistrationDTO user) throws CustomSqlException {
		userService.addUser(user);
	}


//	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@PostMapping("/normal/register")
	public void registerNormalUser(@RequestBody UserRegistrationDTO user) throws CustomSqlException{
		userService.registerNormalUser(user);
	}
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") int userId, @AuthenticationPrincipal CustomUserDetails user) {
		userService.deleteUser(userId);
	}
	
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@PutMapping("/{userId}/disable")
	public void disableUser(@PathVariable("userId") int userId) {
		userService.disableUser(userId);
	}
	
	
	@PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUserInfo(@PathVariable("userId") int userId,
			@RequestBody Map<String, Object> updates) {
		userService.updateUserInfoByUserId(updates, userId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userId}/password")
	public void changePassword(@RequestBody String password, @PathVariable("userId") int userId) {
		userService.changePassword(password, userId);
	}
	
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@GetMapping
	public ResponseDTO<List<UserDTO>> getAllUserInfo(@AuthenticationPrincipal CustomUserDetails user) throws CustomSqlException {
		return new ResponseDTO<List<UserDTO>> (userService.getAllUserInfo(user.getUser().getUserId()));
	}

	@PostMapping(value = "/duplicate")
	public ResponseDTO<Map<String, Boolean>> checkParameter(@RequestBody Map<String, String> userParameters) {
		return new ResponseDTO<Map<String, Boolean>>(userService.checkDuplicateEntryParams(userParameters));
	}
	

	@Secured("ROLE_SUPER_ADMIN")
	@GetMapping("/{userId}")
	public ResponseDTO<UserDTO> getUserInfoByUserId(@PathVariable("userId") int userId) throws CustomSqlException {
		return new ResponseDTO<UserDTO> (userService.getUserInfoByUserId(userId));
	}
	
	@PatchMapping(value = "edit/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUserInfoByAdmin(@PathVariable("userId") int userId,
			@RequestBody Map<String, Object> updates) {
		userService.updateUserInfoByAdmin(updates, userId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userId}/pass-reset-admin")
	public void changePasswordByAdmin(@RequestBody String password, @PathVariable("userId") int userId) {
		userService.changePasswordByAdmin(password, userId);
	}
	@GetMapping("/activateUser")
	public ResponseSec<?> activateUserAccount(@RequestParam("token") String token) {
		String tokenVerificationStatus = verificationTokenService.validateVerificationToken(token);
		if (tokenVerificationStatus.equals("INVALID_TOKEN")) {
			throw new UserRegistrationException("Account activation unsuccessful because of invalid activation token!");
		} else {
			return ResponseSec.ok(new ArrayList<>(), HttpStatus.OK.value(),
					"Your account has been activated. Now you can login to our system.");
		}
	}




}
