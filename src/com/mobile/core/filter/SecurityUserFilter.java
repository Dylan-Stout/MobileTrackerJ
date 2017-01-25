package com.mobile.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobile.core.SecurityUser;
import com.mobile.model.User;
import com.mobile.ui.UIConstants;

public class SecurityUserFilter extends SecurityUser implements Filter{
	
	@Override
	public void destroy() {	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
		HttpServletResponse httpServletResponse = (HttpServletResponse) response; 
		
		
		
		if(doSecurityCheck(httpServletRequest, httpServletResponse)){ 
			try {
				checkSessionData(httpServletRequest, httpServletResponse);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			chain.doFilter(request, response);
			
		}else{ 
			User user = (User)httpServletRequest.getSession().getAttribute("user"); 
			if(user==null)
				user = new User(); 
			String username = user.getUsername(); 
			logger.error("There was an invalid navigation attempt from -- Session: " + httpServletRequest.getSession().getId() +
					"User: " + username );
			logger.error("Navigation Target: " + httpServletRequest.getContextPath());
			user.setUsererror(UIConstants.SESSION_INVALID);
			httpServletRequest.setAttribute("user", user);
			request.getServletContext().getRequestDispatcher("/login.jsp").forward(httpServletRequest, httpServletResponse);
		}
				
	}
	
	/**
	 * Check session data for nulls, if exist throw exceptions to be handled 
	 * @param request
	 * @param response
	 * @throws PickException
	 */
	public void checkSessionData(HttpServletRequest request, HttpServletResponse response) throws ServletException{ 
		HttpSession session = request.getSession(); 
		User user = (User)session.getAttribute("user"); 
		if(user==null){ 
			throw new ServletException("Session invalid, no user. Please login."); 
		}
		
	}



}
