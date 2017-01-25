package com.mobile.jdbc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mobile.jdbc.hibernate.HibernateUtil;
import com.mobile.model.Gps;

public class DbConnection {
	
	HttpServletRequest req = null; 
	HttpServletResponse res = null; 
	Properties prop = null; 
	
	public DbConnection(HttpServletRequest request, HttpServletResponse response){ 
		req = request; 
		res = response; 
		prop = (Properties)req.getServletContext().getAttribute("prop"); 
	}
	
	final static Logger logger = Logger.getLogger(DbConnection.class);
	
	public List<LocationData> getLocationData(){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("from Gps"); 
		Session session = HibernateUtil.getSession(); 
		Query query = session.createQuery(sb.toString());
		List res = query.getResultList(); 
		List<LocationData> plots = new ArrayList(); 
		return null; 
	}
	
	public List<LocationData> getLocationData(String time){ 
		List<LocationData> plots = new ArrayList(); 
		Session session = HibernateUtil.getSession(); 
		String[] timeParam = TimeUtil.getTimeParam(time);
		Query query = session.createQuery("from Gps where id between :fromunix and :tounix");
		query.setParameter("fromunix", timeParam[0]);
		query.setParameter("tounix", timeParam[1]); 
		List<Gps> res = query.getResultList(); 
		for(Gps gpsEntry : res){ 
			plots.add(new LocationData(gpsEntry.getId(),gpsEntry.getDate(),gpsEntry.getLongitude(),gpsEntry.getLatitude(),gpsEntry.getSpeed())); 
		}
		
		return plots; 
	}
	
//	public List<LocationData> getAllList(String time){ 
//		Connection conn = null; 
//		Statement stmt = null; 
//		ResultSet res = null; 
//		List<LocationData> plots = null; 
//		try{ 
//			//STEP 2: Register JDBC driver
//			Class.forName(prop.getProperty("db_driver"));
//
//			//STEP 3: Open a connection
//			logger.debug("Connecting to database");
//			conn = DriverManager.getConnection(prop.getProperty("db_url"),prop.getProperty("db_user"),prop.getProperty("db_pass"));
//
//			//STEP 4: Execute a query
//			logger.debug("Gathering coordinates");
//			stmt = conn.createStatement();
//			String sql;
//			sql = "SELECT * FROM GPS";
//			switch (time) {
//			case "today":
//				long from = ((System.currentTimeMillis()/1000) - (60*60*24)) ; 
//				long to = (System.currentTimeMillis()/1000); 
//				sql+=" WHERE id BETWEEN " + from + " AND " + to + ";";
//				break;
//			case "yesterday":	
//				sql+=" WHERE id BETWEEN " + ((System.currentTimeMillis()/1000) - (60*60*24*2)) + " AND " + ((System.currentTimeMillis()/1000) - (60*60*24)) + ";";
//				break;
//			case "seven":
//				sql+=" WHERE id BETWEEN " + ((System.currentTimeMillis()/1000) - (60*60*24*7)) + " AND " + (System.currentTimeMillis()/1000) + ";";
//				break;
//			default:
//				sql += ";";
//				break;
//			}
//			logger.debug(sql);
//			res = stmt.executeQuery(sql);
//			plots = new ArrayList<LocationData>(); 
//			
//			while(res.next()){
//				Object obj = res.getObject("id"); 
//				Long unixTime = ((Number)obj).longValue(); 
//				String hrTime = res.getString("date");
//				String latitude = res.getString("latitude"); 
//				String longitude = res.getString("longitude");
//				String speed = res.getString("speed"); 
//				plots.add(new LocationData(unixTime,hrTime,longitude,latitude,speed)); 
//				
//			}
//			
//		}catch(SQLException se){ 
//			se.printStackTrace();
//		}catch(Exception e){ 
//			e.printStackTrace();
//		}
//		return plots; 
//	}
	

	
//	public HttpServletResponse getAllCsv(HttpServletRequest request, HttpServletResponse response, String fileFullPath){ 
//		Connection conn = null; 
//		Statement stmt = null; 
//		ResultSet res = null; 
//		try{
//			//STEP 2: Register JDBC driver
//			Class.forName(prop.getProperty("db_driver"));
//
//			//STEP 3: Open a connection
//			System.out.println("Connecting to database");
//			conn = DriverManager.getConnection(prop.getProperty("db_url"),prop.getProperty("db_user"),prop.getProperty("db_pass"));
//
//			//STEP 4: Execute a query
//			System.out.println("Gathering all coordinates");
//			stmt = conn.createStatement();
//			String sql;
//			sql = "SELECT * FROM GPS";
//			res = stmt.executeQuery(sql);
//			List<LocationData> plots = new ArrayList<LocationData>();
//			
//			
//			String reportName =  "GenerateCSV_Report_"
//	                +System.currentTimeMillis()+".csv";     
//			File file = new File(reportName); 
//			file.createNewFile();
//			FileWriter fw = new FileWriter(file); 
//			
//			try{
//					while(res.next()){ 
//						Object val = res.getObject("id");
//						Long ident = ((Number)val).longValue();
//						fw.write(Long.toString(ident) +","+res.getString("longitude")+","+res.getString("latitude")+","+res.getString("speed"));
//						System.out.println(Long.toString(ident) +","+res.getString("longitude")+","+res.getString("latitude")+","+res.getString("speed"));
//					}
//				} catch (IOException e) {}
//			
//			stmt.close();
//			conn.close();
//			fw.close();
//			response.setHeader("Content-Type", "text/csv");
//			response.setHeader("Content-Disposition", "attachment; filename=" + file.getAbsoluteFile());
//			return response; 
//		}catch(SQLException se){
//			//Handle errors for JDBC
//			se.printStackTrace();
//		}catch(Exception e){
//			//Handle errors for Class.forName
//			e.printStackTrace();
//		}finally{
//			//finally block used to close resources
//			try{
//				if(stmt!=null)
//					stmt.close();
//			}catch(SQLException se2){
//			}// nothing we can do
//			try{
//				if(conn!=null)
//					conn.close();
//			}catch(SQLException se){
//				se.printStackTrace();
//			}//end finally try
//		}//end try
//		return response;
//		
//	}
}
