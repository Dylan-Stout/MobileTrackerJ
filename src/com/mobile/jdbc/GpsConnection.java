package com.mobile.jdbc;
//STEP 1. Import required packages
import java.sql.*;

public class GpsConnection {
	
	public GpsConnection(){ 
		//none 
	}
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost:5432/LOCATION";

	//  Database credentials
	static final String USER = "gps";
	static final String PASS = "101airborne123";

	public void insert(String[] postdata) {
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			System.out.println("Inserting coordinates");
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO GPS(latitude,longitude,timerecord,speed) VALUES ('" + postdata[0] +"','"+postdata[1] +"','"+postdata[2] +"','"+postdata[3] +"')";
			ResultSet rs = stmt.executeQuery(sql);

			//STEP 6: Clean-up environment
			rs.close();
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
}//end FirstExample
