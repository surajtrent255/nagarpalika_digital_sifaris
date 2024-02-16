package com.ishanitech.ipalika.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.service.UserService;

@RequestMapping("/super-admin")
@RestController
public class SuperAdminController {
	private final UserService userService;
	
	public SuperAdminController(UserService userService) {
		this.userService = userService;
	}
}
