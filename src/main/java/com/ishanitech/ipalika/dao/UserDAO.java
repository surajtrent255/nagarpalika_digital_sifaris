package com.ishanitech.ipalika.dao;

import com.ishanitech.ipalika.dto.UserDTO;
import com.ishanitech.ipalika.model.FavouritePlace;
import com.ishanitech.ipalika.model.Role;
import com.ishanitech.ipalika.model.User;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;
import java.util.Map;
/**
 * A DAO interface which holds all the CRUD operations that can be performed 
 * on an User entity or Table.
 * @author Umesh Bhujel
 * @since 1.0
 */

@RegisterBeanMapper(value = User.class, prefix = "u")
@RegisterBeanMapper(value = Role.class, prefix = "r")
public interface UserDAO {
	
	/**
	 * Inserts user into database.
	 * @param user User model object.
	 */
	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO user ( `full_name`, "
			+ " `username`, "
			+ " `email`, "
			+ " `password`, "
			+ " `mobile_number`, "
			+ " `locked`, "
			+ " `first_login`, "
			+ " `enabled`, "
			+ " `expired`, "
			+ " `registered_date`,"
			+ " `ward_no`) "
			+ " VALUES (:fullName, "
			+ " :username, "
			+ " :email, "
			+ " :password, "
			+ " :mobileNumber, "
			+ " :locked, "
			+ " :firstLogin, "
			+ " :enabled, "
			+ " :expired, "
			+ " :registeredDate, "
			+ " :wardNo )")
	public int addUser(@BindBean User user);
	
	@SqlQuery("SELECT * FROM user WHERE id = :userId")
	@RegisterBeanMapper(User.class)
	public User getUserById(@Bind("userId") int userId);
	
	/**
	 * Inserts currently inserted user's role into user_role table.
	 * @param userId
	 * @param roleId
	 */
	@SqlUpdate("INSERT INTO user_role(`user_id`, `role_id`) VALUES(:userId, :roleId)")
	public void addUserRole(@Bind("userId") int userId, @Bind("roleId") int roleId);
	
	/**
	 * @return fullName String containing first middle and last name
	 */
	@SqlQuery("SELECT full_name FROM user WHERE id =:id")
	public String getUserFullNameById(@Bind("id") int id);

	@SqlUpdate("UPDATE user SET deleted = true WHERE id = :userId")
	public void deleteUser(@Bind("userId") int userId);
	
	@SqlUpdate("UPDATE user SET enabled = false WHERE id = :userId")
	public void disableUser(@Bind("userId") int userId);
	
	@SqlUpdate("UPDATE user SET password = :password WHERE id = :userId")
	public void changePassword(@Bind("password") String password, @Bind("userId") int userId);
	

	@SqlUpdate("UPDATE user SET full_name = :fullName, mobile_number = :mobileNumber, username = :username, email = :email, ward_no = :wardNo WHERE id = :userId")
	public void updateUserInfo(@BindBean User user, @Bind("userId") int userId);
	
	@SqlQuery(" SELECT u.id AS u_id, "
			+ " username AS u_username, "
			+ " full_name AS u_full_name, "
			+ " password AS u_password, "
			+ " email AS u_email, "
			+ " mobile_number AS u_mobile_number, "
			+ " locked AS u_locked, "
			+ " first_login AS u_first_login, "
			+ " enabled AS u_enabled, "
			+ " expired AS u_expired, "
			+ " registered_date AS u_registered_date, "
			+ " ward_no AS u_ward_no,"
			+ " signature AS u_signature, "
			+ " stamp AS u_stamp, "
			+ " r.id AS r_id, "
			+ " r.role as r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ " INNER JOIN role r ON r.id = ur.role_id "
			+ " WHERE u.username = :username AND u.enabled = 1")
	@UseRowReducer(UserReducer.class)
	public User getUserByUsername(@Bind("username") String username) throws UnableToExecuteStatementException;
	
	
	@Transaction
	default int addUserAndRole(User user, int accountType) {
		int userId = addUser(user);
		addUserRole(userId, accountType);
		return userId;
	}

