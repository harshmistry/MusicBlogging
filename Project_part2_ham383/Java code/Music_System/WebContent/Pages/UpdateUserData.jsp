<%@page import="java.sql.Date"%>
<%@page import="com.MusicSystem.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change user data</title>
<script type="text/javascript">
	window.onload=function(){
	var tfrow = document.getElementById('tfhover').rows.length;
	var tbRow=[];
	for (var i=1;i<tfrow;i++) {
		tbRow[i]=document.getElementById('tfhover').rows[i];
		tbRow[i].onmouseover = function(){
		  this.style.backgroundColor = '#ffffff';
		};
		tbRow[i].onmouseout = function() {
		  this.style.backgroundColor = '#d4e3e5';
		};
	}
};
</script>
<style>
table.tftable {font-size:12px;color:#333335;width:30%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
table.tftable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
table.tftable tr {background-color:#d4e3e5;}
table.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}

button {
   border: 2px solid #0a3c59;
   background: #3e779d;
   background: -webkit-gradient(linear, left top, left bottom, from(#1e83c7), to(#3e779d));
   background: -webkit-linear-gradient(top, #1e83c7, #3e779d);
   background: -moz-linear-gradient(top, #1e83c7, #3e779d);
   background: -ms-linear-gradient(top, #1e83c7, #3e779d);
   background: -o-linear-gradient(top, #1e83c7, #3e779d);
   background-image: -ms-linear-gradient(top, #1e83c7 0%, #3e779d 100%);
   padding: 10.5px 21px;
   -webkit-border-radius: 13px;
   -moz-border-radius: 13px;
   border-radius: 13px;
   -webkit-box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   -moz-box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   text-shadow: #bd7f7f 0 1px 0;
   color: #061f6b;
   font-size: 14px;
   font-family: helvetica, serif;
   text-decoration: none;
   vertical-align: middle;
   }
.button:hover {
   border: 2px solid #0a3c59;
   text-shadow: #1e4158 0 1px 0;
   background: #3e779d;
   background: -webkit-gradient(linear, left top, left bottom, from(#65a9d7), to(#3e779d));
   background: -webkit-linear-gradient(top, #65a9d7, #3e779d);
   background: -moz-linear-gradient(top, #65a9d7, #3e779d);
   background: -ms-linear-gradient(top, #65a9d7, #3e779d);
   background: -o-linear-gradient(top, #65a9d7, #3e779d);
   background-image: -ms-linear-gradient(top, #65a9d7 0%, #3e779d 100%);
   color: #fff;
   }
.button:active {
   text-shadow: #221e57 0 1px 0;
   border: 2px solid #292222;
   background: #21b555;
   background: -webkit-gradient(linear, left top, left bottom, from(#3e779d), to(#3e779d));
   background: -webkit-linear-gradient(top, #3e779d, #21b555);
   background: -moz-linear-gradient(top, #3e779d, #21b555);
   background: -ms-linear-gradient(top, #3e779d, #21b555);
   background: -o-linear-gradient(top, #3e779d, #21b555);
   background-image: -ms-linear-gradient(top, #3e779d 0%, #21b555 100%);
   color: #fff;
   }


html {
	margin: 0;
	padding: 0;
	}
body { 
	font: 75% georgia, sans-serif;
	line-height: 1.88889;
	color: #0AAEF5; 
    background-image: url("bgal.jpg");
    background-repeat:no-repeat;
    background-size:100%;	margin: 0; 
	padding: 0;
	}
p { 
	margin-top: 0; 
	text-align: justify;
	}
h3 { 
	font: italic normal 1.4em georgia, sans-serif;
	letter-spacing: 1px; 
	margin-bottom: 0; 
	color: #0AAEF5;
	}
a:link { 
	font-weight: bold; 
	text-decoration: none; 
	color: #0AAEF5;
	}
a:visited { 
	font-weight: bold; 
	text-decoration: none; 
	color:#0AAEF5;
	}
a:hover, a:focus, a:active { 
	text-decoration: italics; 
	color: white;
	}
abbr {
	border-bottom: none;
	}



#header {
	padding-top: 20px;
	height: 87px;
    background-color:D1AECD;
    color:gold;
    text-align:center;
}

#nav1 {
    line-height:30px;
    height:500px;
    width:250px;
    float:right;
    padding:5px;
	}


#nav {
    line-height:30px;
    height:500px;
    width:250px;
    float:left;
    padding:5px;	      
}
#section 
{
    width:800px;
    height:400px;
	float:left;
    padding:10px;	 	 
	margin-top: 0;
	color: #0AAEF5; 	
	text-align: justify;
	
	}
#footer {
    color:gold;
    clear:both;
    text-align: center; 
	height: 20px; 
	padding:5px;	 	 
	}
	
	th{
		align: center;
		cellpadding: 4;
		cellspacing: 2;
	}
	td{
		align: center;
		cellpadding: 4;
		cellspacing: 2;
	}
	td.ex{
		align: center;
		cellpadding: 0;
		cellspacing: 0;
	}
	table.px{
		resize: both ;
		overflow: auto ;
	}
#signout
{	
color: gold;
float: right;
}	

.inputs {
    height: 15px;
    border: 5px solid #EBE6E2;
    border-radius: 5px;
    -webkit-transition: all 0.3s ease-out;
    -moz-transition: all 0.3s ease-out;
    -ms-transition: all 0.3s ease-out;
    -o-transition: all 0.3s ease-out;
    transition: all 0.3s ease-out;
    width: 120px;
}

.inputs:focus {
    border-color: #BBB;
    outline: none;
}   

#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}

#section 
{
    width:500px;
    height:500px;
	float:center;
    padding:10px;	 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   padding:5px;	 	 
}
</style>
<script type="text/javascript">

</script>
</head>
<body>
<form name="updateUserData" method="post" action="ChangeSettingsController">
<table align="right">
	<tr>
		<td align="right"><a href="SignoutController">sign out</a><a href="HomePageController">homepage</a></td>
	</tr>
</table>
	<div id="header">
	<h1>User Register Page</h1>
	</div>
	<% UserBean userBeanFetched= (UserBean)request.getAttribute("fetchedUserData");
		String username="";
		String userFirstName="";
		String userLastName="";
		double userPhone=0.0;
		Date user_dob=null;
		String city="";
		String state="";
		String password="";
		if(null!=userBeanFetched){
			if(null!=userBeanFetched.getUserid())
				username=userBeanFetched.getUserid();
			if(null!=userBeanFetched.getFirstName())
				userFirstName=userBeanFetched.getFirstName();
			if(null!=userBeanFetched.getLastName())
				userLastName=userBeanFetched.getLastName();
				userPhone=userBeanFetched.getPhoneNo();
			if(null!=userBeanFetched.getDob())
				user_dob=userBeanFetched.getDob();
			if(null!=userBeanFetched.getCity())
				city=userBeanFetched.getCity();
			if(null!=userBeanFetched.getState())
				state=userBeanFetched.getState();
			if(null!=userBeanFetched.getPassword())
				password=userBeanFetched.getPassword();
		}%>
	<div id="section">
	<table id="tfhover" class="tftable" align="center">
		<tr>
			<td align="left">UserName</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_username" value=<%=username %> class="inputs" disabled></td>
		</tr>
		<tr>
			<td align="left">Old Password</td>
			<td align="center">:</td>
			<td align="left"><input type="password" name="form_oldPassword" class="inputs" required>
				<%if(null!=request.getAttribute("oldPassNotMatch") && request.getAttribute("oldPassNotMatch").equals("true")){ %>
					<font color="red">must match previous password</font><%}else{ %>must match previous password<%} %>
				<%if(null!=request.getAttribute("blankOldPasswordErr") && request.getAttribute("blankOldPasswordErr").equals("true")) {%>	
					<font color="red">please enter old-password</font><%} %>
			</td>
		</tr>
		<tr>
			<td align="left">New Password</td>
			<td align="center">:</td>
			<td align="left"><input type="password" name="form_newPassword" class="inputs" required>
				<%if(null!=request.getAttribute("blankNewPass") && request.getAttribute("blankNewPass").equals("true")) {%>	
					<font color="red">please enter new-password</font><%} %>
			</td>
		</tr>
		<tr>
			<td align="left">Re-confirm Password</td>
			<td align="center">:</td>
			<td align="left"><input type="password" name="form_reConfirmPassword" class="inputs" required>
				<%if(null!=request.getAttribute("newPassNotMatch") && request.getAttribute("newPassNotMatch").equals("true")){ %>
					<font color="red">must match new password</font><%}else{ %>must match new password<%} %>
				<%if(null!=request.getAttribute("blankReConfirmPass") && request.getAttribute("blankReConfirmPass").equals("true")) {%>	
					<font color="red">please enter reConfirm-password</font><%} %>
			</td>
		</tr>
		<tr>
			<td align="left">First Name</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_fName" value=<%=userFirstName %> class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Last Name</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_lName" value=<%=userLastName %> class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Phone no</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_phNo" value=<%=userPhone %> maxlength=10 class="inputs"></td>
		</tr>
		<tr>
			<td align="left">City</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_city" value=<%=city %> class="inputs"></td>
		</tr>
		<tr>
			<td align="left">State</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_state" value=<%=state %> class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Date of Birth</td>
			<td align="center">:</td>
			<td align="left"><input type="date" name="form_dob" value=<%=user_dob %> class="inputs" required>
				<%if(null!=request.getAttribute("blankDob") && request.getAttribute("blankDob").equals("true")) {%>	
					<font color="red">please enter date of birth</font><%} %>
			</td>
		</tr>
		<%	request.setAttribute("blankDob", "false");
			request.setAttribute("blankReConfirmPass", "false");
			request.setAttribute("newPassNotMatch", "false");
			request.setAttribute("blankOldPasswordErr", "false");
			request.setAttribute("oldPassNotMatch", "false");%>
		<tr>
			<td colspan=3><input type="hidden" name="fetchedOldPassword" value=<%=password %>></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td align="right"><input type="submit" value="Continue" class="button"></td>
			<td></td>
			<td align="center"><input type="reset" value="cancel" class="button"></td>
		</tr>
	</table> 
	<!-- <a href="/Music_System/SignUpController">-->
	</div>
	
	<div id="footer">
	Estd 2002 Copyright ©musicindus.com
	</div>
</form>	
</body>
</html>