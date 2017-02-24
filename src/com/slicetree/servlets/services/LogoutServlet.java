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
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/Logout")
public class LogoutServlet extends SliceTreeServlet {
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

		try {
			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			if (logonHelper.isUserLoggedIn()) {
				logonHelper.logout();
			}
			response.sendRedirect("Home");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

}
