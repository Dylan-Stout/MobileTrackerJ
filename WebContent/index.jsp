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

  <div data-role="main" class="ui-content">
   <center><h2>Time Frame</h2></center> 
    <a href="${pageContext.request.contextPath}/MapGps?time=today" class="ui-btn">0-24 Hours</a>
    <a href="${pageContext.request.contextPath}/MapGps?time=yesterday" class="ui-btn">24-48 Hours</a>
    <a href="${pageContext.request.contextPath}/MapGps?time=seven_days" class="ui-btn">Past 7 days</a>
    <center><b>CAUTION:</b> USING 'ALL DATES' WILL TAKE CONSIDERABLE PROCESSING TIME BASED ON AVAILABLE DATA</center>
    <a href="${pageContext.request.contextPath}/MapGps?time=all" class="ui-btn">All Dates (CSV)</a>
  </div>
	
	<div class="ui-field-contain">
		<form action="${pageContext.request.contextPath}/MapGps">
    		<label for="textinput-fc"> Custom Date:</label>
    		<input name="textinput-fc" id="textinput-fc" placeholder="MM/DD/YYYY" value="" type="text">
    		<input type="submit" value="Search Date">
    	</form>
	</div>

  <div data-role="footer">
    <h1>Data may be falsified and information contained in these reports are confidential and are not for public use or direct evidence</h1>
  </div>
</div> 

</body>
</html>