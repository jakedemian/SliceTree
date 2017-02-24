package com.slicetree.users.user;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.helpers.UserCredentialsHelper;

public class UserLogonSessionHelper {
	private final String CLASSNAME = this.getClass().getCanonicalName();
	private LoggingHelper LOGGER = new LoggingHelper();
	private UserCredentialsHelper userCredentialsHelper = new UserCredentialsHelper();

	public static final String LOGGED_IN_USER_SESSION_KEY = "loggedInUser";

	private HttpSession session = null;

	public UserLogonSessionHelper(HttpServletRequest request) throws SQLException, NamingException {
		session = request.getSession();
	}

	public void login(String email, String password) throws SQLException, NamingException {
		final String METHODNAME = "login";
		LOGGER.entering(CLASSNAME, METHODNAME, "email: " + email);

		boolean loginSuccessful = userCredentialsHelper.verifyUserCredentials(email, password);
		if (loginSuccessful) {
			setLoggedInUserId(userCredentialsHelper.getVerifiedUserId());
		}

		LOGGER.exiting(CLASSNAME, METHODNAME);
	}

	public void logout() {
		final String METHODNAME = "logout";
		LOGGER.entering(CLASSNAME, METHODNAME);

		if (getLoggedInUserId() != null) {
			removeLoggedInUserId();
		}

		LOGGER.exiting(CLASSNAME, METHODNAME);
	}

	public boolean isUserLoggedIn() {
		final String METHODNAME = "isUserLoggedIn";
		LOGGER.entering(CLASSNAME, METHODNAME);

		boolean isUserLoggedIn = false;

		if (getLoggedInUserId() != null && getLoggedInUserId() > 0) {
			isUserLoggedIn = true;
		} else {
			// TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
			// check their browser login cookie to see if their stored string
			// matches the login token from the DB and verify it hasnt expired.
			// if it matches and it hasn't expired, log the user in and return
			// true.
		}

		LOGGER.exiting(CLASSNAME, METHODNAME, isUserLoggedIn);
		return isUserLoggedIn;
	}

	/**
	 * Get the current logged in user id. null if no user logged in.
	 * 
	 * @return The user's userId, or null if noone logged in.
	 */
	public Long getLoggedInUserId() {
		final String METHODNAME = "getLoggedInUserId";
		LOGGER.entering(CLASSNAME, METHODNAME);

		Long loggedInUserId = null;
		if (session.getAttribute(LOGGED_IN_USER_SESSION_KEY) != null) {
			loggedInUserId =
					Long.valueOf(session.getAttribute(LOGGED_IN_USER_SESSION_KEY).toString());
		}

		LOGGER.exiting(CLASSNAME, METHODNAME, loggedInUserId);
		return loggedInUserId;
	}

	/**
	 * Set the logged in user id in the session
	 * 
	 * @param userId
	 *            The userId if the new user that is logged in.
	 */
	private void setLoggedInUserId(Long userId) {
		final String METHODNAME = "setLoggedInUserId";
		LOGGER.entering(CLASSNAME, METHODNAME, userId);

		if (userId != null && userId > 0) {
			session.setAttribute(LOGGED_IN_USER_SESSION_KEY, userId);
		} else {
			LOGGER.log(LogLevel.WARN, CLASSNAME, METHODNAME,
					"Cannot set logged in user with null userId or userId <= 0. "
							+ "Passed in userId value was: " + userId);
		}
		LOGGER.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Remove the logged in user's userId from the session.
	 */
	private void removeLoggedInUserId() {
		final String METHODNAME = "removeLoggedInUserId";
		LOGGER.entering(CLASSNAME, METHODNAME);

		session.removeAttribute(LOGGED_IN_USER_SESSION_KEY);

		LOGGER.exiting(CLASSNAME, METHODNAME);
	}
}
