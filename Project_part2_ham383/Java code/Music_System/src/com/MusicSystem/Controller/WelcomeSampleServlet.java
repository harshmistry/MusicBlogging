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

import com.MusicSystem.Connection.ArtistCncrtPrfmgPrfmdDAO;
import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.FetchArtistDetailDAO;
import com.MusicSystem.Connection.LoginDao;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.ArtistDetailBean;
import com.MusicSystem.bean.ConcertDetailsBean;
import com.MusicSystem.bean.LoginBean;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class WelcomeSampleServlet
 */
public class WelcomeSampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeSampleServlet() {
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
		// TODO Auto-generated method stub
		System.out.println("Made to welcome servlet");
		//System.out.println("in servlet controller");
		LoginBean loginBean=new LoginBean();
		loginBean.setUsername(request.getParameter("username"));
		loginBean.setPassword(request.getParameter("password"));
		
		HttpSession session=request.getSession(); 
		//System.out.println("Username: "+loginBean.getUsername());
		session.setAttribute("SessionUsername", loginBean.getUsername());  //so that login id is visible throughout the session
		session.setMaxInactiveInterval(180);     //session will expire if page remains inactive till 3 minutes
		//request.setAttribute("employeeMap",loginBean.getLogin_id());
		//request.setAttribute("pas",loginBean.getPassword());
		//System.out.println("Before calling loginDao");
		LoginDao loginDao=new LoginDao();
		UserBean userBean=null;
		try {
			userBean=loginDao.authenticateLogin(loginBean);
		} catch (ConnectionFailureException e) {
			ErrorLog.logError("WelcomeSampleServlet-->1-->"+e.getMessage());
		} catch (SQLException e) {
			ErrorLog.logError("WelcomeSampleServlet-->2-->"+e.getMessage());
		}
		System.out.println("Exiting welcome servlet");
		if(null!=userBean && null!=userBean.getFirstName()){//if user with entered credential exist then direct to homepage
			if(userBean.getIsArtist()==0){
				RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
				request.setAttribute("userBean",userBean);
				rd.forward(request, response);
			}else if(userBean.getIsArtist()==1){
				//if artist has logged in
				ArtistDetailBean artistBean1=new ArtistDetailBean();
				artistBean1.setArtistId(loginBean.getUsername());
				FetchArtistDetailDAO fetchArtistDetailDAO=new FetchArtistDetailDAO();
				ArtistCncrtPrfmgPrfmdDAO cncrtPrfmgPrfmdDAO=new ArtistCncrtPrfmgPrfmdDAO();
				try {
					ArtistDetailBean artistBean2=fetchArtistDetailDAO.fetchArtistDetail(artistBean1);
					List<ConcertDetailsBean> cncrtPrfmgList=cncrtPrfmgPrfmdDAO.fetchConcertPerformng(artistBean1.getArtistId()); //list of concerts performing
					List<ConcertDetailsBean> cncrtPrfmdList=cncrtPrfmgPrfmdDAO.fetchConcertPerformng(artistBean1.getArtistId()); //list of concerts already performed
					session.setAttribute("cncrtPrfmgList", cncrtPrfmgList);
					session.setAttribute("cncrtPrfmdList", cncrtPrfmdList);
					if(null!=artistBean2){
						System.out.println("artistbean not null");
						artistBean2.setArtistId(loginBean.getUsername());
						System.out.println(artistBean2.getMusicCategoryName()+"  "+artistBean2.getMusicSubCategoryName());
					}
					else
					 System.out.println("astist bean null");
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistHomePage.jsp");
					request.setAttribute("userBean",userBean);
					session.setAttribute("artistBeanSession", artistBean2);
					rd.forward(request, response);
				} catch (ConnectionFailureException | SQLException e) {
					ErrorLog.logError("WelcomeSampleServlet-->3-->"+e.getMessage());
					e.printStackTrace();
				}
				
			}
		}
		else if(null!=userBean && null==userBean.getFirstName()){//if no user exist, then redirect to login page
			RequestDispatcher rd=request.getRequestDispatcher("/WelcomePage.jsp");
			request.setAttribute("invalidUser", "true");
			rd.forward(request, response);
		}
	}

}
