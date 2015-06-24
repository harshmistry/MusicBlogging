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

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.SignupDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.Utility.PopulateMusicCategory;
import com.MusicSystem.Utility.UtilityPropertyFile;
import com.MusicSystem.bean.MusicCategoryBean;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class SignUpController
 */
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpController() {
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
		System.out.println("SignUpController-->doPost");
		HttpSession session=request.getSession();
		String username=(String) (null!=session.getAttribute("SessionUsername")?session.getAttribute("SessionUsername"):"");
		/*System.out.println("Session username: "+username);
		System.out.println("username:"+request.getParameter("form_username"));
		System.out.println("first name:"+request.getParameter("form_fName"));
		System.out.println("last name:"+request.getParameter("form_lName"));
		System.out.println("city:"+request.getParameter("form_city"));
		System.out.println("state:"+request.getParameter("form_state"));
		System.out.println("artist id:"+request.getParameter("form_artistID"));
		System.out.println("dob:"+request.getParameter("form_dob"));*/
		
		UserBean userBean=new UserBean();
		userBean.setUserid(request.getParameter("form_username"));
		userBean.setFirstName(request.getParameter("form_fName"));
		userBean.setLastName(request.getParameter("form_lName"));
		userBean.setPhoneNo(Double.parseDouble(request.getParameter("form_phNo")));
		userBean.setCity(request.getParameter("form_city"));
		userBean.setState(request.getParameter("form_state"));
		userBean.setArtistID(request.getParameter("form_artistID"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate=null;
		try {
			utilDate = sdf.parse(request.getParameter("form_dob"));
		} catch (ParseException e1) {
			ErrorLog.logError(e1.getMessage());			
		}
		userBean.setDob(new java.sql.Date(utilDate.getTime()));
		if(null!=request.getParameter("form_artistID") && !request.getParameter("form_artistID").equals("")){
			//if artistID is entered, then set isArtist=1, else 0
			userBean.setIsArtist(Integer.parseInt(UtilityPropertyFile.getPropertyValue("isArtist")));
		}
		else if(request.getParameter("form_artistID").equals("")){
			userBean.setIsArtist(Integer.parseInt(UtilityPropertyFile.getPropertyValue("isNotArtist")));
		}
		userBean.setPassword(request.getParameter("form_password"));
		SignupDAO signupDAO=new SignupDAO();
		try {
			if(null!=request.getParameter("form_artistID") && !request.getParameter("form_artistID").equals("") && signupDAO.checkArtistIDExist(userBean.getArtistID())){ //when artistID is entered
				if(signupDAO.checkUsernameExist(userBean.getUserid())){//returns true if no username found
					signupDAO.signUPInsert(userBean);
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/ArtistSignUp.jsp");
					session.setAttribute("SessionUsername", userBean.getUserid());
					List<MusicCategoryBean> musicCategoryBean=new PopulateMusicCategory().populateCategory();
					request.setAttribute("musicCatBean", musicCategoryBean);
					System.out.println("forwarding to ArtistSignUp.jsp");
					rd.forward(request, response);
				}
				else{
					//redirect to signup page stating that username already exist
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/SignUpPage.jsp");
					//session.setAttribute("userExist","true");
					request.setAttribute("userExist", "true");
					System.out.println("forwarding to SignUpPage.jsp--1");
					rd.forward(request, response);
				}
			}
			else if(null==request.getParameter("form_artistID") || request.getParameter("form_artistID").equals("")){ //when artistID is not entered
				if(signupDAO.checkUsernameExist(userBean.getUserid())){//returns true if no username found, and direct to category selection page
					signupDAO.signUPInsert(userBean);
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/MusicCategorySignup.jsp");
					session.setAttribute("SessionUsername", userBean.getUserid());
					List<MusicCategoryBean> musicCategoryBean=new PopulateMusicCategory().populateCategory();
					request.setAttribute("musicCatBean", musicCategoryBean);
					System.out.println("forwarding to MusicCategorySignup.jsp");
					rd.forward(request, response);
				}
				else{
					//redirect to signup page stating that username already exist
					RequestDispatcher rd=request.getRequestDispatcher("/Pages/SignUpPage.jsp");
					//session.setAttribute("userExist","true");
					request.setAttribute("userExist", "true");
					System.out.println("forwarding to SignUpPage.jsp--2");
					rd.forward(request, response);
				}
			}
			else{
				RequestDispatcher rd=request.getRequestDispatcher("/Pages/SignUpPage.jsp");
				//session.setAttribute("userExist","true");
				request.setAttribute("artistNotExist", "true");
				System.out.println("forwarding to SignUpPage.jsp--3");
				rd.forward(request, response);
			}
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError(e.getMessage());
			e.printStackTrace();
		}
	}

}
