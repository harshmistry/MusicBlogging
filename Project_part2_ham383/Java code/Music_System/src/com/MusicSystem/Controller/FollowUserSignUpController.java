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
import com.MusicSystem.Connection.FollowUserSignUpDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class FollowUserSignUpController
 */
public class FollowUserSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowUserSignUpController() {
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
		System.out.println("Inside FollowUserSignUpController !!");
		HttpSession session=request.getSession();
		FollowUserSignUpDAO followUserSignUpDAO=new FollowUserSignUpDAO();
		List<List<UserBean>> reccomArtistList=(List<List<UserBean>>)request.getAttribute("reccoArtistList");
		String[] reccomUserFollowBean=request.getParameterValues("reccomUserSelected");
		String username=(String) (null!=session.getAttribute("SessionUsername")?session.getAttribute("SessionUsername"):""); 
		if(null!=reccomUserFollowBean && reccomUserFollowBean.length>0){
			for(String userFollowingID:reccomUserFollowBean){
				//int userFollowing=Integer.parseInt(userFollowingID);
				try {
					followUserSignUpDAO.insertUsersFollowing(username, userFollowingID);
				} catch (NumberFormatException | ConnectionFailureException
						| SQLException e) {
					ErrorLog.logError(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		//Once users followed by logged in user is inserted, direct him to like recommended artist
		RequestDispatcher rd=request.getRequestDispatcher("Pages/FollowArtistSignUP.jsp");
		request.setAttribute("reccoArtistList", reccomArtistList);
		rd.forward(request, response);
	}

}
