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
import com.MusicSystem.Connection.RecommendedUsersAndBandDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.Utility.PopulateMusicCategory;
import com.MusicSystem.bean.MusicCategoryBean;
import com.MusicSystem.bean.UserBean;
import com.MusicSystem.bean.UserMusicFollowingBean;

/**
 * Servlet implementation class FollowMusicController
 * servlet to display all music category and sub category for user to like
 */
public class FollowMusicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowMusicController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//fetch list of music category and sub-category and direct to display page
		System.out.println("Inside FollowMusicController-->doGet()");
		try {
			List<MusicCategoryBean> musicCategoryBean=new PopulateMusicCategory().populateCategory();
			request.setAttribute("musicCatBean", musicCategoryBean);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/FollowMusic.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("FollowMusicController-->doGet()-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Once user selects music category and sub-category, store the choice
		System.out.println("Inside FollowMusicController-->doPost()");
		MusicCatSignUpDAO musicCatSignUpDAO=new MusicCatSignUpDAO();
		HttpSession session=request.getSession();
		String userID=(String)session.getAttribute("SessionUsername");
		String[] categorySelected=request.getParameterValues("categorySelected");
		String[] subCatSelected=request.getParameterValues("subCatSelected");		
		//System.out.println("UserID: "+userID);
		System.out.println("Number of category selected: "+categorySelected.length+"\nThey are");
		System.out.println("Number of sub-category selected: "+subCatSelected.length+"\nThey are");
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
						RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");		
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
