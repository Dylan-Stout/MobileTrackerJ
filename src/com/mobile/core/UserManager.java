package com.mobile.core;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mobile.core.SecurityHelper.CannotPerformOperationException;
import com.mobile.core.SecurityHelper.InvalidHashException;
import com.mobile.jdbc.hibernate.HibernateUtil;
import com.mobile.model.User;
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
	 * Validate username password for authentication against USER_ACTIVITY table. If user is already logged in, logout and 
	 * remove user from UserSession. 
	 * 
	 * @param username
	 * @param password
	 * @return User - populated User 
	 * @throws CannotPerformOperationException 
	 */
	public static int authenticateLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws CannotPerformOperationException{ 
		
//		User user = new User(); 
//		user.setUserName(username);
		int loginCode = -1; //default error code
		
		Session session = HibernateUtil.getSession();
		Query safeHQLQuery = session.createQuery("from User where username=:uname");
		safeHQLQuery.setParameter("uname", username);
		
		List result = safeHQLQuery.getResultList();
		if (result.size()<=0){ 
			return -2; //NO SUCH USER
		}
		if (result.size()==1){ 
			User user = (User) result.get(0); 
			String uname = user.getUsername();
			if(username.equalsIgnoreCase(uname)){ 
				Boolean verified = false;
				try {
					verified = SecurityHelper.verifyPassword(password, user.getUserhash());
				} catch (InvalidHashException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				if(verified){ 
					return 1; 
				}else{ 
					return 0; //WRONG PASSWORD
				}
			}
		}
		if(result.size()>1){ 
			return -3; //TOO MANY RESULTS
		}
		
		return loginCode; 
			

//		String machineName = ""; 
//		
//	    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
//	    if (ipAddress == null) {  
//	         ipAddress = request.getRemoteAddr();  
//	    }
//	    if(ipAddress == null)
//	    	logger.error("Unable to gather remote IP from user authentication attempt " + username);
//	    
//	    user.setIpAddress(ipAddress);
//	    user.setUserName(username);
//
//		try {
//			logger.info("Attempting to login user: " + username);
//			if(username!="" && password!= ""){
//				// TODO - ADD LOGIN AUTH
//			}else{
//				loginCode=-1; 
//			}
//
//		} catch (Exception e) {
//			logger.error("*** Error validating login, Database problem exists. ****-");
//			e.printStackTrace();
//		} 
//
//		if(loginCode == 1){ 
//			user.setValidated(true); 
//		}else{ 
//			switch(loginCode){ 
//			case -1: // Wrong password | no user exiting 
//				user.setLoginError(UIConstants.LOGIN_INVALID);
//				break;
//			case -2:  // IP Address not allowed
//				user.setLoginError(UIConstants.LOGIN_IP_ERR); 
//				break;
//			//TODO - ADD USER LOGGED IN
////			case -3: // user already logged in
////				try {
////					String previousMachine = WrxjConnection.getPreviousMachineName(username); 
////					if(previousMachine!=null){ 
////						userServer.logOut(username, previousMachine);
////						UserSession.removeUserSession(user);
////						logger.info("User: " + username + " - already logged in, logged out from previous terminal and revalidating on this machine");
////						user = authenticateLogin(username,password); 
////					}else{ 
////						user.setLoginError("Login in use - Logout from terminal");
////					}					
////				} catch (DBException e) {
////					logger.error("Login already in use and could not logout previous session: " + e.getMessage());
////					user.setLoginError(UIConstants.LOGIN_IN_USE);
////					e.printStackTrace();
////				}
////				break; 
//			}
//		}
//
//		return user; 

	}
	

}
