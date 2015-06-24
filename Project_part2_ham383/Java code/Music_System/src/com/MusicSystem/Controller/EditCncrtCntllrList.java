package com.MusicSystem.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.bean.ConcertDetailsBean;

/**
 * Servlet implementation class EditCncrtCntllrList
 */
public class EditCncrtCntllrList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCncrtCntllrList() {
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
		System.out.println("Inside EditCncrtCntllrList-->doPost()");
		HttpSession session=request.getSession();
		List<ConcertDetailsBean> cncrtPrfmgList=(List<ConcertDetailsBean>)session.getAttribute("cncrtPrfmgList");
		String concertIdToEdit=request.getParameter("editConcertInfoId");
		ConcertDetailsBean concertDetailsBean=new ConcertDetailsBean();
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
						concertDetailsBean.setConcert_name(oldConcertBean.getConcert_name());					
						concertDetailsBean.setConcert_time(oldConcertBean.getConcert_time());					
						concertDetailsBean.setConcert_venue_city(oldConcertBean.getConcert_venue_city());					
						concertDetailsBean.setConcert_venue_state(oldConcertBean.getConcert_venue_state());					
						concertDetailsBean.setConcert_price(oldConcertBean.getConcert_price());					
						concertDetailsBean.setTotal_tickets(oldConcertBean.getTotal_tickets());					
						concertDetailsBean.setConcert_date(oldConcertBean.getConcert_date());
						concertDetailsBean.setMusic_category_id(oldConcertBean.getMusic_category_id());
						concertDetailsBean.setMusic_category_name(oldConcertBean.getMusic_category_name());
						concertDetailsBean.setMusic_subcategory_id(oldConcertBean.getMusic_subcategory_id());
						concertDetailsBean.setMusic_subcategory_name(oldConcertBean.getMusic_subcategory_name());
						break;
					}
				}
			}
			request.setAttribute("cncrtToEdit", concertDetailsBean);
			RequestDispatcher rd=request.getRequestDispatcher("/Pages/ShowCncrtPrfmg.jsp");
			rd.forward(request, response);
		}
	}

}
