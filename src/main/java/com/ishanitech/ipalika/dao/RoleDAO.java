/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 13, 2020
 */
package com.ishanitech.ipalika.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.ishanitech.ipalika.model.Role;

public interface RoleDAO {
	
	@SqlQuery("SELECT * FROM role")
	@RegisterBeanMapper(Role.class)
	List<Role> getAllRoles();
}
