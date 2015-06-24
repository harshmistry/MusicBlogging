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
import com.MusicSystem.Connection.FollowUserSignUpDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;

/**
 * Servlet implementation class FollowArtistSignUpController
 */
public class FollowArtistSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowArtistSignUpController() {
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
		System.out.println("inside FollowArtistSignUpController");
		HttpSession session=request.getSession();
		String username=(String) (null!=session.getAttribute("SessionUsername")?session.getAttribute("SessionUsername"):"");
		String[] artistFollowing=request.getParameterValues("reccomAtristSelected");
		if(null!=artistFollowing && artistFollowing.length>0){
			FollowUserSignUpDAO followArtistSignUpDAO=new FollowUserSignUpDAO();
			for(String artistIDFollowing: artistFollowing){
				try {
					//currently liking a band is not supported in system
					followArtistSignUpDAO.insertArtistFollowing(username, null, artistIDFollowing);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		RequestDispatcher rd=request.getRequestDispatcher("Pages/userHomePage.jsp");
		rd.forward(request, response);
	}

}
