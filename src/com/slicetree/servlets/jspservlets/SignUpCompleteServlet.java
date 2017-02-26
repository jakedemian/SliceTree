package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpCompleteServlet
 */
@WebServlet("/SignUpComplete")
public class SignUpCompleteServlet extends SliceTreeServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);

		setForwardAction(FORWARD_ACTION_REQUEST_FORWARD);

		dispatchRequest("SignUpComplete.jsp", request, response);
	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response) {

	}

}
