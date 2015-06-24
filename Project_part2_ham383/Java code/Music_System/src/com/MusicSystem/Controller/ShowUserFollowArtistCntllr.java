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
import com.MusicSystem.Connection.ShowUserFollowArtistDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class ShowUserFollowArtistCntllr
 * To display list of users following logged in artist
 */
public class ShowUserFollowArtistCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUserFollowArtistCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowUserFollowArtistCntllr-->doGet()");
		HttpSession session=request.getSession();
		String artistId=(String)session.getAttribute("SessionUsername");
		ShowUserFollowArtistDAO showUserFollowArtistDAO=new ShowUserFollowArtistDAO();
		try {
			List<UserBean> userFollowArtistList=showUserFollowArtistDAO.fetchUserFollowingArtist(artistId);
			request.setAttribute("userFollowArtistList", userFollowArtistList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowUserFollowArtist.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("ShowUserFollowArtistCntllr-->doGet()-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
