package com.slicetree.servlets.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.helpers.UserHelper;
import com.slicetree.servlets.jspservlets.SliceTreeServlet;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class SignUpService
 */
@WebServlet("/SignUpService")
public class SignUpService extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;
	private LoggingHelper logger = new LoggingHelper();
	private final String CLASSNAME = getClass().getCanonicalName();
	private String redirectServlet = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doPost";
		logger.entering(CLASSNAME, METHODNAME);
		enforceUserLogonStatus(MUST_NOT_BE_LOGGED_IN, "Dashboard", request, response);

		dispatchRequest(redirectServlet, request, response);
		logger.exiting(CLASSNAME, METHODNAME);
	}

	protected boolean validateParameters(HttpServletRequest request) {
		boolean isValid = false;

		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confPass = request.getParameter("confirm-password");

		// TODO FIXME still need to do more validation. is the password long
		// enough? does it meet baseline password strength benchmarks? is the
		// email in the correct format? does the email exist in the database
		// already?

		setForwardAction(FORWARD_ACTION_RESPONSE_REDIRECT);
		if (paramNotNull(firstName) && paramNotNull(lastName) && paramNotNull(email)
				&& paramNotNull(password) && paramNotNull(confPass)) {
			if (password.toString().equals(confPass.toString())) {
				isValid = true;
				setForwardAction(FORWARD_ACTION_REQUEST_FORWARD);
			}
		}

		return isValid;
	}

	protected boolean paramNotNull(Object o) {
		return o != null;
	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		if (validateParameters(request)) {
			Map<String, Object> formParams = new HashMap<String, Object>();
			formParams.put("firstName", request.getParameter("first-name").toString());
			formParams.put("lastName", request.getParameter("last-name").toString());
			formParams.put("email", request.getParameter("email").toString());
			formParams.put("password", request.getParameter("password").toString());

			// create the user
			createUser(formParams, request);

			// we want a response redirect here because this a post and we want
			// the next page loaded as a get
			setForwardAction(FORWARD_ACTION_RESPONSE_REDIRECT);

			redirectServlet = "SignUpComplete";
		} else {
			redirectServlet = "SignUp";
		}
	}

	private boolean createUser(Map<String, Object> params, HttpServletRequest request)
			throws ServletException {
		final String METHODNAME = "createUser";
		logger.entering(CLASSNAME, METHODNAME, params);

		boolean success = false;
		try {
			UserHelper userHelper = new UserHelper();

			// TODO not sure what to do with org id here
			// i THINK allowing it to be null in the DB is cleaner than forcing
			// it to not null, but then oddly set it to -1 if no org assigned
			// yet.

			// also, if a user has no org their dashboard should be empty, with
			// a string telling them to either create or join an organization
			userHelper.createUser(params.get("email").toString(), params.get("password").toString(),
					params.get("firstName").toString(), params.get("lastName").toString(), "U",
					Long.valueOf(-1));

			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			logonHelper.login(params.get("email").toString(), params.get("password").toString());

			if (logonHelper.isUserLoggedIn()) {
				success = true;
			} else {
				logger.log(LogLevel.WARN, CLASSNAME, METHODNAME,
						"User was created but not logged in successfully.");
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}

		logger.exiting(CLASSNAME, METHODNAME, success);
		return success;
	}

}
