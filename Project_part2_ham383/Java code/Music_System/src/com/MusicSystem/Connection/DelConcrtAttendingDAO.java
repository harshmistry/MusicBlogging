package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


import com.MusicSystem.ExceptionLogger.ErrorLog;

public class DelConcrtAttendingDAO {
	public void delCOncertAttending(String userId,int concertId) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();		
		CallableStatement callableStatement=conn.prepareCall("call DEL_CONCERT_ATTENDING_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userId);
		callableStatement.setInt(2, concertId);
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
