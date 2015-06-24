package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.FetchRecommendedArtistDAO;
import com.MusicSystem.Connection.FollowUserSignUpDAO;
import com.MusicSystem.Connection.ShowRecommendedUserDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class RecommendedUserConstroller
 * servlet to display list of recommended users for logged in user(doGet)
 * servlet to save list of recommended users selected by logged in user(doPost)
 */
public class RecommendedUserConstroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendedUserConstroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside RecommendedUserConstroller-->doGet()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		ShowRecommendedUserDAO showRecommendedUserDAO=new ShowRecommendedUserDAO();
		try {
			List<UserBean> recommendedUserList=showRecommendedUserDAO.fetchRecommendedArtist(userId);
			request.setAttribute("recommendedUserList", recommendedUserList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRecommendedUser.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("RecommendedUserConstroller-->doget()-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside RecommendedUserConstroller-->doPost()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		FollowUserSignUpDAO followUserSignUpDAO=new FollowUserSignUpDAO();
		String[] UserIdToFollowSelected=request.getParameterValues("reccomdedUserSelected");
		if(null==UserIdToFollowSelected ||(null!=UserIdToFollowSelected && UserIdToFollowSelected.length==0)){
			//if no artist is selected, then redirect him to previous page
			request.setAttribute("noArtistSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRecommendedArtist.jsp");
			rd.forward(request, response);
		}else{
			//allow to like the selected artist
			for(String userIdFollowing:UserIdToFollowSelected){
				try {
					followUserSignUpDAO.insertUsersFollowing(userId, userIdFollowing);
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
					rd.forward(request, response);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("RecommendedUserConstroller-->doPost()-->"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
