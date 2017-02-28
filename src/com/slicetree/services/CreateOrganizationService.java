package com.slicetree.services;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.db.beans.accessbeans.UserAccessBean;
import com.slicetree.db.helpers.OrganizationHelper;
import com.slicetree.servlets.jspservlets.SliceTreeServlet;

/**
 * Servlet implementation class CreateOrganizationService
 */
@WebServlet("/CreateOrganizationService")
public class CreateOrganizationService extends SliceTreeServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private final String CLASSNAME = getClass().getCanonicalName();
	private LoggingHelper logger = new LoggingHelper();

	private String redirectLocation = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enforceUserLogonStatus(MUST_BE_LOGGED_IN, "Home", request, response);
		dispatch(redirectLocation, request, response);
	}

	@Override
	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		final String METHODNAME = "doWork";
		logger.entering(CLASSNAME, METHODNAME);

		redirectLocation = "CreateOrganization"; // default sending them back to
													// the form
		setForwardAction(FA_REQUEST_FORWARD);
		try {
			Long userId = Long.valueOf(request.getParameter("org-master-user-id"));
			UserAccessBean user = new UserAccessBean();
			user.populateByUserId(userId);

			if (user.getOrgId() == null || user.getOrgId() == -1) {
				// get other form parameters
				String orgName = request.getParameter("org-name");
				String orgType = request.getParameter("org-type");

				// create the organization
				OrganizationHelper orgHelper = new OrganizationHelper();
				orgHelper.createOrganization(orgName, orgType, user.getUserId());

				// get our new org
				try {
					Map<String, Object> org =
							orgHelper.findOrganizationByMasterUserId(user.getUserId());

					if (org != null) {
						Long orgId = Long.valueOf(org.get("org_id").toString());
						user.refresh();
						user.setOrgId(orgId);
						user.setUserRole("M"); // set role to master
						user.commit();

						setForwardAction(FA_RESPONSE_REDIRECT);
						redirectLocation = "CreateOrgSuccess";
					} else {
						throw new ServletException(
								"There was a problem obtaining the newly created "
										+ "organization and updating the org master.  "
										+ "Was the org created successfully?");
					}
				} catch (Throwable e) {
					throw new ServletException(e);
				}
			} else {
				logger.log(LogLevel.ERROR, CLASSNAME, METHODNAME,
						"User attempted to create an organization when they are already "
								+ "associated with an organization. userId: " + userId.toString());
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

}
