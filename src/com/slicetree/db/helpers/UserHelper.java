package com.slicetree.db.helpers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.slicetree.common.logging.LoggingHelper;

public class UserHelper extends DatabaseHelper {
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	/**
	 * Create a user.
	 * 
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @param orgId
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void createUser(String email, String password, String firstName, String lastName,
			String role, Long orgId) throws SQLException, NamingException {
		final String METHODNAME = "createUser";
		logger.entering(CLASSNAME, METHODNAME);

		UserCredentialsHelper credsHelper = new UserCredentialsHelper();
		credsHelper.createUserCredentials(email, password);

		// TODO FIXME no validation if the credentials were actually added
		// successfully or not. fix that.

		String existingUserSql = "SELECT * FROM users WHERE email=?;";
		String[] params = { email };
		List<Map<String, Object>> res = this.query(existingUserSql, params);
		if (res.size() == 0) {
			// TODO make this a sql constant
			String newUserSql = "INSERT INTO users (org_id, email, firstname, "
					+ "lastname, user_role, user_creation) VALUES (?,?,?,?,?,?);";
			Object[] newUserParams =
					{ orgId, email, firstName, lastName, role, new java.util.Date() };
			this.execute(newUserSql, newUserParams);
		} else {
			throw new SQLException("A user with that email address already exists.");
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Find a user using their email address.
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Map<String, Object> findUserByEmailAddress(String email)
			throws SQLException, NamingException {
		final String METHODNAME = "findUserByEmailAddress";
		logger.entering(CLASSNAME, METHODNAME, email);

		Map<String, Object> res = null;

		// TODO make this a sql constant
		String findSql = "SELECT * FROM users WHERE email=?;";
		String[] params = { email };
		List<Map<String, Object>> dbRes = this.query(findSql, params);
		if (dbRes != null && dbRes.size() == 1) {
			res = (HashMap<String, Object>) dbRes.get(0);
		} else {
			// TODO log
		}

		logger.exiting(CLASSNAME, METHODNAME, res);
		return res;
	}

	/**
	 * Find a user using their user id.
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Map<String, Object> findUserByUserId(Long userId) throws SQLException, NamingException {
		final String METHODNAME = "findUserByUserId";
		logger.entering(CLASSNAME, METHODNAME, userId);

		Map<String, Object> res = null;

		// TODO make this a sql constant
		String findSql = "SELECT * FROM users WHERE user_id=?;";
		Long[] params = { userId };
		List<Map<String, Object>> dbRes = this.query(findSql, params);
		if (dbRes != null && dbRes.size() == 1) {
			res = (HashMap<String, Object>) dbRes.get(0);
		} else {
			// TODO log
		}

		logger.exiting(CLASSNAME, METHODNAME, res);
		return res;
	}

	/**
	 * Commit all changeable columns to the database for a particular user.
	 * 
	 * @param sql
	 * @param params
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void commitFromUserId(String sql, Object[] params) throws SQLException, NamingException {
		final String METHODNAME = "commitFromUserId";
		logger.entering(CLASSNAME, METHODNAME, new Object[] { sql, params });

		this.execute(sql, params);

		logger.exiting(CLASSNAME, METHODNAME);
	}
}
