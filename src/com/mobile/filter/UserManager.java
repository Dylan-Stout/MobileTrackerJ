package com.mobile.filter;


import java.net.InetAddress;
import java.net.UnknownHostException;










import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author dystout
 * Date: Aug 14, 2016
 *
 * Description: User login/logout manager. Writes login/logout values to wrxj LOGIN table. 
 * Evalutates password against EMPLOYEE table. 
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
	public User authenticateLogin(String username, String password){ 
		
		User user = new User(); 
		user.setUserName(username);
		int loginCode = -1; 
		StandardUserServer userServer = new StandardUserServer(DBScanConstants.DB_NAME);
		DBInfo dbInfo = new DBInfo(); 
		try {
			dbInfo.init();
		} catch (DBException e) {
			logger.error("** Database metadata collection failed. ***");
			e.printStackTrace();
		}
		

		InetAddress ip = null; 
		String machineName = ""; 
		
		try {
			ip = InetAddress.getLocalHost();
			machineName = ip.getHostName(); 
		} catch (UnknownHostException e) {
			logger.error("*** Could not determine host IP Address during login validation ***");
			logger.error(e.getMessage());
		} 

		if(ip!=null){ 
			user.setIpAddress(ip.getHostAddress());
			user.setMachineName(machineName);
		}else{ 
			logger.error("*** Unable to retrieve IP Address ***");
		}


		try {
			logger.info("Attempting to login user: " + username);
			if(username!="" && password!= "")
				loginCode = userServer.validateLogin(username, password, machineName, user.getIpAddress());
			else
				loginCode=-1; 
		} catch (DBException e) {
			logger.error("*** Error validating login, Database problem exists. ****-");
			e.printStackTrace();
		} 

		if(loginCode == userServer.LOGIN_OKAY){ 
			user.setValidated(true); 
		}else{ 
			switch(loginCode){ 
			case -1: // Wrong password | no user exiting 
				user.setLoginError(UIConstants.LOGIN_INVALID);
				break;
			case -2:  // login expired
				user.setLoginError(UIConstants.LOGIN_EXPIRED); 
				break;
			case -3: // user already logged in
				try {
					String previousMachine = WrxjConnection.getPreviousMachineName(username); 
					if(previousMachine!=null){ 
						userServer.logOut(username, previousMachine);
						UserSession.removeUserSession(user);
						logger.info("User: " + username + " - already logged in, logged out from previous terminal and revalidating on this machine");
						user = authenticateLogin(username,password); 
					}else{ 
						user.setLoginError("Login in use - Logout from terminal");
					}					
				} catch (DBException e) {
					logger.error("Login already in use and could not logout previous session: " + e.getMessage());
					user.setLoginError(UIConstants.LOGIN_IN_USE);
					e.printStackTrace();
				}
				break; 
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

		StandardUserServer userServer = new StandardUserServer(DBScanConstants.DB_NAME);
		DBInfo dbInfo = new DBInfo(); 
		try {
			dbInfo.init();
		} catch (DBException e) {
			logger.error("** Database metadata collection failed. ***");
			e.printStackTrace();
		}
		
		if(user==null)
			user=new User(); 
		
		logger.debug("Logging out user: " + user.getUserName() + "Machine Name: " + user.getMachineName()
					+ "Session ID: " + session.getId());
		try {
			userServer.logOut(user.getUserName(), user.getMachineName());
			logger.info("Successfully logged out user: " + user.getUserName() + "Machine Name: " + user.getMachineName());
		} catch (DBException e) {
			logger.error("Error using Standard User Service to log user out. User: " + user.getUserName() 
						+ "Machine Name: " + user.getMachineName() + " Session ID: " + session.getId() );
			logger.error(e.getMessage());
		}
		UserSession.removeUserSession(user);
		session.removeAttribute("user");
		session.invalidate();
		
	}

}
