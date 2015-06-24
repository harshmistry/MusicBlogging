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
import com.MusicSystem.bean.UserBean;

public class ShowArtistUserFollowDAO {
	private List<UserBean> artistFollowList=new ArrayList<UserBean>();
	public List<UserBean> fetchUserFollow(String loggedInUserID) throws ConnectionFailureException, SQLException, IOException{
		List<UserBean> userFollowList=new ArrayList<UserBean>();
		UserBean userBean=null;
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call SHOW_ARTIST_USER_FOLLOW_PROC(?,?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
		callableStatement.setString(1, loggedInUserID);
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(4);
		ResultSet rs=((OracleCallableStatement)callableStatement).getCursor(2);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				userBean=new UserBean();
				userBean.setUserid(rs.getString(1));
				userBean.setFirstName(rs.getString(2));
				userBean.setLastName(rs.getString(3));
				userBean.setPhoneNo(rs.getDouble(4));
				userBean.setDob(rs.getDate(5));
				userBean.setCity(rs.getString(6));
				userBean.setState(rs.getString(7));
				artistFollowList.add(userBean);
			}
		}
		rs.close();
		rs=((OracleCallableStatement)callableStatement).getCursor(3);
		if(!callableStatement.wasNull()){
			while(rs.next()){
				userBean=new UserBean();
				userBean.setUserid(rs.getString(1));
				userBean.setFirstName(rs.getString(2));
				userBean.setLastName(rs.getString(3));
				userBean.setPhoneNo(rs.getDouble(4));
				userBean.setDob(rs.getDate(5));
				userBean.setCity(rs.getString(6));
				userBean.setState(rs.getString(7));
				userFollowList.add(userBean);
			}
		}
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=rs)
			rs.close();
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		return userFollowList;
	}
	
	public List<UserBean> fetchArtistFollow(){
		return artistFollowList;
	}
}
