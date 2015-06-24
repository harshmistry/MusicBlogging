package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;

public class UnfollowUserDAO {
	public void unfollowUser(String loggedInUserID, String userIDUnfollow) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		
		CallableStatement callableStatement=conn.prepareCall("call UNFOLLLOW_USER_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedInUserID);
		callableStatement.setString(2, userIDUnfollow);
		callableStatement.execute();
		String errMsg=callableStatement.getString(3);
		
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
