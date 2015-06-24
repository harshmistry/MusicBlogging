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
import com.MusicSystem.Connection.FetchToBeRecommDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class CreateReccomController
 * Servlet to fetch list of to be recommended list of concerts(doGet)
 * Servlet to create list of recommendation selected by user(doPost)
 */
public class CreateReccomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReccomController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside CreateReccomController-->doGet()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		FetchToBeRecommDAO fetchToBeRecommDAO=new FetchToBeRecommDAO();
		try {
			List<ConcertDetailsBean> toBeRecomendList=fetchToBeRecommDAO.fetchToBeRecommedConcert(userId);
			request.setAttribute("toBeRecomendList", toBeRecomendList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ToBeRecommendConcert.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("CreateReccomController-->doGet()-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside CreateReccomController-->doPost()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		String cncrtIdSelctToRcmd=request.getParameter("cncrtIdSelctdToRcmd");
		if(null==cncrtIdSelctToRcmd || (null!=cncrtIdSelctToRcmd && cncrtIdSelctToRcmd.equals(""))){
			//if no concert is selected, then redirect to homepage
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ToBeRecommendConcert.jsp");
			request.setAttribute("concertNotSelected", "true");
			rd.forward(request, response);
		}else{
			//insert recommendation into table and redirect to homepage
			FetchToBeRecommDAO fetchToBeRecommDAO=new FetchToBeRecommDAO();
			try {
				fetchToBeRecommDAO.createRecommendedConcertList(userId, Integer.parseInt(cncrtIdSelctToRcmd));
				RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
				rd.forward(request, response);
			} catch (NumberFormatException | ConnectionFailureException
					| SQLException e) {
				ErrorLog.logError("CreateReccomController-->doPost()-->"+e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
