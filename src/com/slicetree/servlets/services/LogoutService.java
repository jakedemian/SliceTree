package com.slicetree.servlets.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.servlets.jspservlets.SliceTreeServlet;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class LogoutService
 */
@WebServlet("/Logout")
public class LogoutService extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;
	private LoggingHelper logger = new LoggingHelper();
	private final String CLASSNAME = getClass().getCanonicalName();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doGet";
		logger.entering(CLASSNAME, METHODNAME);
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);
		setForwardAction(FORWARD_ACTION_RESPONSE_REDIRECT);
		dispatchRequest("Home", request, response);
		logger.exiting(CLASSNAME, METHODNAME);
	}

	// TODO make a separate class for servlet services so i dont have to do this
	protected void doWork(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			if (logonHelper.isUserLoggedIn()) {
				logonHelper.logout();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
