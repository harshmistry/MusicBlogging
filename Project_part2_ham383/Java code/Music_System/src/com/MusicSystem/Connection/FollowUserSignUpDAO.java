package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;

/**
 * 
 * @author Harsh
 * This class is used to insert values into user_following table
 * i.e:- the user's whom logged in user is following on signup
 *
 */
public class FollowUserSignUpDAO {
	public void insertUsersFollowing(String loggedInUserID, String followingUserID) throws ConnectionFailureException, SQLException, IOException{
		System.out.println("Inside FollowUserSignUpDAO-->insertUsersFollowing()");
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call USER_FOLLOWING_INSERT_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedInUserID);
		callableStatement.setString(2, followingUserID);
		callableStatement.execute();
		String errorMsg=callableStatement.getString(3);
		if(null!=errorMsg && !errorMsg.equals("")){
			ErrorLog.logError(errorMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
	
	public void insertArtistFollowing(String loggedInUserID, String followingBandID, String followingArtistID) throws ConnectionFailureException, SQLException, IOException{
		System.out.println("Inside FollowUserSignUpDAO-->insertArtistFollowing()");
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call USER_BAND_FOLLOW_INSERT_PROC(?,?,?,?)");
		callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedInUserID);
		callableStatement.setString(2, followingBandID);
		callableStatement.setString(3, followingArtistID);
		callableStatement.execute();
		String errorMsg=callableStatement.getString(4);
		if(null!=errorMsg && !errorMsg.equals("")){
			ErrorLog.logError(errorMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
