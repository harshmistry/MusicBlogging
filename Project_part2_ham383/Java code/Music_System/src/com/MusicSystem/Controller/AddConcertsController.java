package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.AddConcertDAO;
import com.MusicSystem.Connection.ArtistCncrtPrfmgPrfmdDAO;
import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ArtistDetailBean;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class AddConcertsController
 */
public class AddConcertsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddConcertsController() {
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
		System.out.println("Inside AddConcertsController-->doPost()");
		HttpSession session=request.getSession();
		String loggedInUserid=(String)session.getAttribute("SessionUsername");
		ArtistDetailBean sessionArtistBean=(ArtistDetailBean)session.getAttribute("artistBeanSession");
		ConcertDetailsBean concertDetailsBean=new ConcertDetailsBean();
		concertDetailsBean.setArtist_id(sessionArtistBean.getArtistId());
		concertDetailsBean.setBand_id(sessionArtistBean.getBandId());
		concertDetailsBean.setMusic_category_id(sessionArtistBean.getMusicCategoryId());
		concertDetailsBean.setMusic_subcategory_id(sessionArtistBean.getMusicSubCategoryId());
		concertDetailsBean.setConcert_venue_city(request.getParameter("concertCity"));
		concertDetailsBean.setConcert_venue_state(request.getParameter("concertState"));
		concertDetailsBean.setConcert_price(Integer.parseInt(request.getParameter("concertPrice")));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate=null;
		Date toDay=new Date();
		try {
			utilDate = sdf.parse(request.getParameter("concertDate"));
		} catch (ParseException e1) {
			ErrorLog.logError(e1.getMessage());			
		}
		concertDetailsBean.setConcert_date(new java.sql.Date(utilDate.getTime()));
		concertDetailsBean.setTotal_tickets(Integer.parseInt(request.getParameter("concertTotalTicket")));
		concertDetailsBean.setConcert_time(request.getParameter("concertTime"));
		concertDetailsBean.setConcert_name(request.getParameter("concertName"));
		if(toDay.before(utilDate) || toDay.equals(utilDate)){
			//if concert date is future date, then allow to insert
			AddConcertDAO addConcertDAO=new AddConcertDAO();
			try {
				addConcertDAO.addConcertDetail(concertDetailsBean);
				//once concert is added, update session list of concerts to be performed
				ArtistCncrtPrfmgPrfmdDAO cncrtPrfmgPrfmdDAO=new ArtistCncrtPrfmgPrfmdDAO();
				List<ConcertDetailsBean> cncrtPrfmgList=cncrtPrfmgPrfmdDAO.fetchConcertPerformng(loggedInUserid); //list of concerts performing
				session.setAttribute("cncrtPrfmgList", cncrtPrfmgList);
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError(e.getMessage());
				e.printStackTrace();
			}
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");
			rd.forward(request, response);
		}else{
			//if date is past, then redirect to previous page
			request.setAttribute("pastDateConcert", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/AddConcerts.jsp");
			rd.forward(request, response);
			
		}
	}

}
