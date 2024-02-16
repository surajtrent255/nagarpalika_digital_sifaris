package com.ishanitech.ipalika.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.ishanitech.ipalika.model.Form;

/**
 * {@code FormDAO} dao interface which does all CRUD related to form entity.
 * @author <b> Umesh Bhujel </b>
 * @since 1.0
 */
public interface FormDAO {
	
	/**
	 * Inserts the form into the table `form`
	 * @param form entity
	 * @return void
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@SqlUpdate("INSERT INTO form(`form_id`, `form_name`) VALUES (:formId, :formName)")
	public void addForm(@BindBean Form form);

	/**
	 * This method gets from info from form table.
	 * @param id integer id
	 * @author <b> Umesh Bhujel </b>
	 * @since 1.0
	 */
	@SqlQuery("SELECT * FROM form WHERE id = :id")
	@RegisterBeanMapper(Form.class)
	public Form getFormById(@Bind("id") Integer id);

	/**
	 * @param id
	 */
	@SqlUpdate("DELETES FROM form WHERE id = :id")
	public void deleteById(@Bind("id") Integer id);

	/**
	 * @param id
	 * @param form
	 * @return
	 */
	@SqlUpdate("UPDATE form SET form_name = :formName WHERE id = :id")
	public void updateFormById(@Bind Integer id, @BindBean Form form);
}
