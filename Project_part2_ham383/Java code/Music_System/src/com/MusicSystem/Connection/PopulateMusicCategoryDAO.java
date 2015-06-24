package com.MusicSystem.Connection;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.MusicCategoryBean;
import com.MusicSystem.bean.MusicSubCategoryBean;

public class PopulateMusicCategoryDAO {
	private Connection conn;
	private CallableStatement callableStatement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public List<MusicCategoryBean> populateMusicCategory() throws ConnectionFailureException, SQLException, IOException{
		System.out.println("In PopulateMusicCategoryDAO !!");
		MusicCategoryBean categoryBean=null;
		List<MusicCategoryBean> musicCatList=new ArrayList<MusicCategoryBean>();
		conn=DBManager.getConnection();
		String sql="select music_category_id from MUSIC_CATEGORY_MST";
		preparedStatement=conn.prepareStatement(sql);
		resultSet=preparedStatement.executeQuery();
		String errorMsg=null;
		while(resultSet.next()){ //fetch value for music category, and set in bean
			categoryBean=new MusicCategoryBean();
			ArrayList<MusicSubCategoryBean> subCatList=new  ArrayList<MusicSubCategoryBean>();
			categoryBean.setMusicCategoryID(resultSet.getInt(1));
			callableStatement=conn.prepareCall("call POPULATE_MUSIC_CAT_PROC(?,?,?,?,?)");
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStatement.setInt(1, categoryBean.getMusicCategoryID());
			callableStatement.execute();
			categoryBean.setCategoryName(callableStatement.getString(2));
			categoryBean.setCategoryDescription(callableStatement.getString(3));
			errorMsg=callableStatement.getString(5);
			//System.out.println("--------------Category details for category id:"+categoryBean.getMusicCategoryID()+"-------------");
			//System.out.println(categoryBean.getCategoryName()+"  "+categoryBean.getCategoryDescription());			
			ResultSet subCatResult=((OracleCallableStatement) callableStatement).getCursor(4);
			if (!callableStatement.wasNull()) {
				while (subCatResult.next()) { //fetch value of subcategory for each category
					MusicSubCategoryBean subCategoryBean=new MusicSubCategoryBean();
					subCategoryBean.setSubCategoryID(subCatResult.getInt(1));
					subCategoryBean.setCategoryID(subCatResult.getInt(2));
					subCategoryBean.setSubCategoryName(subCatResult.getString(3));
					subCategoryBean.setSubCategoryDescription(subCatResult.getString(4));
					subCatList.add(subCategoryBean);
					/*System.out.println("--------------Subcategory details for category id:"+subCategoryBean.getCategoryID()+"-------------");
					System.out.println(subCategoryBean.getCategoryID()+" "+subCategoryBean.getSubCategoryID());
					System.out.println(subCategoryBean.getSubCategoryName()+" "+subCategoryBean.getSubCategoryDescription());
					System.out.println("---------------------------------------------------------------------------");*/
				}
			subCatResult.close();
			categoryBean.setSubCategoryBean(subCatList); //add subcategory to main category
			}
			else{
				System.out.println("callableStatement was null for subcategory details result set");
			}
			musicCatList.add(categoryBean); //add main category to list
			/*System.out.println("subcategory count: "+categoryBean.getSubCategoryBean().size());
			System.out.println("category count: "+musicCatList.size());
			System.out.println("----------------------------------------------------------------------------------");*/
		}
		if(null!=errorMsg && !errorMsg.equals("")){
			ErrorLog.logError(errorMsg);
		}
		return musicCatList;
	}
	
}
