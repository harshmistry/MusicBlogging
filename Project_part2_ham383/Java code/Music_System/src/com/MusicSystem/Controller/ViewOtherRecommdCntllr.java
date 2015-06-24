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

import com.MusicSystem.Connection.AttendConcertDAO;
import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.FetchOtherUserReccoDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.AttendConcertBean;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class ViewOtherRecommdCntllr
 * servlet to view recommendation from other user(doGet)
 * servlet to add concert to attend from list of recommended concerts(doPost)
 */
public class ViewOtherRecommdCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOtherRecommdCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ViewOtherRecommdCntllr-->doGet()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		FetchOtherUserReccoDAO fetchOtherUserReccoDAO=new FetchOtherUserReccoDAO();
		try {
			List<ConcertDetailsBean> othersRecomendConcertList=fetchOtherUserReccoDAO.ftchOthrUserReccomd(userId);
			request.setAttribute("othersRecomendConcertList", othersRecomendConcertList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowOthersRcmdList.jsp");
			rd.forward(request, response);
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError("ViewYourRecommdCntllr-->doGet-->"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ViewOtherRecommdCntllr-->doPost()");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		String[] concertIdToAttend=request.getParameterValues("recomdConcertToAttend");
		if(null==concertIdToAttend || (null!=concertIdToAttend && concertIdToAttend.length==0)){
			//if no recommendation is selected, then redirect him to previous page
			request.setAttribute("cncrtNotSelected","true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowOthersRcmdList.jsp");
			rd.forward(request, response);
		}else{
			//allow to insert into attend concert
			AttendConcertDAO attendConcertDAO=new AttendConcertDAO();
			for(String concertId:concertIdToAttend){
				AttendConcertBean attendConcertBean=new AttendConcertBean();
				attendConcertBean.setConcertID(Integer.parseInt(concertId));
				attendConcertBean.setUserId(userId);
				try {
					attendConcertDAO.attendConcert(attendConcertBean);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("ViewYourRecommdCntllr-->doPost-->"+e.getMessage());
					e.printStackTrace();
				}
			}
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
			rd.forward(request, response);
		}
	}

}
