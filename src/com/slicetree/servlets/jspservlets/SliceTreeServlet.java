package com.slicetree.servlets.jspservlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class SliceTreeServlet
 */
public class SliceTreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoggingHelper logger = new LoggingHelper();
	private final String CLASSNAME = getClass().getCanonicalName();

	protected final boolean MUST_BE_LOGGED_IN = true;
	protected final boolean MUST_NOT_BE_LOGGED_IN = false;

	protected boolean responseWasAlreadyCommitted = false;

	/**
	 * Force a particular logon state to view this servlet's jsp.
	 * 
	 * @param logonStatus
	 *            MUST_BE_LOGGED_IN if the user must be logged in to view the
	 *            servlet's jsp, MUST_NOT_BE_LOGGED_IN if the user must NOT be
	 *            logged in to view the servlet's jsp
	 * @param redirectServletId
	 *            The servlet id to redirect to if the actual logon state
	 *            doesn't match the required logon state.
	 * @throws IOException
	 * @throws ServletException
	 * @throws NamingException
	 * @throws SQLException
	 */
	public void enforceUserLogonStatus(boolean requiredLogonStatus, String redirectServletId,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String METHODNAME = "enforceUserLogonStatus";
		logger.entering(CLASSNAME, METHODNAME);

		try {
			UserLogonSessionHelper logonHelper = new UserLogonSessionHelper(request);
			if (logonHelper.isUserLoggedIn() != requiredLogonStatus) {
				response.sendRedirect(redirectServletId);
				responseWasAlreadyCommitted = true;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	public void dispatchRequest(String jspName, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String METHODNAME = "dispatchRequest";
		logger.entering(CLASSNAME, METHODNAME);

		if (!responseWasAlreadyCommitted) {
			request.getRequestDispatcher(jspName).forward(request, response);
		}

		// have to reset this otherwise it's "true" value will be preserved
		// between multiple pages
		responseWasAlreadyCommitted = false;

		logger.exiting(CLASSNAME, METHODNAME);
	}
}
