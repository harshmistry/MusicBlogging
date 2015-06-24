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

public class RecommendedUsersAndBandDAO {
	Connection conn;
	CallableStatement callableStatement;
	ResultSet reccomUserRS;
	List<UserBean> reccomArtistList;
	public List<UserBean> fetchReccoUsers(String userID,int categoryID,int subCatID) throws ConnectionFailureException, SQLException, IOException{
		List<UserBean> userBeanList=new ArrayList<UserBean>();
		UserBean userBean;
		conn=DBManager.getConnection();
		callableStatement=conn.prepareCall("call FETCH_RECCOM_USERS_ARTIST_PROC(?,?,?,?,?,?)");
		callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
		callableStatement.setString(1, userID);
		callableStatement.setInt(2, categoryID);
		callableStatement.setInt(3, subCatID);
		callableStatement.execute();
		String errorMsg=callableStatement.getString(6);
		reccomUserRS=((OracleCallableStatement) callableStatement).getCursor(4);
		if (!callableStatement.wasNull()) {
			while (reccomUserRS.next()) { //fetch value of each recommended users to newly signup user 
				userBean=new UserBean();
				userBean.setUserid(reccomUserRS.getString(1));
				userBean.setFirstName(reccomUserRS.getString(2));
				userBean.setLastName(reccomUserRS.getString(3));
				userBean.setDob(reccomUserRS.getDate(4));
				userBean.setCity(reccomUserRS.getString(5));
				userBean.setState(reccomUserRS.getString(6));
				System.out.println("--------------Reccom users details for category id:"+categoryID+" sub-cat id: "+subCatID+"-------------");
				System.out.println(userBean.getUserid()+" "+userBean.getFirstName()+" "+userBean.getLastName());
				System.out.println(userBean.getDob()+" "+userBean.getCity()+" "+userBean.getState());
				System.out.println("---------------------------------------------------------------------------");
				userBeanList.add(userBean);
			}
		reccomUserRS.close();
		}
		else{
			System.out.println("callableStatement was null for recommended user result set");
		}
		
		//fetching recommended artist to newly signup user
		reccomUserRS=((OracleCallableStatement) callableStatement).getCursor(5);
		if (!callableStatement.wasNull()) {
			reccomArtistList=new ArrayList<UserBean>();
			while (reccomUserRS.next()) { //fetch value of subcategory for each category
				userBean=new UserBean();
				userBean.setUserid(reccomUserRS.getString(1));
				userBean.setFirstName(reccomUserRS.getString(2));
				userBean.setLastName(reccomUserRS.getString(3));
				userBean.setDob(reccomUserRS.getDate(4));
				userBean.setCity(reccomUserRS.getString(5));
				userBean.setState(reccomUserRS.getString(6));
				System.out.println("--------------Reccom users details for category id:"+categoryID+" sub-cat id: "+subCatID+"-------------");
				System.out.println(userBean.getUserid()+" "+userBean.getFirstName()+" "+userBean.getLastName());
				System.out.println(userBean.getDob()+" "+userBean.getCity()+" "+userBean.getState());
				System.out.println("---------------------------------------------------------------------------");
				reccomArtistList.add(userBean);
			}
		reccomUserRS.close();
		}
		else{
			System.out.println("callableStatement was null for recommended user result set");
		}
		if(null!=errorMsg && !errorMsg.equals("")){
			ErrorLog.logError(errorMsg);
		}
		if(null!= callableStatement)
			callableStatement.close();
		if(null!=conn)
			conn.close();
		return userBeanList;
	}
	
	public List<UserBean> fetchReccomArtist(){
		//Artist list is prefetched by fetchReccoUsers() above
		return reccomArtistList;
	}
}
