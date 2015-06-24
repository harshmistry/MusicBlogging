package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.ShowArtistUserFollowDAO;
import com.MusicSystem.Connection.UnfollowArtistUserDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;

/**
 * Servlet implementation class UnfollowArtistUserController
 * This servlet is used to unfollow/delete artist and users whom logged in user is following
 */
public class UnfollowArtistUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnfollowArtistUserController() {
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
		System.out.println("Inside UnfollowArtistUserController->doPost()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		String[] userUnfollowSelected=request.getParameterValues("userSelectedtoUnlike");
		String[] artistUnfollowSelected=request.getParameterValues("artistSelectedtoUnlike");
		RequestDispatcher rd=null;
		if(null==userUnfollowSelected ||(null!=userUnfollowSelected && userUnfollowSelected.length==0)){
			if(null==artistUnfollowSelected ||(null!=artistUnfollowSelected && artistUnfollowSelected.length==0)){
				rd=request.getRequestDispatcher("/Pages/ShowUserArtistFollow.jsp");
				request.setAttribute("noValuesSelected", "true");
				rd.forward(request, response);
			}
		}else{
			//delete selected artist/user and redirect to ShowUserArtistFollow.jsp
			UnfollowArtistUserDAO unfollowArtistUserDAO=new UnfollowArtistUserDAO();
			if(null!=userUnfollowSelected){
				for(String userIdUnfollow: userUnfollowSelected){
					try {//unfollow users selected
						unfollowArtistUserDAO.unFollowUser(loggedInUserID, userIdUnfollow);
					} catch (ConnectionFailureException | SQLException e) {
						ErrorLog.logError(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			if(null!=artistUnfollowSelected){
				for(String artistIdUnfollow:artistUnfollowSelected ){
					try {//unfollow artist selected
						unfollowArtistUserDAO.unFollowArtist(loggedInUserID, artistIdUnfollow);
					} catch (ConnectionFailureException | SQLException e) {
						ErrorLog.logError(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			//again fetch artist and user following and redirect to ShowUserArtistFollow.jsp page
			ShowArtistUserFollowDAO showArtistUserFollowDAO=new ShowArtistUserFollowDAO();
			try {
				request.setAttribute("usersFollowingList", showArtistUserFollowDAO.fetchUserFollow(loggedInUserID));
				request.setAttribute("artistFollowingList", showArtistUserFollowDAO.fetchArtistFollow());
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError(e.getMessage());
				e.printStackTrace();
			}
			rd=request.getRequestDispatcher("/Pages/ShowUserArtistFollow.jsp");
			rd.forward(request, response);
		}
	}

}
