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
import com.MusicSystem.ExceptionLogger.ErrorLog;

/**
 * Servlet implementation class ShowArtistAndUserFollowController
 */
public class ShowArtistAndUserFollowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowArtistAndUserFollowController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowArtistAndUserFollowController-->doGet()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		ShowArtistUserFollowDAO showArtistUserFollowDAO=new ShowArtistUserFollowDAO();
		try {
			request.setAttribute("usersFollowingList", showArtistUserFollowDAO.fetchUserFollow(loggedInUserID));
			request.setAttribute("artistFollowingList", showArtistUserFollowDAO.fetchArtistFollow());
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError(e.getMessage());
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowUserArtistFollow.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in doPost");
	}

}
