package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

public class AddConcertDAO {
	public void addConcertDetail(ConcertDetailsBean concertDetailsBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call CONCERT_DETAIL_INSERT_PROC(?,?,?,?,?,?,?,?,?,?,?,?)");
		callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);
		callableStatement.setString(1, concertDetailsBean.getArtist_id());
		callableStatement.setString(2, concertDetailsBean.getBand_id());
		callableStatement.setString(3, concertDetailsBean.getConcert_venue_city());
		callableStatement.setString(4, concertDetailsBean.getConcert_venue_state());
		callableStatement.setInt(5, concertDetailsBean.getConcert_price());
		callableStatement.setDate(6, concertDetailsBean.getConcert_date());
		callableStatement.setInt(7, concertDetailsBean.getTotal_tickets());
		callableStatement.setString(8, concertDetailsBean.getConcert_time());
		callableStatement.setInt(9, concertDetailsBean.getMusic_category_id());
		callableStatement.setInt(10, concertDetailsBean.getMusic_subcategory_id());
		callableStatement.setString(11, concertDetailsBean.getConcert_name());
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(12);
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}

		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
