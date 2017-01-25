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

	public DbConnection(HttpServletRequest request, HttpServletResponse response){ 
		req = request; 
		res = response; 
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
	
}
