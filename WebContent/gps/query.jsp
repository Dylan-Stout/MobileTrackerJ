<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

<title>GPS Query</title>
</head>
<body>

      
<!-- Learn about this code on MDN: https://developer.mozilla.org/en-US/docs/Learn/HTML/Forms/How_to_structure_an_HTML_form -->

<div data-role="page">
  <div data-role="header">
    <h1>GPS Query</h1>
  </div>

  <div data-role="main" class="ui-content queryBox">
    <div data-role="controlgroup" data-type="vertical">
   		<center><h2>Time Frame</h2></center> 
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=today" class="ui-btn" data-ajax="false">0-24 Hours</a>
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=yesterday" class="ui-btn" data-ajax="false">24-48 Hours</a>
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=seven_days" class="ui-btn" data-ajax="false">Past Week</a>
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=month" class="ui-btn" data-ajax="false">Past Month</a>
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=year" class="ui-btn" data-ajax="false">Past Year</a>
    	</br><center><b>CAUTION:</b> QUERYING 'ALL DATES' MAY TAKE A FEW MINUTES.<br></center></br>
    	<a href="${pageContext.request.contextPath}/gps/MapGps?time=all" class="ui-btn" data-ajax="false">All Dates</a>
    </div>
  </div>
	
	<div class="ui-field-contain queryBox">
		<form action="${pageContext.request.contextPath}/gps/MapGps">
    		<label for="textinput-fc"> Custom Date:</label>
    		<input name="textinput-fc" id="textinput-fc" placeholder="MM/DD/YYYY" value="" type="text">
    		<input type="submit" value="Search Date">
    	</form>
	</div>

  <div data-role="footer">
    <center>Data may be falsified and information contained in these reports is confidential and are not to be viewed or used without expressed permissions</center>
  </div>
</div> 
            <style>
            	.queryBox{
            		padding: 3% 15% !important; 
            	}
            </style>  
</body>
</html>