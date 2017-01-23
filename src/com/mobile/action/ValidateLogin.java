package com.mobile.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mobile.core.SecurityHelper;
import com.mobile.core.SecurityHelper.CannotPerformOperationException;
import com.mobile.jdbc.hibernate.HibernateUtil;
import com.mobile.model.User;

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
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		Query safeHQLQuery = session.createQuery("from User where username=:uname");
		safeHQLQuery.setParameter("uname", request.getParameter("userName"));
		String passwd = request.getParameter("password"); 
		String hash = null; 
		try {
			hash = SecurityHelper.createHash(passwd);
		} catch (CannotPerformOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(hash!=null){ 
			logger.debug("************** HASH GENERATED = " + hash + " *******************");
		}else{ 
			logger.error("Unable to process blank passwords.");
			throw new ServletException("UNABLE TO PROCESS BLANK PASSWORDS");
		}
		List result = safeHQLQuery.getResultList();
		if (result.size()<=0){ 
			//TODO - ERROR UI
		}
		if (result.size()==1){ 
			User user = (User) result.get(0); 
			String uname = user.getUsername();
			logger.debug("PASS -> " + uname);
		}
		if(result.size()>1){ 
			//TODO - ERROR UI
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
