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
import com.MusicSystem.Connection.WritePostDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.AttendConcertBean;

/**
 * Servlet implementation class WritePostController
 */
public class WritePostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WritePostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside WritePostController-->doGet()");
		HttpSession session=request.getSession();
		//String loggedInUserID=(String)session.getAttribute("SessionUsername");
		String concertIdStr=request.getParameter("writepostfor");
		if(null==concertIdStr){
			//redirect to previous page if no radio button is selected 
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowConcertAttended.jsp");
			rd.forward(request, response);
		}else{
			//allow him to view posts and share his post
			int concertID=Integer.parseInt(request.getParameter("writepostfor"));
			WritePostDAO writePostDAO=new WritePostDAO();
			try {
				List<AttendConcertBean> previousPostList=writePostDAO.fetchPreviousPost(concertID);
				request.setAttribute("previousPostList", previousPostList);
				request.setAttribute("concertID", concertID);
				session.setAttribute("concertIDForPost", concertID);
				RequestDispatcher rd=request.getRequestDispatcher("/Pages/WritePost.jsp");
				rd.forward(request, response);
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside WritePostController-->doPost()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		int concertID=(int) session.getAttribute("concertIDForPost");
		//int concertID=(int) request.getAttribute("concert_ID");
		AttendConcertBean attendConcertBean=new AttendConcertBean();
		attendConcertBean.setConcertID(concertID);
		attendConcertBean.setUserId(loggedInUserID);
		attendConcertBean.setConcertReview(request.getParameter("concertReview"));
		attendConcertBean.setConcertRating(request.getParameter("concertRating"));
		WritePostDAO writePostDAO=new WritePostDAO();
		try {
			writePostDAO.writeNewPost(attendConcertBean);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
