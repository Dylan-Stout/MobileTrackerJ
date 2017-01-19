package com.mobile.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mobile.builder.JsBuilder;
import com.mobile.jdbc.DbConnection;
import com.mobile.jdbc.GpsMap;
import com.mobile.jdbc.LocationData;

/**
 * Servlet implementation class MapGps
 */
@WebServlet("/MapGps")
public class MapGps extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = Logger.getLogger(MapGps.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapGps() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug("Generating map data from source connection.");
		DbConnection gps = new DbConnection(request,response); 
		List<LocationData> lData = gps.getAllList(request.getParameter("time"));
		GpsMap map = null; 
		if(lData.size()>0)
			map = new GpsMap(lData, request.getParameter("time")); 
		request.setAttribute("map", map);
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/map.jsp"); 
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
