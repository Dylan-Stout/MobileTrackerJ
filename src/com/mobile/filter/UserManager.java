package com.mobile.filter;


import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mobile.core.User;
import com.mobile.core.UserSession;
import com.mobile.ui.UIConstants;

/**
 * @author dystout
 * Date: Aug 14, 2016
 *
 * Description: User login/logout manager. Writes login/logout events to USER_ACTIVITY table. 
 * Evalutates against USER table. 
 * 
 */
public class UserManager {
	

	public Logger logger = Logger.getLogger(this.getClass()); 
	
	/**
	 * Validate username password for authentication against wrxj EMPLOYEE table. If user is already logged in, logout and 
	 * remove user from UserSession. Uses wrxj StandardUserServer.validateLogin(user, password, machine name, IP Address) 
	 * for authentication.
	 * 
	 * @param username
	 * @param password
	 * @return User - populated User 
	 */
	public User authenticateLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){ 
		
		User user = new User(); 
		user.setUserName(username);
		int loginCode = -1; 

		
//TODO - ADD LOGIN AUTH PREP		
		
		

		String machineName = ""; 
		
	    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	    if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	    }
	    if(ipAddress == null)
	    	logger.error("Unable to gather remote IP from user authentication attempt " + username);
	    
	    user.setIpAddress(ipAddress);
	    user.setUserName(username);

		try {
			logger.info("Attempting to login user: " + username);
			if(username!="" && password!= ""){
				// TODO - ADD LOGIN AUTH
			}else{
				loginCode=-1; 
			}

		} catch (Exception e) {
			logger.error("*** Error validating login, Database problem exists. ****-");
			e.printStackTrace();
		} 

		if(loginCode == 1){ 
			user.setValidated(true); 
		}else{ 
			switch(loginCode){ 
			case -1: // Wrong password | no user exiting 
				user.setLoginError(UIConstants.LOGIN_INVALID);
				break;
			case -2:  // IP Address not allowed
				user.setLoginError(UIConstants.LOGIN_IP_ERR); 
				break;
			//TODO - ADD USER LOGGED IN
//			case -3: // user already logged in
//				try {
//					String previousMachine = WrxjConnection.getPreviousMachineName(username); 
//					if(previousMachine!=null){ 
//						userServer.logOut(username, previousMachine);
//						UserSession.removeUserSession(user);
//						logger.info("User: " + username + " - already logged in, logged out from previous terminal and revalidating on this machine");
//						user = authenticateLogin(username,password); 
//					}else{ 
//						user.setLoginError("Login in use - Logout from terminal");
//					}					
//				} catch (DBException e) {
//					logger.error("Login already in use and could not logout previous session: " + e.getMessage());
//					user.setLoginError(UIConstants.LOGIN_IN_USE);
//					e.printStackTrace();
//				}
//				break; 
			}
		}

		return user; 

	}
	
	/**
	 * Change record in wrxj LOGIN table to reflect logged off status using 
	 * StandardUserServer.logout(User Name, Machine Name) 
	 * 
	 * Remove user from UserSession and invalidate session. 
	 * 
	 * @param session
	 * @param user
	 */
	public void logoutUser(HttpSession session, User user){ 

//TODO - ADD LOGOUT AUTH PREP
		
		if(user==null)
			user=new User(); 
		
		logger.debug("Logging out user: " + user.getUserName() + "Session ID: " + session.getId());
		try {
//TODO - ADD LOGOUT 
//			userServer.logOut(user.getUserName(), user.getMachineName());
			logger.info("Successfully logged out user: " + user.getUserName() );
		} catch (Exception e) {
			logger.error("Error using Standard User Service to log user out. User: " + user.getUserName() 
						+ " Session ID: " + session.getId() );
			logger.error(e.getMessage());
		}
		UserSession.removeUserSession(user);
		session.removeAttribute("user");
		session.invalidate();
		
	}

}
