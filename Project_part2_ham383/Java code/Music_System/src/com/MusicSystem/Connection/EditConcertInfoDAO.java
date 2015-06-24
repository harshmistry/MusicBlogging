package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

public class EditConcertInfoDAO {
	public void editConcertInfo(ConcertDetailsBean concertDetailsBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call EDIT_CONCERT_INFO_PROC(?,?,?,?,?,?,?,?,?)");
		callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
		callableStatement.setInt(1, concertDetailsBean.getConcert_id());
		callableStatement.setString(2,concertDetailsBean.getConcert_venue_city());
		callableStatement.setString(3, concertDetailsBean.getConcert_venue_state());
		callableStatement.setInt(4, concertDetailsBean.getConcert_price());
		callableStatement.setDate(5, concertDetailsBean.getConcert_date());
		callableStatement.setInt(6, concertDetailsBean.getTotal_tickets());
		callableStatement.setString(7, concertDetailsBean.getConcert_time());
		callableStatement.setString(8, concertDetailsBean.getConcert_name());
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(9);
		if(null!=errMsg&& !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
