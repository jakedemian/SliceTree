package com.slicetree.common.constants;

public class SQLConstants {
	/**
	 * USERS
	 */
	public static final String CREATE_NEW_USER = "INSERT INTO users (org_id, email, firstname, "
			+ "lastname, user_role, user_creation) VALUES (?,?,?,?,?,?);";

	public static final String SELECT_USERS_BY_EMAIL = "SELECT * FROM users WHERE email=?;";

	public static final String SELECT_USERS_BY_USER_ID = "SELECT * FROM users WHERE user_id=?;";

	public static final String UPDATE_USER_INFO_BY_USER_ID =
			"UPDATE users SET org_id=?,email=?,firstname=?,"
					+ "lastname=?,user_role=? WHERE user_id=?;";

	/**
	 * USER CREDENTIALS
	 */
	public static final String CREATE_NEW_USER_CREDENTIALS =
			"INSERT INTO usercredentials (email,hash) VALUES (?,?);";

	public static final String UPDATE_USER_PASSWORD_HASH =
			"UPDATE usercredentials SET hash=? where email=?;";

	public static final String DELETE_USER_CREDENTIALS =
			"DELETE FROM usercredentials where email=?;";

	public static final String SELECT_USER_CREDENTIALS_BY_EMAIL =
			"SELECT * FROM usercredentials WHERE email=?;";

	/**
	 * ORGANIZATIONS
	 */
	public static final String CREATE_NEW_ORGANIZATION =
			"INSERT INTO orgs (org_name, org_type, org_master_id, org_creation) VALUES (?,?,?,?);";

	public static final String SELECT_ORG_BY_MASTER_USER_ID =
			"SELECT * FROM orgs WHERE org_master_id=?;";

	public static final String SELECT_ORG_BY_ORG_ID = "SELECT * FROM orgs WHERE org_id=?;";

}
