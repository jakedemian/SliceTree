package com.slicetree.common.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Crunchify.com
 * 
 */

public class SliceTreeConfigPropertiesHelper {
	private InputStream inputStream;

	private static Map<String, String> props = new HashMap<String, String>();

	public SliceTreeConfigPropertiesHelper() throws IOException {

		try {
			Properties properties = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"Property file '" + propFileName + "' not found in the classpath");
			}

			// iterate through properties and put each one in our this.props map
			for (Object propKey : properties.keySet()) {
				String thisKey = propKey.toString();
				props.put(thisKey, properties.getProperty(thisKey));
			}

		} catch (Exception e) {
			// TODO something else here, can't use logger since this is the file
			// that gets log level (at least I think i can't use it...)
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}

	public String get(String propKey) {
		String res = null;
		if (props != null) {
			res = props.get(propKey);
		}
		return res;
	}
}
