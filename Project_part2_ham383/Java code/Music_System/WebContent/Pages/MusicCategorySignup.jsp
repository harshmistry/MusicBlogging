<%@page import="com.MusicSystem.bean.MusicSubCategoryBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.MusicSystem.bean.MusicCategoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Music category selection</title>
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

</style>
</head>
<body>
<form name="musicCategorySignUp" method="post" action="MusicCatSignUpController">
	<!-- Main Table(You can define padding accordingly) -->
	<%List<MusicCategoryBean> musicCategoryBeanList=(ArrayList<MusicCategoryBean>)request.getAttribute("musicCatBean"); 
		if(null!=musicCategoryBeanList){%>
	<table align="center" border=1 width=50% id="tfhover" class="tftable">
		<tr>
			<th colspan=3>Music category</th>
			<th colspan=3>Music sub category</th>
		</tr>
		<tr>
			<td align=center>Select</td>
			<td align=center>Name</td>
			<td align=center>Description</td>
			<td align=center>Select</td>
			<td align=center>Name</td>
			<td align=center>Description</td>
		</tr>
		<%for(MusicCategoryBean musicCategoryBean: musicCategoryBeanList) {%>
		<tr>
			<td><input type="checkbox" name="categorySelected" value=<%=musicCategoryBean.getMusicCategoryID() %>></td>
			<td><%=musicCategoryBean.getCategoryName() %></td>
			<td><%=musicCategoryBean.getCategoryDescription() %></td>
			<td colspan=3>
				<%if(null!=musicCategoryBean.getSubCategoryBean() && !musicCategoryBean.getSubCategoryBean().isEmpty()) {%>
				<table align="center" border=1 id="tfhover" class="tftable" width=100%>
				<%for(MusicSubCategoryBean subCatBean: musicCategoryBean.getSubCategoryBean()){ %>
					<tr>
						<td width=26% align="center"><input type="checkbox" name="subCatSelected" value=<%=subCatBean.getSubCategoryID() %>/<%=subCatBean.getCategoryID() %>></td>
						<td width=26% align="center"><%=subCatBean.getSubCategoryName() %></td>
						<td align="center"><%=subCatBean.getSubCategoryDescription() %></td>
					</tr>
					<%} %>
				</table>
				<%} %>
			</td>
		</tr>
		<%} %>
		<tr>
			<td colspan=3 align="center"><input type="submit" value="continue" class="button"></td>
			<td colspan=3 align="center"><input type="reset" value="cancel" class="button"></td>
		</tr>	
	</table>				
	<%}else{ %>
	<font color="red"><b>Some problem occurred while displaying music category list, please try again</b>
	<%} %>
</form>
</body>
</html>