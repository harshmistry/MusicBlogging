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

/**
 * 
 * @author Harsh
 *	Class to display upcoming concert based on artist and music he's liking
 */
public class ShowUpCmngConcertDAO {
	private List<ConcertDetailsBean> musicUpCmgConcertList=new ArrayList<ConcertDetailsBean>();
	public List<ConcertDetailsBean> showArtistUpCmgConcert(String loggedin_userid) throws ConnectionFailureException, SQLException, IOException{
		//function to fetch list of upcoming concerts based on artists he's liking
		List<ConcertDetailsBean> artistUpCmgConcertList=new ArrayList<ConcertDetailsBean>();
		ConcertDetailsBean concertDetailsBean;
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call SHOW_UPCOMING_CONCERT_PROC(?,?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedin_userid);
		callableStatement.execute();
		ResultSet rs=((OracleCallableStatement)callableStatement).getCursor(2);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				concertDetailsBean=new ConcertDetailsBean();
				concertDetailsBean.setConcert_id(rs.getInt(1));
				concertDetailsBean.setArtist_id(rs.getString(2));
				concertDetailsBean.setBand_id(rs.getString(3));
				concertDetailsBean.setConcert_venue_city(rs.getString(4));
				concertDetailsBean.setConcert_venue_state(rs.getString(5));
				concertDetailsBean.setConcert_price(rs.getInt(6));
				concertDetailsBean.setConcert_time(rs.getString(7));
				concertDetailsBean.setConcert_date(rs.getDate(8));
				concertDetailsBean.setConcert_name(rs.getString(9));
				concertDetailsBean.setMusic_category_id(rs.getInt(10));
				concertDetailsBean.setMusic_subcategory_id(rs.getInt(11));
				concertDetailsBean.setTotal_tickets(rs.getInt(12));
				concertDetailsBean.setMusic_category_name(rs.getString(13));
				concertDetailsBean.setMusic_subcategory_name(rs.getString(14));
				artistUpCmgConcertList.add(concertDetailsBean);
			}
		}
		rs.close();
		rs=((OracleCallableStatement)callableStatement).getCursor(3);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				concertDetailsBean=new ConcertDetailsBean();
				concertDetailsBean.setConcert_id(rs.getInt(1));
				concertDetailsBean.setArtist_id(rs.getString(2));
				concertDetailsBean.setBand_id(rs.getString(3));
				concertDetailsBean.setConcert_venue_city(rs.getString(4));
				concertDetailsBean.setConcert_venue_state(rs.getString(5));
				concertDetailsBean.setConcert_price(rs.getInt(6));
				concertDetailsBean.setConcert_time(rs.getString(7));
				concertDetailsBean.setConcert_date(rs.getDate(8));
				concertDetailsBean.setConcert_name(rs.getString(9));
				concertDetailsBean.setMusic_category_id(rs.getInt(10));
				concertDetailsBean.setMusic_subcategory_id(rs.getInt(11));
				concertDetailsBean.setTotal_tickets(rs.getInt(12));
				concertDetailsBean.setMusic_category_name(rs.getString(13));
				concertDetailsBean.setMusic_subcategory_name(rs.getString(14));
				musicUpCmgConcertList.add(concertDetailsBean);
			}
		}
		
		String errMsg=callableStatement.getString(4);
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		
		if(null!=rs)
			rs.close();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		
		return artistUpCmgConcertList;
	}
	
	public List<ConcertDetailsBean> showMusicUpCmgConcert(){
		//function to fetch list of upcoming concerts based on music category and sub_category he's liking
		return musicUpCmgConcertList;
	}
}
