package com.slicetree.common.logging;

public class LoggingHelper {
	private Integer currentLogLevel = null;

	public LoggingHelper() {
		initCurrentLogLevel();
	}

	private void initCurrentLogLevel() {
		// TODO
		currentLogLevel = 4;
	}

	private String getDateTimeString() {
		String res = new java.util.Date().toString();
		return res;
	}

	public void log(Integer requiredLevel, String CLASSNAME, String METHODNAME, String msg) {
		if (requiredLevel <= currentLogLevel) {
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
		if (currentLogLevel >= LogLevel.TRACE) {
			String msg = "ENTERING";
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}

	public void entering(String CLASSNAME, String METHODNAME, Object o) {
		if (currentLogLevel >= LogLevel.TRACE) {
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
		if (currentLogLevel >= LogLevel.TRACE) {
			String msg = "EXITING";
			log(LogLevel.TRACE, CLASSNAME, METHODNAME, msg);
		}
	}

	public void exiting(String CLASSNAME, String METHODNAME, Object o) {
		if (currentLogLevel >= LogLevel.TRACE) {
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
