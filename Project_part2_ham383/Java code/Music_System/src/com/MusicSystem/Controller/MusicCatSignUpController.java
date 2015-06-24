package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.MusicSystem.bean.UserBean;
import com.MusicSystem.bean.UserMusicFollowingBean;

/**
 * Servlet implementation class MusicCatSignUpController
 */
public class MusicCatSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MusicCatSignUpController() {
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
		System.out.println("Inside MusicCatSignUpController");
		MusicCatSignUpDAO musicCatSignUpDAO=new MusicCatSignUpDAO();
		List<List<UserBean>> reccomUserList=new ArrayList<List<UserBean>>(); //to hold list of recommended users after signup
		List<List<UserBean>> reccomArtistList=new ArrayList<List<UserBean>>(); //to hold list of recommended artist after signup
		RecommendedUsersAndBandDAO reccUsersAndBandDAO=new RecommendedUsersAndBandDAO();
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
				List<UserBean> userListRecc=new ArrayList<UserBean>();
				List<UserBean> artistListRecc = new ArrayList<UserBean>();
				String[] catSubCatStr= subCatValue.split("/");
				if(catSubCatStr[1].equals(catValue)){//select appropriate subcategory
					userMusicFollowingBean.setSubCategoryID(Integer.parseInt(catSubCatStr[0]));
					try {
						musicCatSignUpDAO.insertUserMusicFollow(userMusicFollowingBean);
						userListRecc=reccUsersAndBandDAO.fetchReccoUsers(userID, Integer.parseInt(catSubCatStr[1]), Integer.parseInt(catSubCatStr[0]));
						artistListRecc=reccUsersAndBandDAO.fetchReccomArtist();
						reccomUserList.add(userListRecc);
						reccomArtistList.add(artistListRecc);
					} catch (ConnectionFailureException | SQLException e) {
						ErrorLog.logError(e.getMessage());
						e.printStackTrace();
					}
				}
				//System.out.println("SubCategoryID:"+catSubCatStr[0]+"   cat ID:"+catSubCatStr[1]);
			}
		}
		RequestDispatcher rd=request.getRequestDispatcher("/Pages/FollowUserArtists.jsp");		
		request.setAttribute("reccoUserList", reccomUserList);
		request.setAttribute("reccoArtistList", reccomArtistList);
		rd.forward(request, response);
	}

}
