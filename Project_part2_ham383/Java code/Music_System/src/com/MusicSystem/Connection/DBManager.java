package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
	public static Connection getConnection() throws ConnectionFailureException {
		try {
			Properties dbProps = new Properties();
			dbProps.load(DBManager.class.getClassLoader().getResourceAsStream(
					"db.properties"));
			// Step No.1 : Loading the JDBC Driver
			//System.out.println("driverClassName="+dbProps.getProperty("driverClassName"));
			Class.forName(dbProps.getProperty("driverClassName"));
			// Step No.2 : Obtaining connection to the database
			//System.out.println("URL="+dbProps.getProperty("url"));
			String url = dbProps.getProperty("url");
			//System.out.println("username="+dbProps.getProperty("username"));
			String user = dbProps.getProperty("username");
			//System.out.println("password="+dbProps.getProperty("password"));
			String pass = dbProps.getProperty("password");
			//System.out.println("* * * * * Connection successful * * * * * * *");
			return DriverManager.getConnection(url, user, pass);
		} catch (IOException ioe) {
			String errorMsg = "Unable to read db.properties file";
			throw new ConnectionFailureException(errorMsg);
		} catch (ClassNotFoundException cnfe) {

			String errorMsg = "Unable to connect to Database."
					+ "Please check if the driver jar is present in the classpath"
					+ "or not";
			throw new ConnectionFailureException(errorMsg);

		} catch (SQLException sqle) {
			String errorMsg = "Unable to connect to the database\n\t"+sqle.getErrorCode()+"  "+sqle.getMessage();
			throw new ConnectionFailureException(errorMsg, sqle);
		}

	}

}
