package com.mobile.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;

import com.mobile.jdbc.DbConnection;
import com.mobile.jdbc.LocationData;

/**
 * Servlet implementation class GpsStore
 */
@WebServlet("/Gps")
public class GpsStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(GpsStore.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GpsStore() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * No use
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet on /Gps Servlet");
	}

	/**
	 * Add entry of gps coords to database. 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Enumeration<String> parameterNames = request.getParameterNames();
		ArrayList<String> locationData = new ArrayList<String>(); 

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			locationData.add(paramValues[0]);
		}
		
		String[] singleEntry = new String[5];
		int numMessages = locationData.size()/4;
		for(int i=0; i<numMessages;i+=4){
			singleEntry[0]=locationData.get(i*4);
			Long milTime = Long.parseLong(singleEntry[0],10); 
			Long unixTime = milTime/1000L;  // convert milliseconds to seconds
			singleEntry[0]=unixTime.toString(); 
			Date time = new Date(unixTime*1000L); 
			logger.debug( unixTime +" -> " + time.toString()); //debug the time being sent
			singleEntry[1]=time.toString(); 
			singleEntry[2]=locationData.get((i*4)+1);
			singleEntry[3]=locationData.get((i*4)+2); 
			singleEntry[4]=locationData.get((i*4)+3); 

			DbConnection gpsCon = new DbConnection(request, response); 
			gpsCon.insert(singleEntry);
			singleEntry = new String[5]; 
		}
		
		
	}
	

}
