package com.mobile.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public abstract class SecurityUser {
	public static Logger logger = Logger.getLogger(SecurityUser.class);
	
	public boolean doSecurityCheck(HttpServletRequest request, HttpServletResponse response){ 
		User user = (User)request.getAttribute("user"); 
		if(user==null){  //if user is not in request look in session
			HttpSession session = request.getSession(); 
			user = (User)session.getAttribute("user"); 
		}
		try {
			return(UserSession.isUserSessionActive(user, request.getSession()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("There was a problem with checking the user's active session: ");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false; 
	}
}
