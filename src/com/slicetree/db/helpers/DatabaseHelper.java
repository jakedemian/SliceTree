package com.slicetree.db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;

public class DatabaseHelper {
	private final String CLASSNAME = getClass().getCanonicalName();
	private Connection conn = null;
	private LoggingHelper logger = new LoggingHelper();

	@Resource(name = "jdbc/slicetree_local_db")
	private DataSource dataSource;

	/**
	 * CONSTRUCTOR
	 * 
	 * @throws NamingException
	 * @throws SQLException
	 */
	public DatabaseHelper() {
		final String METHODNAME = "DatabaseHelper";
	}

	/**
	 * Query the database.
	 * 
	 * @param sql
	 *            The query statement
	 * @return List of rows returned by the query.
	 * @throws SQLException
	 * @throws NamingException
	 */
	protected List<Map<String, Object>> query(String sql) throws SQLException, NamingException {
		return this.query(sql, null);
	}

	/**
	 * Query the database.
	 * 
	 * @param sql
	 *            The query statement
	 * @param params
	 *            The prepared statement parameters (in order).
	 * @return List of rows returned by the query.
	 * @throws SQLException
	 * @throws NamingException
	 */
	protected List<Map<String, Object>> query(String sql, Object[] params)
			throws SQLException, NamingException {
		final String METHODNAME = "query";
		List<Map<String, Object>> resList = null;

		establishConnection();

		if (conn != null && !conn.isClosed()) {
			resList = new ArrayList<Map<String, Object>>();

			PreparedStatement stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 1; i <= params.length; i++) { // 1 based, not 0
					stmt.setObject(i, params[i - 1]);
				}
			}

			ResultSet res = stmt.executeQuery();

			while (res.next()) {
				Map<String, Object> row = new HashMap<String, Object>();

				for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
					String key = res.getMetaData().getColumnName(i);
					Object val = res.getObject(i);
					row.put(key, val);
				}
				resList.add(row);
			}
			stmt.close();
			res.close();
		} else {
			logger.log(LogLevel.ALL, CLASSNAME, METHODNAME, "Attempted to run query when "
					+ "there was no established connection to the database.");
		}

		closeConnection();

		return resList;
	}

	/**
	 * Execute a statement on the database.
	 * 
	 * @param sql
	 *            The sql statement to execute.
	 * @throws SQLException
	 * @throws NamingException
	 */
	protected void execute(String sql) throws SQLException, NamingException {
		this.execute(sql, null);
	}

	/**
	 * Execute a statement on the database.
	 * 
	 * @param sql
	 *            The sql statement to execute.
	 * @param params
	 *            The prepared statement parameters.
	 * @throws SQLException
	 * @throws NamingException
	 */
	protected void execute(String sql, Object[] params) throws SQLException, NamingException {
		final String METHODNAME = "execute";

		establishConnection();

		if (conn != null && !conn.isClosed()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 1; i <= params.length; i++) { // 1 based, not 0
					stmt.setObject(i, params[i - 1]);
				}
			}

			Boolean res = stmt.execute();
			stmt.close();

			// false means returned row update count or nothing, which is
			// what an execute SHOULD do
			if (res != false) {
				logger.log(LogLevel.ALL, CLASSNAME, METHODNAME,
						"Executed statement returned a result set.  "
								+ "Did you mean to use DatabaseHelper.query() ?");
			}
		} else {
			logger.log(LogLevel.WARN, CLASSNAME, METHODNAME,
					"Attempted to execute statement when there was "
							+ "no established connection to the database.");
		}

		closeConnection();

	}

	/**
	 * Establish a connection to the database using definition in
	 * META-INF/context.xml
	 * 
	 * @throws SQLException
	 * @throws NamingException
	 */
	private void establishConnection() throws SQLException, NamingException {
		final String METHODNAME = "establishConnection";

		if (dataSource != null) {
			conn = dataSource.getConnection();
		} else {
			// TODO why isn't the @resource thing working?
			logger.log(LogLevel.ALL, CLASSNAME, METHODNAME,
					"Member variable 'dataSource' was null.  Attempting to lazy load.");

			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			dataSource = (DataSource) envCtx.lookup("jdbc/slicetree_local_db");
			conn = dataSource.getConnection();
		}
	}

	private void closeConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
}
