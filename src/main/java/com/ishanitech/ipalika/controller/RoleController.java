/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 13, 2020
 */
package com.ishanitech.ipalika.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalika.dto.ResponseDTO;
import com.ishanitech.ipalika.model.Role;
import com.ishanitech.ipalika.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseDTO<List<Role>> getAllRoles(){
		return new ResponseDTO<List<Role>>(roleService.getAllRoles());
	}
}
