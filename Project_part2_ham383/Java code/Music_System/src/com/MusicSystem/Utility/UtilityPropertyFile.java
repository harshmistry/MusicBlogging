package com.MusicSystem.Utility;

import java.io.IOException;
import java.util.Properties;

import com.MusicSystem.ExceptionLogger.ErrorLog;


public class UtilityPropertyFile {
	public static String getPropertyValue(String key) {
		Properties utilityProps = new Properties();
		try {
			utilityProps.load(UtilityPropertyFile.class.getClassLoader().getResourceAsStream("utility.properties"));
		} catch (IOException e) {
			try {
				ErrorLog.logError(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return utilityProps.getProperty(key);
	}
}
