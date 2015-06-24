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
import com.MusicSystem.Connection.FetchRecommAtristForArtistDAO;
import com.MusicSystem.Connection.FollowUserSignUpDAO;
import com.MusicSystem.Connection.ShowRecommendedUserDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class ArtistFollowArtistCntllr
 */
public class ArtistFollowArtistCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtistFollowArtistCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ArtistFollowArtistCntllr-->doGet()");
		HttpSession session=request.getSession();
		String artistId=(String)session.getAttribute("SessionUsername");
		FetchRecommAtristForArtistDAO recommAtristForArtistDAO=new FetchRecommAtristForArtistDAO();
		try {
			List<UserBean> recommendedArtistForArtistList=recommAtristForArtistDAO.fetchRecommendedArtistforArtist(artistId);
			request.setAttribute("recommendedArtistForArtistList", recommendedArtistForArtistList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRcomndedArtistForArtist.jsp");
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
		System.out.println("Inside ArtistFollowArtistCntllr-->doPost()");
		HttpSession session=request.getSession();
		String artistId=(String)session.getAttribute("SessionUsername");
		FollowUserSignUpDAO followUserSignUpDAO=new FollowUserSignUpDAO();
		String[] artistIdSelected=request.getParameterValues("reccomdedAtristforArtistSel");
		if(null==artistIdSelected ||(null!=artistIdSelected && artistIdSelected.length==0)){
			//if no artist is selected, then redirect him to previous page
			request.setAttribute("noArtistSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRecommendedArtist.jsp");
			rd.forward(request, response);
		}else{
			//allow to like the selected artist
			for(String artistIdToFollow:artistIdSelected){
				try {
					followUserSignUpDAO.insertArtistFollowing(artistId, null, artistIdToFollow);
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");
					rd.forward(request, response);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("RecommendedArtistConstroller-->doPost()-->"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
