package com.slicetree.servlets.jspservlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;
import com.slicetree.users.user.UserLogonSessionHelper;

/**
 * Servlet implementation class SliceTreeServlet
 */
public abstract class SliceTreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoggingHelper logger = new LoggingHelper();
	private final String CLASSNAME = getClass().getCanonicalName();

	// enforceUserLogonStatus() values for requiredLogonStatus
	protected final boolean MUST_BE_LOGGED_IN = true;
	protected final boolean MUST_NOT_BE_LOGGED_IN = false;

	// true if this servlet has already committed a response
	protected boolean responseWasAlreadyCommitted = false;

	// the action to take after doWork()
	protected Integer FORWARD_ACTION = null;

	// FIXME make this a class or something. like ForwardAction
	// possible values for FORWARD_ACTION
	protected final int FORWARD_ACTION_REQUEST_FORWARD = 1;
	protected final int FORWARD_ACTION_RESPONSE_REDIRECT = 2;

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
	protected void enforceUserLogonStatus(boolean requiredLogonStatus, String redirectServletId,
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

		if (!responseWasAlreadyCommitted) {
			doWork(request, response);
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Dispatch the request if the response has not already been committed.
	 * 
	 * @param jspName
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void dispatchRequest(String target, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String METHODNAME = "dispatchRequest";
		logger.entering(CLASSNAME, METHODNAME);

		if (!responseWasAlreadyCommitted) {
			doForwardAction(target, request, response);
		}

		// have to reset this otherwise it's "true" value will be preserved
		// between multiple pages
		responseWasAlreadyCommitted = false;

		logger.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Forward this request via either a request forward() or a response
	 * sendRedirect()
	 * 
	 * @param target
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doForwardAction(String target, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String METHODNAME = "doForwardAction";
		logger.entering(CLASSNAME, METHODNAME);

		if (FORWARD_ACTION != null) {
			if (FORWARD_ACTION == FORWARD_ACTION_REQUEST_FORWARD) {
				request.getRequestDispatcher(target).forward(request, response);
			} else {
				response.sendRedirect(target);
			}
		} else {
			logger.log(LogLevel.ERROR, CLASSNAME, METHODNAME,
					"Attempted to invoke doForwardAction() without first setting FORWARD_ACTION.");
			throw new ServletException();
		}

		logger.exiting(CLASSNAME, METHODNAME);
	}

	/**
	 * Perform the core work of the servlet. This is automatically called after
	 * enforceUserLogonStatus() if the response was not committed. All children
	 * of this class must implement this method.
	 * 
	 * @param request
	 * @param response
	 */
	abstract protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException;

	/**
	 * Set the forward action for this servlet
	 * 
	 * @param action
	 *            FORWARD_ACTION_REQUEST_FORWARD
	 *            FORWARD_ACTION_RESPONSE_REDIRECT
	 */
	protected void setForwardAction(Integer action) {
		this.FORWARD_ACTION = action;
	}
}
