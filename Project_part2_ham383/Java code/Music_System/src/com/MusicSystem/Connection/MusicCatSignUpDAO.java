package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserMusicFollowingBean;

public class MusicCatSignUpDAO {
	public void insertUserMusicFollow(UserMusicFollowingBean userMusicFollowingBean) throws ConnectionFailureException, SQLException, IOException{
		System.out.println("Inside MusicCatSignUpDAO");
		Connection conn=null;
		CallableStatement callableStatement=null;
		try{
			conn=DBManager.getConnection();
			callableStatement=conn.prepareCall("call user_music_foll_insert_proc(?,?,?,?)");
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.setString(1,userMusicFollowingBean.getUserID());
			callableStatement.setInt(2,userMusicFollowingBean.getMusicCatID());
			callableStatement.setInt(3,userMusicFollowingBean.getSubCategoryID());
			callableStatement.execute();
			String errorMsg=callableStatement.getString(4);
			if(null!=errorMsg && !errorMsg.equals(""))
				ErrorLog.logError(errorMsg);
		}catch(SQLException e){
			try {
				ErrorLog.logError(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		finally{
			if(null!=callableStatement)
				callableStatement.close();
			if(null!=conn)
				conn.close();				
		}	
	}
}
