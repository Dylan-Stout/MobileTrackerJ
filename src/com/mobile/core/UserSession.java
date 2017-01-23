package com.mobile.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;


/**
 * @author dystout
 * Date: Jul 28, 2016
 *
 * Description: All user's sessions. Add/remove sessions and determine if given users are in the active pool. 
 * Implements custom algorithm for generating session hash. 
 */
public final class UserSession {
	
	public final static HashMap<String, String> activeSessions = new HashMap<String, String>(); 
	public final static UserManager userManager = new UserManager(); 
	protected static Logger logger = Logger.getLogger(UserSession.class); 
	
	/**
	 * Add user's session to the active pool 
	 * 
	 * @param user
	 * @param session
	 */
	public static void addUserSession(User user, HttpSession session){ 
		try {
			activeSessions.put(user.getUserName(), makeSHA1Hash(session.getId()));
			logger.debug("Added user session to active pool for: " + user.getUserName());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Remove user's session from the active pool 
	 * 
	 * @param user
	 */
	public static void removeUserSession(User user){ 
		activeSessions.remove(user.getUserName()); 
		logger.debug("Removed user session from active pool for: " + user.getUserName());
	}
	
	/**
	 * Determines if the user's session is in the active pool. Creates hash of user's session ID
	 * to compare against hash stored in the active pool. 
	 * 
	 * @param user
	 * @param session
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean isUserSessionActive(User user, HttpSession session)
			throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(user == null){ 
			return false; 
		}
		String compareHash = makeSHA1Hash(session.getId()); 
		String activeHash = activeSessions.get(user.getUserName()); 
		return activeHash.equals(compareHash); 
	}
	

	/**
	 * Creates hash for use in active pool. 
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
    private static String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
        {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        }    

}