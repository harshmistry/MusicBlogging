package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

public class FetchUserData {
	public UserBean fetchUserData(UserBean userBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		
		CallableStatement callableStatement=conn.prepareCall("call FETCH_USERDATA_PROC(?,?,?,?,?,?,?,?,?)");
		callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(4, java.sql.Types.DOUBLE);
		callableStatement.registerOutParameter(5, java.sql.Types.DATE);
		callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userBean.getUserid());
		
		callableStatement.execute();
		
		userBean.setFirstName(callableStatement.getString(2));
		userBean.setLastName(callableStatement.getString(3));
		userBean.setPhoneNo(callableStatement.getDouble(4));
		userBean.setDob(callableStatement.getDate(5));
		userBean.setCity(callableStatement.getString(6));
		userBean.setState(callableStatement.getString(7));
		userBean.setPassword(callableStatement.getString(8));
		
		String errMsg=callableStatement.getString(9);
		
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		return userBean;
	}
}
