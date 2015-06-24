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
import com.MusicSystem.Connection.ShowUserFollowingDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class ShowUserFollowingController
 */
public class ShowUserFollowingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUserFollowingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowUserFollowingController-->doGet()");
		HttpSession session=request.getSession();
		String userid=(String)session.getAttribute("SessionUsername");
		ShowUserFollowingDAO showUserFollowingDAO=new ShowUserFollowingDAO();
		try {
			List<UserBean> userBeanFollowingList=showUserFollowingDAO.showUserFollowing(userid);
			request.setAttribute("userBeanFollowingList", userBeanFollowingList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowUserFollowing.jsp");
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
