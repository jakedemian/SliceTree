package com.slicetree.db.helpers;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

public class UserHelper extends DatabaseHelper {

	public UserHelper() throws SQLException, NamingException {
	}

	public void createUser(String email, String password, String firstName, String lastName,
			String role, Long orgId) throws SQLException, NamingException {
		UserCredentialsHelper credsHelper = new UserCredentialsHelper();
		credsHelper.createUserCredentials(email, password);

		// TODO FIXME no validation if the credentials were actually added
		// successfully or not. fix that.

		String existingUserSql = "SELECT * FROM users WHERE email=?;";
		String[] params = { email };
		List res = this.query(existingUserSql, params);
		if (res.size() == 0) {
			String newUserSql = "INSERT INTO users (org_id, email, firstname, "
					+ "lastname, user_role, user_creation) VALUES (?,?,?,?,?,?);";
			Object[] newUserParams =
					{ orgId, email, firstName, lastName, role, new java.util.Date() };
			this.execute(newUserSql, newUserParams);
		} else {
			throw new SQLException(
					"A user with that email address already exists in the database.");
		}
	}

}
