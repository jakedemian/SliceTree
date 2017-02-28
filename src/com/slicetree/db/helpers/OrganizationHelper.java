package com.slicetree.db.helpers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.slicetree.common.constants.SQLConstants;
import com.slicetree.common.logging.LoggingHelper;

public class OrganizationHelper extends DatabaseHelper {
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	/**
	 * Create an organization.
	 * 
	 * @param name
	 * @param type
	 * @param orgMasterUserId
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void createOrganization(String name, String type, Long orgMasterUserId)
			throws SQLException, NamingException {
		final String METHODNAME = "createOrganization";
		logger.entering(CLASSNAME, METHODNAME);

		String sql = SQLConstants.CREATE_NEW_ORGANIZATION;
		Object[] params = { name, type, orgMasterUserId, new java.util.Date().toString() };
		this.execute(sql, params);

		logger.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Retrieve an organization from the DB using the org master's user id.
	 * 
	 * @param masterUserId
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Map<String, Object> findOrganizationByMasterUserId(Long masterUserId)
			throws SQLException, NamingException {
		final String METHODNAME = "findOrganizationByMasterUserId";
		logger.entering(CLASSNAME, METHODNAME);

		Map<String, Object> res = null;

		String sql = SQLConstants.SELECT_ORG_BY_MASTER_USER_ID;
		Object[] params = { masterUserId };
		List<Map<String, Object>> queryRes = this.query(sql, params);
		if (queryRes != null && queryRes.size() == 1) {
			res = queryRes.get(0);
		} else {
			throw new SQLException();
		}

		logger.exiting(CLASSNAME, METHODNAME, res);
		return res;
	}

	/**
	 * Retrieve an organization from the DB using the org_id.
	 * 
	 * @param orgId
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Map<String, Object> findOrganizationByOrgId(Long orgId)
			throws SQLException, NamingException {
		final String METHODNAME = "findOrganizationByOrgId";
		logger.entering(CLASSNAME, METHODNAME);

		Map<String, Object> res = null;

		String sql = SQLConstants.SELECT_ORG_BY_ORG_ID;
		Object[] params = { orgId };
		List<Map<String, Object>> queryRes = this.query(sql, params);

		if (queryRes != null && queryRes.size() == 1) {
			res = queryRes.get(0);
		} else {
			throw new SQLException();
		}

		logger.exiting(CLASSNAME, METHODNAME, res);
		return res;
	}
}
