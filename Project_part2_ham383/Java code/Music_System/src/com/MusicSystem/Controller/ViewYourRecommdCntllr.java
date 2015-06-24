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
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class ViewYourRecommdCntllr
 * servlet to view recommendation made by loggedin user(doGet)
 */
public class ViewYourRecommdCntllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewYourRecommdCntllr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ViewYourRecommdCntllr-->doGet");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		ViewYourRecommdDAO viewYourRecommdDAO=new ViewYourRecommdDAO();
		try {
			List<ConcertDetailsBean> selfRecomendConcertList=viewYourRecommdDAO.viewSelfConcertReccomend(userId);
			request.setAttribute("selfRecomendConcertList", selfRecomendConcertList);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowSelfRcmdList.jsp");
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
		System.out.println("Inside ViewYourRecommdCntllr-->doPost");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("SessionUsername");
		String[] concertIdToBeDel=request.getParameterValues("recomdToBeDel");
		if(null==concertIdToBeDel || (null!=concertIdToBeDel && concertIdToBeDel.length==0)){
			//if no concert is selected, then redirect back to previous page
			request.setAttribute("cncrtNotSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowSelfRcmdList.jsp");
			rd.forward(request, response);
		}else{
			//proceed to delete the recommendation
			ViewYourRecommdDAO viewYourRecommdDAO=new ViewYourRecommdDAO();
			for(String concertId: concertIdToBeDel){
				try {
					viewYourRecommdDAO.deleteSelfRecommendation(userId, Integer.parseInt(concertId));
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
					rd.forward(request, response);
				} catch (NumberFormatException | ConnectionFailureException
						| SQLException e) {
					ErrorLog.logError("ViewYourRecommdCntllr-->doPost-->"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}

}
