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
import com.MusicSystem.bean.ConcertDetailsBean;

public class FetchToBeRecommDAO {
	public List<ConcertDetailsBean> fetchToBeRecommedConcert(String userId) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call SHOW_CONCERT_TO_RECOM_PROC(?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userId);
		callableStatement.execute();
		ResultSet rs=((OracleCallableStatement)callableStatement).getCursor(2);
		List<ConcertDetailsBean> toBeRecomendList=new ArrayList<ConcertDetailsBean>();
		if(!callableStatement.wasNull()){
			while(rs.next()){
				ConcertDetailsBean concertDetailsBean=new ConcertDetailsBean();
				concertDetailsBean.setArtist_id(rs.getString(1));
				concertDetailsBean.setBand_id(rs.getString(2));
				concertDetailsBean.setConcert_date(rs.getDate(3));
				concertDetailsBean.setConcert_id(rs.getInt(4));
				concertDetailsBean.setConcert_name(rs.getString(5));
				concertDetailsBean.setConcert_price(rs.getInt(6));
				concertDetailsBean.setConcert_time(rs.getString(7));
				concertDetailsBean.setConcert_venue_city(rs.getString(8));
				concertDetailsBean.setConcert_venue_state(rs.getString(9));
				concertDetailsBean.setMusic_subcategory_name(rs.getString(10));
				concertDetailsBean.setMusic_category_name(rs.getString(11));
				concertDetailsBean.setMusic_category_id(rs.getInt(12));
				concertDetailsBean.setMusic_subcategory_id(rs.getInt(13));
				toBeRecomendList.add(concertDetailsBean);
			}
		}
		String errMsg=callableStatement.getString(3);
		if(null!=errMsg&& !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		
		if(null!=rs)
			rs.close();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		
		return toBeRecomendList;
	}
	
	public void createRecommendedConcertList(String userId,int concertId) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call CONCERT_RECOMMEN_INSERT_PROC(?,?,?)");
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userId);
		callableStatement.setInt(2,concertId);
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(3);
		if(null!=errMsg&& !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
	}
}
