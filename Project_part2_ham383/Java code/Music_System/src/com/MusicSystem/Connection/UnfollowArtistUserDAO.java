package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;

public class UnfollowArtistUserDAO {
	private Connection conn;
	private CallableStatement callableStatement;
	String errMsg;
	public void unFollowUser(String loggedinUserID, String userIDUnfollow) throws ConnectionFailureException, SQLException, IOException{
		conn=DBManager.getConnection();
		callableStatement=conn.prepareCall("call UNFOLLOW_ARTIST_USER_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedinUserID);
		callableStatement.setString(2, userIDUnfollow);
		callableStatement.execute();
		errMsg=callableStatement.getString(3);
		
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
	
	public void unFollowArtist(String loggedinUserID, String artistIDUnfollow) throws ConnectionFailureException, SQLException, IOException{
		conn=DBManager.getConnection();
		callableStatement=conn.prepareCall("call UNFOLLOW_ARTIST_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedinUserID);
		callableStatement.setString(2, artistIDUnfollow);
		callableStatement.execute();
		errMsg=callableStatement.getString(3);
		
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}

}
