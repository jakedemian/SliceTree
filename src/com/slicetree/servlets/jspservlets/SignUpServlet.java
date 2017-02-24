package com.slicetree.servlets.jspservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.slicetree.common.logging.LoggingHelper;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	public SignUpServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "doGet";
		logger.entering(CLASSNAME, METHODNAME);

		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		String cpass = request.getParameter("confirmPassword");

		if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(pass)
				&& StringUtils.isNotBlank(cpass)) {
			request.getRequestDispatcher("/SignUpMoreInfo.jsp").forward(request, response);
		} else {
			// one of the fields was empty
			response.sendRedirect("Home?empty_input=1");
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

}
