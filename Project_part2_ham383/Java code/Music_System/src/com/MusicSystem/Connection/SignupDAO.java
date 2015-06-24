package com.MusicSystem.Connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.MusicSystem.bean.UserBean;

public class SignupDAO {
	private Connection conn;
	private CallableStatement callableStatement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public void signUPInsert(UserBean userBean) throws SQLException, ConnectionFailureException{
		conn=DBManager.getConnection();
		System.out.println("In signUPInsert()-->SignupDAO");
		callableStatement=conn.prepareCall("call USERSIGNUP_INSERT_PROC(?,?,?,?,?,?,?,?,?,?)");
		callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);
		callableStatement.setString(1,userBean.getUserid());
		callableStatement.setString(2,userBean.getFirstName());
		callableStatement.setString(3,userBean.getLastName());
		callableStatement.setDouble(4,userBean.getPhoneNo());
		callableStatement.setDate(5, userBean.getDob());
		callableStatement.setString(6, userBean.getCity());
		callableStatement.setString(7, userBean.getState());
		callableStatement.setInt(8, userBean.getIsArtist());
		callableStatement.setString(9, userBean.getPassword());
		callableStatement.execute();
		//userFName=callableStatement.getString(3);
		//errorMsg=callableStatement.getString(4);
	}
	
	public boolean checkUsernameExist(String userName) throws ConnectionFailureException, SQLException{
		conn=DBManager.getConnection();
		int count=0;//to check if username already exist
		
		String sql="select count(user_id) from USERDETAIL_MST where user_id=?";
		preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1, userName);
		resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			count=resultSet.getInt(1);
		}
		if(null!=resultSet)
			resultSet.close();
		if(null!=preparedStatement)
			preparedStatement.close();
		if(null!=conn)
			conn.close();
		return count==0;
	}
	
	public boolean checkArtistIDExist(String artistID) throws ConnectionFailureException, SQLException{
		conn=DBManager.getConnection();
		int count=0;//to check if artistID already exist
		//An artistID must exist in Artist_detail_mst table
		String sql="select count(artist_id) from ARTIST_DETAIL_MST where artist_id=?";
		preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1, artistID);
		resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			count=resultSet.getInt(1);
		}
		if(null!=resultSet)
			resultSet.close();
		if(null!=preparedStatement)
			preparedStatement.close();
		if(null!=conn)
			conn.close();
		return count==1;
	}
}
