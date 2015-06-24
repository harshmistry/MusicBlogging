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

import com.MusicSystem.Connection.ArtistCncrtPrfmgPrfmdDAO;
import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.EditConcertInfoDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class EditConcertInfoContllr
 */
public class EditConcertInfoContllr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditConcertInfoContllr() {
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
		System.out.println("Inside EditConcertInfoContllr-->doPost()");
		HttpSession session=request.getSession();
		List<ConcertDetailsBean> cncrtPrfmgList=(List<ConcertDetailsBean>)session.getAttribute("cncrtPrfmgList");
		String concertIdToEdit=request.getParameter("editConcertInfoId");
		ConcertDetailsBean concertDetailsBean=new ConcertDetailsBean();
		concertDetailsBean.setConcert_name(request.getParameter("concertnameEdit"));
		concertDetailsBean.setConcert_time(request.getParameter("concertTimeEdit"));
		concertDetailsBean.setConcert_venue_city(request.getParameter("concertCityEdit"));
		concertDetailsBean.setConcert_venue_state(request.getParameter("concertStateEdit"));
		concertDetailsBean.setConcert_price(Integer.parseInt(request.getParameter("concertPriceEdit")));
		concertDetailsBean.setTotal_tickets(Integer.parseInt(request.getParameter("concertTicketsEdit")));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate=null;
		try {
			utilDate = sdf.parse(request.getParameter("concertDateEdit"));
		} catch (ParseException e1) {
			ErrorLog.logError(e1.getMessage());			
		}
		concertDetailsBean.setConcert_date(new java.sql.Date(utilDate.getTime()));
		
		if(null==concertIdToEdit ||(null!=concertIdToEdit && concertIdToEdit.equals(""))){
			//redirect to previous page
			request.setAttribute("concertToEditNotSelected", "true");
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowCncrtPrfmg.jsp");
			rd.forward(request, response);
		}else{
			concertDetailsBean.setConcert_id(Integer.parseInt(concertIdToEdit));
			//if any value if kept blank, set to original value
			if(null!=cncrtPrfmgList && !cncrtPrfmgList.isEmpty()){
				for(ConcertDetailsBean oldConcertBean:cncrtPrfmgList){
					if(concertDetailsBean.getConcert_id()!=oldConcertBean.getConcert_id())
						continue;
					else{
						if(null==concertDetailsBean.getConcert_name() ||(null!=concertDetailsBean.getConcert_name() && concertDetailsBean.getConcert_name().equals("")))
							concertDetailsBean.setConcert_name(oldConcertBean.getConcert_name());
						if(null==concertDetailsBean.getConcert_time() ||(null!=concertDetailsBean.getConcert_time() && concertDetailsBean.getConcert_time().equals("")))
							concertDetailsBean.setConcert_time(oldConcertBean.getConcert_time());
						if(null==concertDetailsBean.getConcert_venue_city() ||(null!=concertDetailsBean.getConcert_venue_city() && concertDetailsBean.getConcert_venue_city().equals("")))
							concertDetailsBean.setConcert_venue_city(oldConcertBean.getConcert_venue_city());
						if(null==concertDetailsBean.getConcert_venue_state() ||(null!=concertDetailsBean.getConcert_venue_state() && concertDetailsBean.getConcert_venue_state().equals("")))
							concertDetailsBean.setConcert_venue_state(oldConcertBean.getConcert_venue_state());
						if(0==concertDetailsBean.getConcert_price())
							concertDetailsBean.setConcert_price(oldConcertBean.getConcert_price());
						if(0==concertDetailsBean.getTotal_tickets())
							concertDetailsBean.setTotal_tickets(oldConcertBean.getTotal_tickets());
						if(null==concertDetailsBean.getConcert_date() ||(null!=concertDetailsBean.getConcert_date() && concertDetailsBean.getConcert_date().equals("")))
							concertDetailsBean.setConcert_date(oldConcertBean.getConcert_date());
						concertDetailsBean.setArtist_id(oldConcertBean.getArtist_id());
						break;
					}
				}
			}
			//proceed to edit info
			concertDetailsBean.setConcert_id(Integer.parseInt(concertIdToEdit));
			EditConcertInfoDAO editConcertInfoDAO=new EditConcertInfoDAO();
			try {
				editConcertInfoDAO.editConcertInfo(concertDetailsBean);
				//once the concert info is updated, fetch updated list and set in session
				ArtistCncrtPrfmgPrfmdDAO cncrtPrfmgPrfmdDAO=new ArtistCncrtPrfmgPrfmdDAO();
				List<ConcertDetailsBean> cncrtPrfmgListNew=cncrtPrfmgPrfmdDAO.fetchConcertPerformng(concertDetailsBean.getArtist_id()); //list of concerts performing
				session.setAttribute("cncrtPrfmgList", cncrtPrfmgListNew);
				RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");
				rd.forward(request, response);
			} catch (ConnectionFailureException | SQLException e) {
				ErrorLog.logError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
