package com.slicetree.common.logging;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.slicetree.common.config.SliceTreeConfigPropertiesHelper;

public class LoggingHelper {
	private Integer currentLogLevel = null;
	private final String CLASSNAME = getClass().getCanonicalName();

	public LoggingHelper() {
		initCurrentLogLevel();
	}

	private void initCurrentLogLevel() {
		final String METHODNAME = "initCurrentLogLevel";
		try {
			SliceTreeConfigPropertiesHelper propsHelper = new SliceTreeConfigPropertiesHelper();
			if (StringUtils.isNotBlank(propsHelper.get("log_level"))) {
				currentLogLevel = Integer.valueOf(propsHelper.get("log_level"));
				if (currentLogLevel > 4 || currentLogLevel < 0) {
					currentLogLevel = null;
					_forceLogError(CLASSNAME, METHODNAME,
							"Failed to initialize log level because log level configuration is out "
									+ "of bounds.  Logging will not work until this problem is solved.");
				}
			}
		} catch (IOException e) {
			_forceLogError(CLASSNAME, METHODNAME, "Failed to initialize log level.  "
					+ "Logging will not work until this problem is resolved.");
		}
	}

	private String getDateTimeString() {
		String res = new java.util.Date().toString();
		return res;
	}

	private void _forceLogError(String CLASSNAME, String METHODNAME, String msg) {
		System.out.println(getDateTimeString() + " " + "ERROR" + " " + CLASSNAME + ":" + METHODNAME
				+ "()  :  " + msg);
	}

	public void log(Integer requiredLevel, String CLASSNAME, String METHODNAME, String msg) {
		if (currentLogLevel != null && requiredLevel <= currentLogLevel) {
			System.out.println(getDateTimeString() + " " + LogLevel.getLogLevelString(requiredLevel)
					+ " " + CLASSNAME + ":" + METHODNAME + "()  :  " + msg);
		}
	}

	public void loge(Integer requiredLevel, String CLASSNAME, String METHODNAME, String msg,
			Throwable e) {
		this.log(requiredLevel, CLASSNAME, METHODNAME, msg);
		e.printStackTrace();
	}

	public void entering(String CLASSNAME, String METHODNAME) {
		if (currentLogLevel != null && currentLogLevel >= LogLevel.TRACE) {
			String msg = "ENTERING";
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}

	public void entering(String CLASSNAME, String METHODNAME, Object o) {
		if (currentLogLevel != null && currentLogLevel >= LogLevel.TRACE) {
			String msg = "";
			if (o != null) {
				msg += "ENTERING  " + o.toString();
			} else {
				msg += "ENTERING  " + "null";
			}
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}

	public void exiting(String CLASSNAME, String METHODNAME) {
		if (currentLogLevel != null && currentLogLevel >= LogLevel.TRACE) {
			String msg = "EXITING";
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}

	public void exiting(String CLASSNAME, String METHODNAME, Object o) {
		if (currentLogLevel != null && currentLogLevel >= LogLevel.TRACE) {
			String msg = "";
			if (o != null) {
				msg += "EXITING  " + o.toString();
			} else {
				msg += "EXITING  " + "null";
			}
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}
}
