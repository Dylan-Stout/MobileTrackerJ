
<%-- 
    Document   : Login - MobileTrackerJ
    Created on : 1/20/2017
    Author     : Dylan Stout
    
    Default login authentication page for MobileTrackerJ. 
    
--%>    
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>MobileTracker - Login</title>
       <meta name="viewport" content="width=device-width, initial-scale=1">	
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script> 
        <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script> 
         <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"> 
</head>
<body id="mainBody">
	<div style="background-image: url(${pageContext.request.contextPath}/images/login_background.png); background-size: 100%; " data-role="page" data-theme="b" id="p1">
		<div data-role="header" data-position="fixed" data-tap-toggle="false"
			data-theme="a">
			<h1>Mobile Tracker</h1>

		</div>
		<div role="main" class="ui-content" data-theme="a">
			<div class="ui-body ui-body-a ui-corner-all login">

				<center><h3>User Authentication</h3></center>

				<p>
			<form id="loginForm" method="post" class="formField" action="${pageContext.request.contextPath}/ValidateLogin">
	
			<b>USERNAME:</b> 
			<input type="text" id="userName" name="userName" value='${user.userName}' onkeypress="handleCarriageReturn(event)" required/> 
			<b>PASSWORD:</b> 
			<input type="password" id="password" name="password" value='' required/> 
			<center>
			<button type="submit" value="LOGIN" style="width:90%;">LOGIN</button>
			</center>		
			<font size=2 color=red>${user.loginError}</font>
		</form></p>
			</div>
		</div>
		<div data-role="footer" data-position="fixed" data-tap-toggle="false">
			<h1>GreyHelm&copy; ver. 0.5.1</h1>

		</div>
	</div>
</body>
</html>