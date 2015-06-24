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
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class RecommendedArtistConstroller
 * servlet to display list of recommended artists for logged in user(doGet)
 */
public class RecommendedArtistConstroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendedArtistConstroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside RecommendedArtistConstroller-->doGet()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		FetchRecommendedArtistDAO recommendedArtistDAO=new FetchRecommendedArtistDAO();
		try {
			List<UserBean> recommendedArtistList=recommendedArtistDAO.fetchRecommendedArtist(userId);
			request.setAttribute("recommendedArtistList", recommendedArtistList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRecommendedArtist.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside RecommendedArtistConstroller-->doPost()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		FollowUserSignUpDAO followUserSignUpDAO=new FollowUserSignUpDAO();
		String[] artistIdSelected=request.getParameterValues("reccomdedAtristSelected");
		if(null==artistIdSelected ||(null!=artistIdSelected && artistIdSelected.length==0)){
			//if no artist is selected, then redirect him to previous page
			request.setAttribute("noArtistSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowRecommendedArtist.jsp");
			rd.forward(request, response);
		}else{
			//allow to like the selected artist
			for(String artistId:artistIdSelected){
				try {
					followUserSignUpDAO.insertArtistFollowing(userId, null, artistId);
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
					rd.forward(request, response);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("RecommendedArtistConstroller-->doPost()-->"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
