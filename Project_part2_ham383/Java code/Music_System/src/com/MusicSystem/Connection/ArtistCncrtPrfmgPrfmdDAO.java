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
 *	class to fetch list of concerts which artist has performed or is going to perform
 */
public class ArtistCncrtPrfmgPrfmdDAO {
	List<ConcertDetailsBean> cncrtPrfmdList=new ArrayList<ConcertDetailsBean>();
	public List<ConcertDetailsBean> fetchConcertPerformng(String artistId) throws ConnectionFailureException, SQLException, IOException{
		//function to fetch list of concerts to be performed
		List<ConcertDetailsBean> cncrtPrfrmgList=new ArrayList<ConcertDetailsBean>();
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call SHOW_CNCRT_PRFMNG_PRFMD_PROC(?,?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
		callableStatement.setString(1, artistId);
		callableStatement.execute();
		ResultSet rs=((OracleCallableStatement)callableStatement).getCursor(2);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				ConcertDetailsBean concertBean=new ConcertDetailsBean();
				concertBean.setConcert_id(rs.getInt(1));
				concertBean.setArtist_id(rs.getString(2));
				concertBean.setBand_id(rs.getString(3));
				concertBean.setConcert_venue_city(rs.getString(4));
				concertBean.setConcert_venue_state(rs.getString(5));
				concertBean.setConcert_price(rs.getInt(6));
				concertBean.setConcert_date(rs.getDate(7));
				concertBean.setTotal_tickets(rs.getInt(8));
				concertBean.setConcert_time(rs.getString(9));
				concertBean.setMusic_category_id(rs.getInt(10));
				concertBean.setMusic_subcategory_id(rs.getInt(11));
				concertBean.setConcert_name(rs.getString(12));
				concertBean.setMusic_category_name(rs.getString(13));
				concertBean.setMusic_subcategory_name(rs.getString(14));
				cncrtPrfrmgList.add(concertBean);
			}
		}
		rs.close();
		rs=((OracleCallableStatement)callableStatement).getCursor(3);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				ConcertDetailsBean concertBean=new ConcertDetailsBean();
				concertBean.setConcert_id(rs.getInt(1));
				concertBean.setArtist_id(rs.getString(2));
				concertBean.setBand_id(rs.getString(3));
				concertBean.setConcert_venue_city(rs.getString(4));
				concertBean.setConcert_venue_state(rs.getString(5));
				concertBean.setConcert_price(rs.getInt(6));
				concertBean.setConcert_date(rs.getDate(7));
				concertBean.setTotal_tickets(rs.getInt(8));
				concertBean.setConcert_time(rs.getString(9));
				concertBean.setMusic_category_id(rs.getInt(10));
				concertBean.setMusic_subcategory_id(rs.getInt(11));
				concertBean.setConcert_name(rs.getString(12));
				concertBean.setMusic_category_name(rs.getString(13));
				concertBean.setMusic_subcategory_name(rs.getString(14));
				cncrtPrfmdList.add(concertBean);
			}
		}
		
		String errMsg=callableStatement.getString(4);
		if(null!=errMsg&&!errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=rs)
			rs.close();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		return cncrtPrfrmgList;
	}
	
	public List<ConcertDetailsBean> fetchConcertPerformnd(){
		//function to fetch list of concerts that are already performed
		return cncrtPrfmdList;
	}
}
