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
import com.MusicSystem.Connection.ShowConcertAttendedDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class ShowConcertsAttendedController
 */
public class ShowConcertsAttendedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowConcertsAttendedController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowConcertsAttendedController-->doGet()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		ShowConcertAttendedDAO showConcertAttendedDAO=new ShowConcertAttendedDAO();
		try {
			List<ConcertDetailsBean> concertAttendedList=showConcertAttendedDAO.showConcertsAttended(loggedInUserID);
			request.setAttribute("concertAttendedList", concertAttendedList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowConcertAttended.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError(e.getMessage());
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
