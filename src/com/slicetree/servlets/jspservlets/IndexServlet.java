package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;

/**
 * Servlet implementation class IndexServlet
 */

@WebServlet("/Home")
public class IndexServlet extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	private final String JSP_REDIRECT_PATH = "index.jsp";
	private final String INCORRECT_LOGON_STATE_REDIRECT_SERVLET_NAME = "Dashboard";

	public IndexServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doGet";
		logger.entering(CLASSNAME, METHODNAME);
		enforceUserLogonStatus(MUST_NOT_BE_LOGGED_IN, INCORRECT_LOGON_STATE_REDIRECT_SERVLET_NAME,
				request, response);

		// TODO work here

		dispatchRequest(JSP_REDIRECT_PATH, request, response);
		logger.exiting(CLASSNAME, METHODNAME);
	}
}
