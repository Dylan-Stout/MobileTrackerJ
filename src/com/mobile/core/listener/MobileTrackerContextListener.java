package com.mobile.core.listener;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mobile.jdbc.DbConnection;

public class MobileTrackerContextListener implements ServletContextListener{
	
	final static Logger logger = Logger.getLogger(MobileTrackerContextListener.class);


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("Logging with log4j settings started, see WEB-INF/classes/log4j.properties to modify.");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("mobile_tracker.properties"); 
		logger.debug("Loading properties file..");
		Properties properties = new Properties(); 
		if(input!=null){ 
			try {
				properties.load(input);
				arg0.getServletContext().setAttribute("prop", properties);
				Properties checkProp = (Properties) arg0.getServletContext().getAttribute("prop");
				Enumeration keys = checkProp.keys();
//				logger.debug("*** PROPERTIES*** ");
//				while (keys.hasMoreElements()) {
//				    String key = (String)keys.nextElement();
//				    String value = (String)checkProp.get(key);
//				    logger.debug(key + " == " + value);
//				}
			} catch (IOException e) {
				logger.error("Unable to read properties file");
				e.printStackTrace();
			}
		}else{ 
			logger.debug("Classloader input file for properties load is null, cannot load properties");
		}
		Properties dbProp = (Properties) arg0.getServletContext().getAttribute("prop");
		logger.debug("CHECKING DB CONNECTION");
		try {
			Class.forName(dbProp.getProperty("db_driver"));
			Connection conn = null;
			conn = DriverManager.getConnection(dbProp.getProperty("db_url"),dbProp.getProperty("db_user"), dbProp.getProperty("db_pass"));
			conn.close();
			logger.debug("Connection test success");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
