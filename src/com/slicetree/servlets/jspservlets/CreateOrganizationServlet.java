package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.beans.accessbeans.UserAccessBean;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class CreateOrganizationServlet
 */
@WebServlet("/CreateOrganization")
public class CreateOrganizationServlet extends SliceTreeServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	private final String VIEW_JSP = "CreateOrganization.jsp";
	private String redirectLocation = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);
		dispatch(redirectLocation, request, response);
	}

	@Override
	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		redirectLocation = "Dashboard"; // default sending them to their
										// dashboard
		setForwardAction(FA_RESPONSE_REDIRECT);
		try {
			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			String email = logonHelper.getLoggedInUserEmail();

			UserAccessBean user = new UserAccessBean();
			user.populateByUserEmail(email);

			// if they don't have an organization yet, send them to the org
			// creation form
			if (user.getOrgId() == null || user.getOrgId() == -1) {
				setForwardAction(FA_REQUEST_FORWARD);
				redirectLocation = VIEW_JSP;
				request.setAttribute("userId", user.getUserId());
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

}
