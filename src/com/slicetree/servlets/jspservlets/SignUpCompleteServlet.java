package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.beans.accessbeans.UserAccessBean;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class SignUpCompleteServlet
 */
@WebServlet("/SignUpComplete")
public class SignUpCompleteServlet extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);
		setForwardAction(FA_REQUEST_FORWARD);
		dispatch("SignUpComplete.jsp", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		final String METHODNAME = "doWork";
		logger.entering(CLASSNAME, METHODNAME);

		try {
			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			String userEmail = logonHelper.getLoggedInUserEmail();

			if (StringUtils.isNotBlank(userEmail)) {
				UserAccessBean user = new UserAccessBean();
				user.populateByUserEmail(userEmail);
				String firstName = user.getFirstName();
				Long orgId = user.getOrgId();
				request.setAttribute("orgId", orgId);
				request.setAttribute("firstName", firstName);
			}
		} catch (Throwable e) {
			throw new ServletException();
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

}
