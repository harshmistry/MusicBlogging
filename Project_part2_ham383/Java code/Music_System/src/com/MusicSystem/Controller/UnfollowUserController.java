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
import com.MusicSystem.Connection.ShowUserFollowingDAO;
import com.MusicSystem.Connection.UnfollowUserDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class UnfollowUserController
 */
public class UnfollowUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnfollowUserController() {
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
		System.out.println("Inside UnfollowUserController");
		HttpSession session=request.getSession();
		RequestDispatcher rd=null;
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		String[] selectedUserID=request.getParameterValues("unfollowUserSelected");
		UnfollowUserDAO unfollowUserDAO=new UnfollowUserDAO();
		if(null==selectedUserID||(null!=selectedUserID && selectedUserID.length==0)){
			rd=request.getRequestDispatcher("/Pages/ShowUserFollowing.jsp");
			request.setAttribute("noUserSelected", "true");
			rd.forward(request, response);
		}else{
			for(String userIdUnfollow: selectedUserID){
				try {
					unfollowUserDAO.unfollowUser(loggedInUserID, userIdUnfollow);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError(e.getMessage());
					e.printStackTrace();
				}
			}
			//redirect to showUserFollowing page
			ShowUserFollowingDAO showUserFollowingDAO=new ShowUserFollowingDAO();
			try {
				List<UserBean> userBeanFollowingList=showUserFollowingDAO.showUserFollowing(loggedInUserID);
				request.setAttribute("userBeanFollowingList", userBeanFollowingList);
				rd=request.getRequestDispatcher("/Pages/ShowUserFollowing.jsp");
				rd.forward(request, response);
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

}
