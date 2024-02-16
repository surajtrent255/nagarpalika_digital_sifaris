/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 13, 2020
 */
package com.ishanitech.ipalika.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.dao.RoleDAO;
import com.ishanitech.ipalika.model.Role;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private final DbService dbService;
	
	public RoleServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}

	@Override
	public List<Role> getAllRoles() {
		return dbService.getDao(RoleDAO.class).getAllRoles();
	}

}
