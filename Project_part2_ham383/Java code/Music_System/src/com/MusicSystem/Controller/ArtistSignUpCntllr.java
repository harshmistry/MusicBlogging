package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.MusicCatSignUpDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.Utility.PopulateMusicCategory;
import com.MusicSystem.bean.MusicCategoryBean;
import com.MusicSystem.bean.UserBean;
import com.MusicSystem.bean.UserMusicFollowingBean;

/**
 * Servlet implementation class ArtistSignUpCntllr
 * servlet to add music category and sub category selected by artist upon sign up
 */
public class ArtistSignUpCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtistSignUpCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ArtistSignUpCntllr-->doPost()");
		MusicCatSignUpDAO musicCatSignUpDAO=new MusicCatSignUpDAO();
		HttpSession session=request.getSession();
		String userID=(String)session.getAttribute("SessionUsername");
		String[] categorySelected=request.getParameterValues("categorySelected");
		String[] subCatSelected=request.getParameterValues("subCatSelected");		
		//System.out.println("UserID: "+userID);
		if((null==categorySelected || (null!=categorySelected && categorySelected.length>1)) || (null==categorySelected || (null!=subCatSelected && subCatSelected.length>1))){
			//if no category is selected, or more then one category or subcategory are selected, then redirect to previous page
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistSignUp.jsp");
			List<MusicCategoryBean> musicCategoryBean=null;
			try {
				musicCategoryBean = new PopulateMusicCategory().populateCategory();
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError("ArtistSignUpCntllr-->doPost()-->"+e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("musicCatBean", musicCategoryBean);
			request.setAttribute("multipleCatSel", "true");
			rd.forward(request, response);
		}else{
			for(String catValue:categorySelected){
				UserMusicFollowingBean userMusicFollowingBean=new UserMusicFollowingBean();
				userMusicFollowingBean.setUserID(userID);
				userMusicFollowingBean.setMusicCatID(Integer.parseInt(catValue));
				//System.out.println("CategoryID:"+catValue);		
				for(String subCatValue:subCatSelected){
					String[] catSubCatStr= subCatValue.split("/");
					if(catSubCatStr[1].equals(catValue)){//select appropriate subcategory
						userMusicFollowingBean.setSubCategoryID(Integer.parseInt(catSubCatStr[0]));
						try {
							musicCatSignUpDAO.insertUserMusicFollow(userMusicFollowingBean);
							RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");		
							rd.forward(request, response);
						} catch (ConnectionFailureException | SQLException e) {
							ErrorLog.logError(e.getMessage());
							e.printStackTrace();
						}
					}
					//System.out.println("SubCategoryID:"+catSubCatStr[0]+"   cat ID:"+catSubCatStr[1]);
				}
			}
		}
	}

}
