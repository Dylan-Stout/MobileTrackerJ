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
		session.close();
		return loginCode; 


	}
	

}
