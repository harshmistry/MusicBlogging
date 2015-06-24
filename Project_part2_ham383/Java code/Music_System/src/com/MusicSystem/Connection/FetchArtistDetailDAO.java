package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ArtistDetailBean;

public class FetchArtistDetailDAO {
	public ArtistDetailBean fetchArtistDetail(ArtistDetailBean artistDetailBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		ArtistDetailBean detailBean=new ArtistDetailBean();
		CallableStatement callableStatement=conn.prepareCall("call FETCH_ARTIST_DETAIL_PROC(?,?,?,?,?,?,?,?,?)");
		callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(4, java.sql.Types.DATE);
		callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
		callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
		callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
		callableStatement.setString(1, artistDetailBean.getArtistId());
		callableStatement.execute();
		String errMsg=callableStatement.getString(3);	
		
		detailBean.setBandId(callableStatement.getString(2));
		detailBean.setBandName(callableStatement.getString(3));
		detailBean.setBandCreateDate(callableStatement.getDate(4));
		detailBean.setMusicCategoryId(callableStatement.getInt(5));
		detailBean.setMusicSubCategoryId(callableStatement.getInt(6));
		detailBean.setMusicCategoryName(callableStatement.getString(7));
		detailBean.setMusicSubCategoryName(callableStatement.getString(8));		
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}

		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		return detailBean;
	}
}
