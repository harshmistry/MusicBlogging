package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.AttendConcertDAO;
import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.AttendConcertBean;

/**
 * Servlet implementation class AttendConcertController
 */
public class AttendConcertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendConcertController() {
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
		System.out.println("inside AttendConcertController-->doPost()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		String[] concertsToAttend=request.getParameterValues("concertattending");
		if(null==concertsToAttend ||(null!=concertsToAttend && concertsToAttend.length==0)){
			//if no values are selected, then re-direct to previous page
			RequestDispatcher rd= request.getRequestDispatcher("/Pages/ShowUpcomingConcerts.jsp");
			rd.forward(request, response);
		}else{
			AttendConcertDAO attendConcertDAO=new AttendConcertDAO();
			for(String concertID:concertsToAttend){
				AttendConcertBean attendConcertBean=new AttendConcertBean();
				attendConcertBean.setConcertID(Integer.parseInt(concertID));
				attendConcertBean.setUserId(loggedInUserID);
				try {
					attendConcertDAO.attendConcert(attendConcertBean);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError(e.getMessage());
					e.printStackTrace();
				}
			}
			RequestDispatcher rd= request.getRequestDispatcher("/Pages/userHomePage.jsp");
			rd.forward(request, response);
		}
	}

}
