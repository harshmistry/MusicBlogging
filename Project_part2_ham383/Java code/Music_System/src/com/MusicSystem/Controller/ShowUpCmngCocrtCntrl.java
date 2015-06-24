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
import com.MusicSystem.Connection.ShowUpCmngConcertDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class ShowUpCmngCocrtCntrl
 * class to display upcoming concerts to user based on artist and music he likes
 */
public class ShowUpCmngCocrtCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUpCmngCocrtCntrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ShowUpCmngCocrtCntrl-->doGet()");
		HttpSession session=request.getSession();
		String loggedInUserID=(String)session.getAttribute("SessionUsername");
		ShowUpCmngConcertDAO upComingConcertDAO= new ShowUpCmngConcertDAO();
		try {
			List<ConcertDetailsBean> artistUpCmgConcertList=upComingConcertDAO.showArtistUpCmgConcert(loggedInUserID);
			List<ConcertDetailsBean> musicUpCmgConcertList= upComingConcertDAO.showMusicUpCmgConcert();
			request.setAttribute("artistUpCmgConcertList", artistUpCmgConcertList);
			request.setAttribute("musicUpCmgConcertList", musicUpCmgConcertList);
			if((null!=artistUpCmgConcertList && !artistUpCmgConcertList.isEmpty()) || (null!=musicUpCmgConcertList && !musicUpCmgConcertList.isEmpty())){
				//remove duplicate concerts
				for(int i=0;i<artistUpCmgConcertList.size();i++){
					ConcertDetailsBean artistConListBean=artistUpCmgConcertList.get(i);
					for(int j=0;j<musicUpCmgConcertList.size();j++){
						ConcertDetailsBean musicConListBean=musicUpCmgConcertList.get(j);
						if(artistConListBean.getConcert_id()==musicConListBean.getConcert_id()){
							musicUpCmgConcertList.remove(j);
						}
					}
				}
			}
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowUpcomingConcerts.jsp");
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
