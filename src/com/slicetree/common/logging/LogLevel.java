package com.slicetree.common.logging;

import java.util.HashMap;
import java.util.Map;

public class LogLevel {
	public static final Integer ERROR = 0;
	public static final Integer WARN = 1;
	public static final Integer INFO = 2;
	public static final Integer TRACE = 3;
	public static final Integer ALL = 4;
	
	private static Map<Integer, String> _logLevels = new HashMap<Integer, String>()
	{{
	     put(ERROR, "ERROR");
	     put(WARN, "WARNING");
	     put(INFO, "INFO");
	     put(TRACE, "TRACE");
	     put(ALL, "ALL");
	}};
	
	public static String getLogLevelString(Integer i){
		String s = "";
		s = _logLevels.get(i);
		return s;
	}
}
