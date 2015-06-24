package com.MusicSystem.Connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class LogoutDAO {
	public void logOut(String username) throws ConnectionFailureException, SQLException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call LOGOUT_PROC(?)");
		callableStatement.setString(1, username);
		callableStatement.execute();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
