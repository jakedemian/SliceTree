package com.slicetree.servlets.jspservlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.db.beans.accessbeans.UserAccessBean;
import com.slicetree.db.helpers.OrganizationHelper;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class CreateOrganizationSuccessServlet
 */
@WebServlet("/CreateOrgSuccess")
public class CreateOrganizationSuccessServlet extends SliceTreeServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);

		dispatch("CreateOrgSuccess.jsp", request, response);
	}

	@Override
	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		setForwardAction(FA_REQUEST_FORWARD);

		OrganizationHelper orgHelper = new OrganizationHelper();
		try {
			String userEmail = new UserLogonSessionHelper(request).getLoggedInUserEmail();
			UserAccessBean user = new UserAccessBean();
			user.populateByUserEmail(userEmail);
			Map<String, Object> org = orgHelper.findOrganizationByOrgId(user.getOrgId());
			if (org != null) {
				request.setAttribute("orgName", org.get("org_name").toString());
			} else {
				// TODO log?
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

}