	@SqlQuery("SELECT u.id as user_id, u.full_name as full_name, u.email as email from user u where u.email = :email AND u.enabled = TRUE")
	@RegisterBeanMapper(UserDTO.class)
	UserDTO getUserByEmail(@Bind("email") String email);

	@SqlUpdate("UPDATE user set password = :password WHERE id = :userId")
    void updateUserPassword(String password, Integer userId);

    /**
	 * {@code UserReducer } Custom Row Reducer class to reduce master detail rows.
	 * Mainly used for @SqlQuery annotation which uses joins to reduce master-details
	 * rows to one or more master-level objects.
	 * @author Umesh Bhujel
	 * @since 1.0
	 */
	class UserReducer implements LinkedHashMapRowReducer<Integer, User> {

		@Override
		public void accumulate(Map<Integer, User> container, RowView rowView) {
			System.out.println("*******************************");
			System.out.println(rowView.getRow(Role.class));
			User user = container.computeIfAbsent(rowView.getColumn("u_id", Integer.class), id -> rowView.getRow(User.class));
			  if(rowView.getColumn("r_id", Integer.class) != null) {
				  Role role = rowView.getRow(Role.class);
				  user.getRole().add(role);
			  }
		}
		
	}

	@SqlQuery(" SELECT u.id AS u_id, "
			+ " u.username AS u_username, "
			+ " full_name AS u_full_name, "
			+ " u.email AS u_email, "
			+ " u.mobile_number AS u_mobileNumber, "
			+ " u.locked AS u_locked, "
			+ " u.first_login AS u_firstLogin, "
			+ " u.enabled AS u_enabled, "
			+ " u.expired AS u_expired, "
			+ " u.registered_date AS u_registeredDate, "
			+ " u.ward_no AS u_wardNo,"
			+ " r.id AS r_id, "
			+ " r.role as r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ " INNER JOIN role r ON r.id = ur.role_id "
			+ " WHERE ur.role_id >= :roleId AND u.id != :userId AND u.enabled = 1")
	@UseRowReducer(UserReducer.class)
	public List<User> getAllUserInfo(@Bind("roleId") int roleId, @Bind("userId") int userId);

	@SqlQuery("SELECT role_id FROM user_role ur INNER JOIN user u ON ur.user_id = u.id WHERE u.id =:userId")
	public int getRoleIdFromUserId(@Bind("userId") int userId);


	@SqlQuery("SELECT if(<param> IS NOT NULL, TRUE, false) AS result FROM user WHERE <param> = :value ")
	public Boolean checkDuplicateUserParams(@Define String param, String value);


	@SqlQuery(" SELECT u.id AS u_id, "
			+ " username AS u_username, "
			+ " full_name AS u_full_name, "
			+ " password AS u_password, "
			+ " email AS u_email, "
			+ " mobile_number AS u_mobile_number, "
			+ " locked AS u_locked, "
			+ " first_login AS u_first_login, "
			+ " enabled AS u_enabled, "
			+ " expired AS u_expired, "
			+ " registered_date AS u_registered_date, "
			+ " ward_no AS u_ward_no,"
			+ " r.id AS r_id, "
			+ " r.role as r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ " INNER JOIN role r ON r.id = ur.role_id "
			+ " WHERE u.id = :userId AND u.enabled = 1")
	@UseRowReducer(UserReducer.class)
	public User getUserInfoByUserId(@Bind("userId") int userId);


	@SqlUpdate("UPDATE user_role SET role_id = :roleId WHERE user_id = :userId")
	public void updateRoleInfo(@Bind("roleId") int roleId,@Bind("userId") int userId);


	@SqlUpdate("UPDATE user set enabled = TRUE WHERE id = :userId")
	void enableUserAccount(@Bind("userId") Integer userId);

}
