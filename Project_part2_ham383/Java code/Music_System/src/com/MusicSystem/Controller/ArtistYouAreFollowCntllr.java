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
import com.MusicSystem.Connection.ShowArtistUserFollowDAO;
import com.MusicSystem.Connection.UnfollowArtistUserDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class ArtistYouAreFollowCntllr
 * Servlet to display list of artist's, that logged in artits is following(doGet)
 * servlet to unfollow selected list of artists that the artist is following(doPost)
 */
public class ArtistYouAreFollowCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtistYouAreFollowCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ArtistYouAreFollowCntllr-->doGet()");
		HttpSession session=request.getSession();
		String artistId=(String)session.getAttribute("SessionUsername");
		ShowArtistUserFollowDAO showArtistUserFollowDAO=new ShowArtistUserFollowDAO();
		try {
			request.setAttribute("usersFollowingList", showArtistUserFollowDAO.fetchUserFollow(artistId));
			request.setAttribute("artistFllwngArtistList", showArtistUserFollowDAO.fetchArtistFollow());
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowArtistFollowingArt.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("ArtistYouAreFollowCntllr-->doGet()-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ArtistYouAreFollowCntllr-->doPost()");
		HttpSession session=request.getSession();
		String artistId=(String)session.getAttribute("SessionUsername");
		String[] artistToUnfollowArr=request.getParameterValues("selArtistToUnFollow");
		if(null==artistToUnfollowArr || (null!=artistToUnfollowArr && artistToUnfollowArr.length==0)){
			request.setAttribute("noArtistSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowArtistFollowingArt.jsp");
			rd.forward(request, response);
		}
		else{
			UnfollowArtistUserDAO unfollowArtistUserDAO=new UnfollowArtistUserDAO();
			for(String artistIdToUnfollow: artistToUnfollowArr){
				try {
					unfollowArtistUserDAO.unFollowArtist(artistId, artistIdToUnfollow);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("ArtistYouAreFollowCntllr-->doPost()-->"+e.getMessage());
					e.printStackTrace();
				}
			}
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");
			rd.forward(request, response);
		}
	}

}
