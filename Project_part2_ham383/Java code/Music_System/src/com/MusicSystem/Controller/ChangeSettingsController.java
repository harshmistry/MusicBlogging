package com.MusicSystem.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MusicSystem.Connection.ConnectionFailureException;
import com.MusicSystem.Connection.FetchUserData;
import com.MusicSystem.Connection.UpdateUserDataDAO;
import com.MusicSystem.ExceptionLogger.ErrorLog;
import com.MusicSystem.bean.UserBean;

/**
 * Servlet implementation class ChangeSettingsController
 */
public class ChangeSettingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeSettingsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside setting controller-->doGET()");
		HttpSession session=request.getSession();
		UserBean userBeanFetched=new UserBean();
		userBeanFetched.setUserid((String) (null!=session.getAttribute("SessionUsername")?session.getAttribute("SessionUsername"):""));
		FetchUserData fetchUserData=new FetchUserData(); //FetchUserDataDAO
		try {
			request.setAttribute("fetchedUserData", fetchUserData.fetchUserData(userBeanFetched));
			session.setAttribute("fetchedUserBean", fetchUserData.fetchUserData(userBeanFetched));
		} catch (ConnectionFailureException | SQLException e) {
			ErrorLog.logError(e.getMessage());
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside ChangeSettingsController-->doPost()");
		HttpSession session=request.getSession();
		//response.sendRedirect("/Pages/UpdateUserData.jsp");
		request.setAttribute("fetchedUserData", session.getAttribute("fetchedUserBean"));
		RequestDispatcher rd=null;
		UserBean updatedUserBean=new UserBean();
		updatedUserBean.setUserid((String)session.getAttribute("SessionUsername"));
		updatedUserBean.setFirstName(request.getParameter("form_fName"));
		updatedUserBean.setLastName(request.getParameter("form_lName"));
		updatedUserBean.setPhoneNo(Double.parseDouble(request.getParameter("form_phNo")));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate=null;
		try {
			utilDate = sdf.parse(request.getParameter("form_dob"));
		} catch (ParseException e1) {
			ErrorLog.logError(e1.getMessage());			
		}
		updatedUserBean.setDob(new java.sql.Date(utilDate.getTime()));
		updatedUserBean.setCity(request.getParameter("form_city"));
		updatedUserBean.setState(request.getParameter("form_state"));
		
		String oldFetchedPassword=request.getParameter("fetchedOldPassword");
		String oldPasswordEntered=request.getParameter("form_oldPassword");
		String newPassword=request.getParameter("form_newPassword");
		String reConfirmPassword=request.getParameter("form_reConfirmPassword");
		if(null==oldPasswordEntered || oldPasswordEntered.equals("")){
			//if old password is not entered by user
			request.setAttribute("blankOldPasswordErr", "true");
			//response.sendRedirect("/Pages/UpdateUserData.jsp");
			rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
			rd.forward(request, response);
		}else if(!oldPasswordEntered.equals(oldFetchedPassword)){
				//if old password does not match with previous old password
				request.setAttribute("oldPassNotMatch", "true");
				//response.sendRedirect("/Pages/UpdateUserData.jsp");
				rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
				rd.forward(request, response);
		}else if(null==newPassword || newPassword.equals("")){
				//if new password is kept blank
				request.setAttribute("blankNewPass", "true");
				//response.sendRedirect("/Pages/UpdateUserData.jsp");
				rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
				rd.forward(request, response);
		}else if(null==reConfirmPassword || reConfirmPassword.equals("")){
				//if re-confirmed password is blank
				request.setAttribute("blankReConfirmPass", "true");
				//response.sendRedirect("/Pages/UpdateUserData.jsp");
				rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
				rd.forward(request, response);
		}else if(null!=newPassword && null!=reConfirmPassword && !newPassword.equals("") && !reConfirmPassword.equals("")){
				//if new password and re-confirmed password are entered
				if(!newPassword.equals(reConfirmPassword)){
					//if new password and re-confirmed password doesn't match
					request.setAttribute("newPassNotMatch", "true");
					//response.sendRedirect("/Pages/UpdateUserData.jsp");
					rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
					rd.forward(request, response);
				}
				else{
					updatedUserBean.setPassword(newPassword);
					if(null==updatedUserBean.getDob()){
						//if dob is kept blnk
						request.setAttribute("blankDob", "true");
						//response.sendRedirect("/Pages/UpdateUserData.jsp");
						rd=request.getRequestDispatcher("/Pages/UpdateUserData.jsp");
						rd.forward(request, response);
					}else{
						UpdateUserDataDAO updateUserDataDAO=new UpdateUserDataDAO();
						try {//update the userdata
							updateUserDataDAO.updateUserData(updatedUserBean);
						} catch (ConnectionFailureException | SQLException e) {
							ErrorLog.logError(e.getMessage());
							e.printStackTrace();
						}
						rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
						rd.forward(request, response);
					}
		}
		//re-direct to homepage
		/*RequestDispatcher rd=request.getRequestDispatcher("/Pages/userHomePage.jsp");
		rd.forward(request, response);*/
		
	}

}
}
