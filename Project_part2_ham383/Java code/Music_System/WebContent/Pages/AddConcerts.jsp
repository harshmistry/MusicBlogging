<%@page import="com.MusicSystem.bean.ArtistDetailBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create concert</title>
<center>Create concert</center>
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
<style type="text/css">
html {
	margin: 0;
	padding: 0;
	}
body { 
	font: 75% georgia, sans-serif;
	line-height: 1.88889;
	color: Blue; 
    background-image: url("bgal.jpg");
    background-repeat:no-repeat;
    background-size:100%;	
	margin: 0; 
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
	color: #7D775C;
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
	color:white;
	}
abbr {
	border-bottom: none;
	}



#header {
	padding-top: 20px;
	height: 87px;
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
    color:black;
    clear:both;
    text-align: center; 
	padding:5px;	 	 
	height: 10px; 
	color: gold;
	}

#signout
{	
color: #0AAEF5;
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
<style type="text/css">
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

</style>
</head>
<body>
<form name="createConcertForm" method="post" action="/Music_System/AddConcertsController">
	<table align="right">
	<tr>
		<td align="right"><a href="/Music_System/SignoutController">sign out</a>&nbsp;&nbsp;&nbsp;<a href="/Music_System/ArtistHomePgContllr">homepage</a></td>
	</tr>
	</table>
	<br/><br/><br/><br/><br/><br/><br/>
	<%ArtistDetailBean artistDetailBean=(ArtistDetailBean)session.getAttribute("artistBeanSession"); %>
	<table align=center id="tfhover" class="tftable">
		<tr>
			<th align="center">Concert name</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertName" class="inputs" required></td>
		</tr>
		<tr>
			<th align="center">City</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertCity" class="inputs" required></td>
		</tr>
		<tr>
			<th align="center">State</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertState" class="inputs" required></td>
		</tr>
		<tr>
			<th align="center">Date</th>
			<td align="center">:</td>
			<td align="center"><input type="date" name="concertDate" class="inputs" required>
			<%if(null!=request.getAttribute("pastDateConcert") && request.getAttribute("pastDateConcert").equals("true")){ %>
				<font color="red"><b>Please enter future date</b></font>
			<% request.setAttribute("pastDateConcert","false");} %>
			</td>
		</tr>
		<tr>
			<th align="center">Time</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertTime" class="inputs" required></td>
		</tr>
		<tr>
			<th align="center">Price</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertPrice" class="inputs" required></td>
		</tr>
		<tr>
			<th align="center">Total tickets</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertTotalTicket" class="inputs" required></td>
		</tr>
		<%if(null!=artistDetailBean) {%>
		<tr>
			<th align="center">Music category</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertCategory" value=<%=artistDetailBean.getMusicCategoryName() %> class="inputs" disabled></td>
		</tr>
		<tr>
			<th align="center">Music sub-category</th>
			<td align="center">:</td>
			<td align="center"><input type="text" name="concertSubCat" value=<%=artistDetailBean.getMusicSubCategoryName() %> class="inputs" disabled></td>
		</tr>
		<%} %>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td align="center" colspan=2><input type="submit" value="create" class="button"></td>
			<td align="right"><input type="reset" value="clear" class="button"></td>
		</tr>
	</table>
</form>

</body>
</html>