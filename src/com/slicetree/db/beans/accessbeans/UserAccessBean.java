package com.slicetree.db.beans.accessbeans;

import java.util.Map;

import com.slicetree.common.constants.SQLConstants;
import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.helpers.UserHelper;

public class UserAccessBean extends SliceTreeAccessBean {
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();
	private UserHelper userHelper = new UserHelper();

	private Long userId = null;
	private Long orgId = null;
	private String email = null;
	private String firstName = null;
	private String lastName = null;
	private String userRole = null;
	private String userCreation = null;

	public void populateByUserEmail(String userEmail) {
		final String METHODNAME = "populateByUserEmail";
		logger.entering(CLASSNAME, METHODNAME, userEmail);

		try {
			Map<String, Object> userData = userHelper.findUserByEmailAddress(userEmail);
			populateFromResultSetMap(userData);
		} catch (Throwable e) {
			logger.loge(LogLevel.WARN, CLASSNAME, METHODNAME,
					"There was an exception while populating UserAccessBean "
							+ "using email address.",
					e);
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	public void populateByUserId(Long thisUserId) {
		final String METHODNAME = "populateByUserEmail";
		logger.entering(CLASSNAME, METHODNAME, thisUserId);

		try {
			Map<String, Object> userData = userHelper.findUserByUserId(thisUserId);
			populateFromResultSetMap(userData);
		} catch (Throwable e) {
			logger.loge(LogLevel.WARN, CLASSNAME, METHODNAME,
					"There was an exception while populating UserAccessBean using userId.", e);
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	private void populateFromResultSetMap(Map<String, Object> dbResultRowMap) {
		final String METHODNAME = "populateFromResultSetMap";
		logger.exiting(CLASSNAME, METHODNAME, dbResultRowMap);

		if (dbResultRowMap != null) {
			this.userId = Long.valueOf(dbResultRowMap.get("user_id").toString());
			this.orgId = Long.valueOf(dbResultRowMap.get("org_id").toString());
			this.email = dbResultRowMap.get("email").toString();
			this.firstName = dbResultRowMap.get("firstname").toString();
			this.lastName = dbResultRowMap.get("lastname").toString();
			this.userRole = dbResultRowMap.get("user_role").toString();
			this.userCreation = dbResultRowMap.get("user_creation").toString();
		} else {
			logger.log(LogLevel.WARN, CLASSNAME, METHODNAME,
					"findUserByEmailAddress returned a null value.");
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	@Override
	public void refresh() {
		final String METHODNAME = "refresh";
		logger.entering(CLASSNAME, METHODNAME);

		if (this.userId != null) {
			populateByUserId(this.userId);
		} else {
			logger.log(LogLevel.INFO, CLASSNAME, METHODNAME,
					"Cannot refresh access bean before it has been successfully populated.");
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	@Override
	public void commit() {
		final String METHODNAME = "commit";
		logger.entering(CLASSNAME, METHODNAME);

		if (this.userId != null) {
			String commitSql = SQLConstants.UPDATE_USER_INFO_BY_USER_ID;
			Object[] commitParams = { orgId, email, firstName, lastName, userRole, userId };

			try {
				userHelper.commitFromUserId(commitSql, commitParams);
			} catch (Throwable e) {
				logger.loge(LogLevel.WARN, CLASSNAME, METHODNAME,
						"There was an exception while committing the UserAccessBean", e);
			}
		} else {
			logger.log(LogLevel.INFO, CLASSNAME, METHODNAME,
					"Cannot commit access bean before it has been successfully populated.");
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	public LoggingHelper getLogger() {
		return logger;
	}

	public void setLogger(LoggingHelper logger) {
		this.logger = logger;
	}

	public UserHelper getUserHelper() {
		return userHelper;
	}

	public void setUserHelper(UserHelper userHelper) {
		this.userHelper = userHelper;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserCreation() {
		return userCreation;
	}

}
