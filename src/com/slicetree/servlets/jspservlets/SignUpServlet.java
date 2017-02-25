package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUp")
public class SignUpServlet extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	private final String INCORRECT_LOGON_STATE_REDIRECT_SERVLET_NAME = "Dashboard";
	private final String JSP_REDIRECT_PATH = "SignUp.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doGet";
		logger.entering(CLASSNAME, METHODNAME);
		enforceUserLogonStatus(MUST_NOT_BE_LOGGED_IN, INCORRECT_LOGON_STATE_REDIRECT_SERVLET_NAME,
				request, response);
		setForwardAction(FORWARD_ACTION_REQUEST_FORWARD);
		dispatchRequest(JSP_REDIRECT_PATH, request, response);
		logger.exiting(CLASSNAME, METHODNAME);
	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response) {

	}

}
