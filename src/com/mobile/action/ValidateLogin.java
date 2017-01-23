package com.mobile.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mobile.core.SecurityHelper;
import com.mobile.core.SecurityHelper.CannotPerformOperationException;
import com.mobile.core.UserManager;
import com.mobile.core.UserSession;
import com.mobile.jdbc.hibernate.HibernateUtil;
import com.mobile.model.User;
import com.mobile.ui.UIConstants;

/**
 * Servlet implementation class ValidateLogin
 */
@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(ValidateLogin.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int loginCode = -100; 
		
		try {
			loginCode = UserManager.authenticateLogin(request.getParameter("userName"), request.getParameter("password"), request, response);
		} catch (CannotPerformOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		logger.debug("**************** LOGIN CODE = " + loginCode + " ****************");
		User anonUser = new User(); 
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/login.jsp");
		switch (loginCode) {
		case -3:
		case -2:
		case -1:
		case 0:
			anonUser.setUsererror(UIConstants.LOGIN_INVALID);
			break;
		case 1:
			anonUser.setUsername(request.getParameter("userName"));
		    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		    if (ipAddress == null) {  
		         ipAddress = request.getRemoteAddr();  
		    }
			anonUser.setUserip(ipAddress);
			anonUser.setUsererror(null);
			if(SecurityHelper.checkIP(ipAddress, request, response)){ 
				HttpSession session = request.getSession(); 
				UserSession.addUserSession(anonUser, session);
				rd = request.getServletContext().getRequestDispatcher("/gps/query.jsp"); 
			}else{ 
				
			}
			break;
		default:
			anonUser.setUsererror(UIConstants.LOGIN_INVALID);
			break;
		}
		request.setAttribute("user", anonUser);
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
