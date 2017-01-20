package com.mobile.error;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mobile.core.User;
import com.mobile.ui.UIConstants;

/**
 * Servlet implementation class GeneralExceptionHandler
 */
@WebServlet("/GeneralExceptionHandler")
public class GeneralExceptionHandler extends HttpServlet {

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneralExceptionHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -1285915499348177749L;
	
	protected static Logger logger = Logger.getLogger(GeneralExceptionHandler.class); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processError(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processError(request, response);
	
	}

	/**
	 * redirect to error page with data collected from exception
	 */
	private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Analyze the servlet exception
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		HttpSession session = request.getSession(); 
		User user = (User)session.getAttribute("user"); 
//TODO - Add GPS session data
		
		boolean nullValueInSession = false; 
		
		if (servletName == null) {
			servletName = "Unknown";
			nullValueInSession = true;
		}
		if (requestUri == null) {
			requestUri = "Unknown";
			nullValueInSession = true; 
		}
		if(user == null){ 
			nullValueInSession = true;
		}

		
		/* stack trace to logs */ 
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		if(throwable != null)
			throwable.printStackTrace(pw);
		String stackTrace = sw.toString(); 
		int errId = (int)(Math.random() * 100000 + 1); 
		logger.error("ERROR ID " + errId + "\nError stacktrace: " + stackTrace);
		
		
		/* ui message */ 
		String statusCodeMessage = null; 
		switch (statusCode) {
		case 400:
			statusCodeMessage = "Bad Request";
			break;
		case 401:
			statusCodeMessage = "Unauthorized";
			break;
		case 403:
			statusCodeMessage = "Forbidden";
			break;
		case 405:
			statusCodeMessage = "Not Allowed";
			break;
		case 429:
			statusCodeMessage = "Too Many Requests";
			break;
		case 500:
			statusCodeMessage = "Internal Server Error";
			break;
		case 501:
			statusCodeMessage = "Not Implemented";
			break;
		case 503:
			statusCodeMessage = "Service Unavailable";
			break;
		case 505:
			statusCodeMessage = "HTTP ver. not supported";
			break;
		case 508:
			statusCodeMessage = "Loop Detected";
			break;
		default:
			break;
		}
		
		String message =null;
		if(throwable!=null)
			message = throwable.getMessage(); 
		if(message == null)
			message = "See logs for ERROR ID " + errId;
		if(nullValueInSession)
			message += " Null value in session.";

		

		request.setAttribute("user", user);
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/errorHandler.jsp"));

	}

}
