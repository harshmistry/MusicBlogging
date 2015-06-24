package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.AttendConcertBean;
import com.MusicSystem.bean.ConcertDetailsBean;

public class WritePostDAO {
	private Connection conn;
	private CallableStatement callableStatement;
	private ResultSet rs;
	
	public List<AttendConcertBean> fetchPreviousPost(int concertID) throws ConnectionFailureException, SQLException, IOException{
		List<AttendConcertBean> attendedConcBeanList=new ArrayList<AttendConcertBean>();
		conn=DBManager.getConnection();
		callableStatement=conn.prepareCall("call FETCH_PREVIOUS_POST(?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setInt(1, concertID);
		callableStatement.execute();
		rs=((OracleCallableStatement)callableStatement).getCursor(2);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				AttendConcertBean attendConcertBean=new AttendConcertBean();
				attendConcertBean.setConcertID(rs.getInt(1));
				attendConcertBean.setUserId(rs.getString(2));
				attendConcertBean.setConcertReview(rs.getString(3));
				attendConcertBean.setConcertRating(rs.getString(4));
				attendedConcBeanList.add(attendConcertBean);
			}
		}
		
		String errMsg=callableStatement.getString(3);
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		
		if(null!=rs)
			rs.close();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		
		return attendedConcBeanList;
	}
	
	public void writeNewPost(AttendConcertBean attendConcertBean) throws ConnectionFailureException, SQLException, IOException{
		//method to insert new posts written by user
		conn=DBManager.getConnection();
		callableStatement=conn.prepareCall("call concert_write_post_proc(?,?,?,?,?)");
		callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
		callableStatement.setInt(1, attendConcertBean.getConcertID());
		callableStatement.setString(2, attendConcertBean.getUserId());
		callableStatement.setString(3, attendConcertBean.getConcertReview());
		callableStatement.setString(4, attendConcertBean.getConcertRating());
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(5);
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
