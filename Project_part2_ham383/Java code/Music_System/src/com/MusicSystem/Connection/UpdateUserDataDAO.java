package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

public class UpdateUserDataDAO {
	public void updateUserData(UserBean updatedUserBean) throws ConnectionFailureException, SQLException, IOException{
		Connection conn=DBManager.getConnection();
		CallableStatement callableStatement=conn.prepareCall("call UPDATE_USERDATA_PROC(?,?,?,?,?,?,?,?,?,?)");
		
		callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);
		callableStatement.setString(1, updatedUserBean.getUserid());
		callableStatement.setString(2, updatedUserBean.getFirstName());
		callableStatement.setString(3, updatedUserBean.getLastName());
		callableStatement.setDouble(4, updatedUserBean.getPhoneNo());
		callableStatement.setDate(5, updatedUserBean.getDob());
		callableStatement.setString(6, updatedUserBean.getCity());
		callableStatement.setString(7, updatedUserBean.getState());
		callableStatement.setInt(8, 0); //update record for non-artist
		callableStatement.setString(9, updatedUserBean.getPassword());
		
		callableStatement.execute();
		
		String errMsg=callableStatement.getString(10);
		if(null!=errMsg && !errMsg.isEmpty()){
			ErrorLog.logError(errMsg);
		}
		if(null!=callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		
	}
}
