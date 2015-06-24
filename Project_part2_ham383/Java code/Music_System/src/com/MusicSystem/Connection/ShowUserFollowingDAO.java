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

public class ShowUserFollowingDAO {
	public List<UserBean> showUserFollowing(String userid) throws ConnectionFailureException, SQLException, IOException{
		List<UserBean> userBeanFollowingList=new ArrayList<UserBean>();
		UserBean userBean=null;
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call FETCH_USER_FOLLOWING_PROC(?,?,?)");
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userid);
		callableStatement.execute();
		String errMsg=callableStatement.getString(3);
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
				userBeanFollowingList.add(userBean);
			}
		}
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		return userBeanFollowingList;
	}
}
