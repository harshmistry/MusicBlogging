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
import com.MusicSystem.Connection.DelConcrtAttendingDAO;
import com.MusicSystem.Connection.ShowConcrtAttendingDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class ShowCncrtAttendingCntllr
 * servlet to show concerts that logged in user is going to attend(doGet)
 * servlet to delete concert which user is going to attend(doPost)
 */
public class ShowCncrtAttendingCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowCncrtAttendingCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowCncrtAttendingCntllr-->doGet()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		ShowConcrtAttendingDAO showConcrtAttendingDAO=new ShowConcrtAttendingDAO();
		try {
			List<ConcertDetailsBean> concertAttendingList=showConcrtAttendingDAO.fetchConcrtAttendingList(userId);
			request.setAttribute("concertAttendingList", concertAttendingList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowConcertAttending.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("ShowCncrtAttendingCntllr-->doGet()-->"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowCncrtAttendingCntllr-->dopost()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		String[] delConcertAttending=request.getParameterValues("deleteConcertAttending");
		if(null==delConcertAttending ||(null!=delConcertAttending && delConcertAttending.length==0)){
			//if no concerts are selected, then redirect to previous page
			request.setAttribute("concertNotSelectedtoDel", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowConcertAttending.jsp");
			rd.forward(request, response);
		}else{
			//allow to remove concert from attending list, then direct to homepage
			DelConcrtAttendingDAO delConcrtAttendingDAO=new DelConcrtAttendingDAO();
			for(String concertId: delConcertAttending){
				try {
					delConcrtAttendingDAO.delCOncertAttending(userId, Integer.parseInt(concertId));
				} catch (NumberFormatException | ConnectionFailureException
						| SQLException e) {
					ErrorLog.logError("ShowCncrtAttendingCntllr-->doPost()-->"+e.getMessage());
					e.printStackTrace();
				}
			}
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
			rd.forward(request, response);
		}
	}

}
