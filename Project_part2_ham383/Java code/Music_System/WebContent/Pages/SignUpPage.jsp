<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
<style>
<style type="text/css">
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

#nav {
    line-height:30px;
    height:500px;
    width:250px;
    float:left;
    padding:5px;	      
}
#nav1 {
    line-height:30px;
    height:500px;
    width:250px;
    float:right;
    padding:5px;
	}
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


</style>
</head>
<body>
<form name="signupform" method="post" action="/Music_System/SignUpController">
	<div id="header">
	<h1>User Register Page</h1>
	</div>
	<% String userExist= (String)request.getAttribute("userExist") ;
	   String artistNotExist= (String)request.getAttribute("artistNotExist") ;%>
	<div id="section">
	<table cellpadding="4" cellspacing="2" align="center">
		<tr>
			<td align="left">UserName</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_username" class="inputs" required> <% if(null!=userExist && userExist=="true"){ %><font color="red">username exists</font><% } %></td>
		</tr>
		<tr>
			<td align="left">Password</td>
			<td align="center">:</td>
			<td align="left"><input type="password" name="form_password" class="inputs"></td>
		</tr>
		<tr>
			<td align="left">First Name</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_fName" class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Last Name</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_lName" class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Phone no</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_phNo" max=10 class="inputs"></td>
		</tr>
		<tr>
			<td align="left">City</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_city" class="inputs"></td>
		</tr>
		<tr>
			<td align="left">State</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_state" class="inputs"></td>
		</tr>
		<tr>
			<td align="left">Artist ID</td>
			<td align="center">:</td>
			<td align="left"><input type="text" name="form_artistID" class="inputs"></td><% if(null!=artistNotExist && artistNotExist=="true"){ %><font color="red">artist does not exist</font><% } %>
		</tr>
		<tr>
			<td align="left">Date of Birth</td>
			<td align="center">:</td>
			<td align="left"><input type="date" name="form_dob" class="inputs"></td>
		</tr>
		<tr></tr>
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
	<%request.setAttribute("userExist", "false") ;
	  request.setAttribute("artistNotExist", "false");	%> 
	<!-- <a href="/Music_System/SignUpController">-->
	<br/><br/><br/><br/>
	</div>
	
	<div id="footer">
	<br/><br/><br/><br/><br/><br/>
	Estd 2002 Copyright ©musicindus.com
	</div>
</form>	
</body>
</html>