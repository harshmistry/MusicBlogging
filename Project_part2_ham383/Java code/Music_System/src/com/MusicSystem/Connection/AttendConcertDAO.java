package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.AttendConcertBean;

public class AttendConcertDAO {
	public void attendConcert(AttendConcertBean attendConcertBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call concert_attendee_insert_proc(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setInt(1, attendConcertBean.getConcertID());
		callableStatement.setString(2, attendConcertBean.getUserId());
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
