package com.slicetree.servlets.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.servlets.jspservlets.SliceTreeServlet;

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

	// TODO
	/*
	 * for now SliceTreeServlet is in a decent enough state to handle all types
	 * of different request/response types and paths. I just need to remember
	 * (and maybe make cleaner) the method of setting the forward action type.
	 * but for now i'm good and i can continue with the logic in this doWork and
	 * try to finish the Sign Up process. I still need to hash out how to create
	 * an organization, what to do if they cancel it, etc etc
	 */
	// TODO
	protected void doWork(HttpServletRequest request, HttpServletResponse response) {
		if (validateParameters(request)) {
			String firstName = request.getParameter("first-name").toString();
			String lastName = request.getParameter("last-name").toString();
			String email = request.getParameter("email").toString();
			String password = request.getParameter("password").toString();
			String confPass = request.getParameter("confirm-password").toString();

			Object[] signupParams = { firstName, lastName, email, password, confPass };
			request.setAttribute("signupParams", signupParams);

			if (request.getParameter("setup-org") != null) {
				redirectServlet = "CreateOrganizationService";
				// TODO more here
			} else {
				redirectServlet = "SetupComplete";
			}
		} else {
			redirectServlet = "SignUp";
		}
	}

}
