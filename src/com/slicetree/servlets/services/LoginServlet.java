package com.slicetree.servlets.services;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.servlets.jspservlets.SliceTreeServlet;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends SliceTreeServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private LoggingHelper logger = new LoggingHelper();
	private final String CLASSNAME = getClass().getCanonicalName();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doPost";
		logger.entering(CLASSNAME, METHODNAME);

		String email = request.getParameter("email");
		String pass = request.getParameter("password");

		if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(pass)) {
			try {
				UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
				if (!logonHelper.isUserLoggedIn()) {
					logonHelper.login(email, pass);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("Home");

		logger.exiting(CLASSNAME, METHODNAME);
	}

}
