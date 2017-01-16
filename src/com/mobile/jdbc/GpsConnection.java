package com.mobile.jdbc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

public class GpsConnection {
	
	public GpsConnection(){ 
		//none 
	}
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/gps?user=gps&password=101airborne123&ssl=true";


	public void insert(String[] postdata) {
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database " + DB_URL);
			conn = DriverManager.getConnection(DB_URL);

			//STEP 4: Execute a query
			System.out.println("Inserting coordinates");
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO GPS(id,latitude,longitude,speed) VALUES ('" + postdata[0] +"','"+postdata[2] +"','"+postdata[1] +"','"+postdata[3] +"')";
			System.out.println(sql);
			//stmt.executeQuery(sql);
			stmt.execute(sql);

			//STEP 6: Clean-up environment
			//rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Sucessfully deposited coordinates");
	}//end main
	
	public HttpServletResponse getAllCsv(HttpServletRequest request, HttpServletResponse response, String fileFullPath){ 
		Connection conn = null; 
		Statement stmt = null; 
		ResultSet res = null; 
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database");
			conn = DriverManager.getConnection(DB_URL);

			//STEP 4: Execute a query
			System.out.println("Gathering all coordinates");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM GPS";
			res = stmt.executeQuery(sql);
			List<LocationData> plots = new ArrayList<LocationData>();
			
			
			String reportName =  "GenerateCSV_Report_"
	                +System.currentTimeMillis()+".csv";     
			File file = new File(reportName); 
			file.createNewFile();
			FileWriter fw = new FileWriter(file); 
			
			try{
					while(res.next()){ 
						Object val = res.getObject("id");
						Long ident = ((Number)val).longValue();
						fw.write(Long.toString(ident) +","+res.getString("longitude")+","+res.getString("latitude")+","+res.getString("speed"));
						System.out.println(Long.toString(ident) +","+res.getString("longitude")+","+res.getString("latitude")+","+res.getString("speed"));
					}
				} catch (IOException e) {}
			
			stmt.close();
			conn.close();
			fw.close();
			response.setHeader("Content-Type", "text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getAbsoluteFile());
			return response; 
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		return response;
		
	}
}//end FirstExample
