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

import com.mobile.jdbc.GpsConnection;

/**
 * Servlet implementation class GpsStore
 */
@WebServlet("/Gps")
public class GpsStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GpsStore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		GpsConnection gps = new GpsConnection(); 
		String path = getServletContext().getRealPath("WEB-INF/../");
		File file = new File(path);
		String fullPath = file.getCanonicalPath();
		response = gps.getAllCsv(request, response, fullPath);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request,response);
		List<NameValuePair> list = (List<NameValuePair>) request.getAttribute("locations");
		Enumeration<String> enumParams = (Enumeration<String>) request.getParameterNames();
		 StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }
		  Object location_string = URLDecoder.decode(jb.toString());
		  System.out.println(jb.toString());
		System.out.println(enumParams);
		
		
	}
	
	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {

		PrintWriter out = res.getWriter();
		Date date = new Date(); 
		//PrintWriter outFile = new PrintWriter("locations_" + date.toString().replaceAll(" ","_") + ".txt");
		res.setContentType("text/plain");

		Enumeration<String> parameterNames = req.getParameterNames();
		ArrayList<String> locationData = new ArrayList<String>(); 

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = req.getParameterValues(paramName);
			locationData.add(paramValues[0]);
		}
		
		String[] singleEntry = new String[4];
		int numMessages = locationData.size()/4;
		for(int i=0; i<numMessages;i+=4){
			singleEntry[0]=locationData.get(i*4);
			singleEntry[1]=locationData.get((i*4)+1);
			singleEntry[2]=locationData.get((i*4)+2); 
			singleEntry[3]=locationData.get((i*4)+3); 
			GpsConnection gpsCon = new GpsConnection(); 
			gpsCon.insert(singleEntry);
			singleEntry = new String[4]; 
		}
		
		
		out.close();
		//outFile.close(); 

	}

}
