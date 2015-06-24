package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.LoginBean;
import com.MusicSystem.bean.UserBean;

//import com.mastek.advancedinvestment.bean.EmployeeBean;
//import com.mastek.advancedinvestment.bean.LoginBean;

public class LoginDao {

	private Connection conn;
	private CallableStatement callableStatement;
	//private Map<Integer,EmployeeBean> empMap=new HashMap<Integer, EmployeeBean>();
	//private EmployeeBean empBean;
	
	@SuppressWarnings("unused")
	public UserBean authenticateLogin(LoginBean bean) throws ConnectionFailureException, SQLException{
		System.out.println("In login DAO");
		int isArtist=0;
		String errorMsg=null;
		String fname=null;
		UserBean userBean=null;
		try{
			conn=DBManager.getConnection();
			callableStatement=conn.prepareCall("call sys_login_proc(?,?,?,?,?)");
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStatement.setString(1,bean.getUsername());
			callableStatement.setString(2,bean.getPassword());
			callableStatement.execute();
			isArtist=callableStatement.getInt(3);
			fname=callableStatement.getString(4);
			errorMsg=callableStatement.getString(5);
			System.out.println("Welcome isArtist: "+isArtist);

			if(null != errorMsg){
				try {
					ErrorLog.logError(errorMsg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			userBean=new UserBean();
			userBean.setIsArtist(isArtist);
			userBean.setFirstName(fname);
			/*sql="select f_name,l_name from employee where e_id=?";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.setString(1, userFName);
			resultSet=preparedStatement.executeQuery();*/
			/*while(resultSet.next()){
				empBean=new EmployeeBean();
				empBean.setEmpId(empId);
				empBean.setFirstName(resultSet.getString(1));
				empBean.setLastName(resultSet.getString(2));
				empMap.put(bean.getLogin_id(),empBean);
			}*/
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
		return userBean;
		
	}
	
	/*public Map<Integer,EmployeeBean> getEmpMap(){
		return empMap;
	}*/
}
