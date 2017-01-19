package com.mobile.jdbc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.mobile.builder.JsBuilder;

public class GpsMap {
	
	
	final static Logger logger = Logger.getLogger(GpsMap.class);
	
	List<LocationData> gpsCoords = new ArrayList<LocationData>();
	String jsPoint = new String(); 
	String jsHeat = new String(); 
	String jsPath = new String(); 
	String startLat = new String(); 
	String startLng = new String(); 
	String dateStamp = new String(); 
	
	public GpsMap(List<LocationData> locationData, String time){ 
		gpsCoords = locationData; 
		startLat = locationData.get(0).latitude; 
		if(startLat.equals("")||startLat == null)
			startLat = "40.760";
		startLng = locationData.get(0).longitude; 
		if(startLng.equals("")||startLng==null)
			startLng = "-111.891"; 
		Long minUnix = 0L; 
		Long maxUnix = 0L;
		for(LocationData lData : locationData){ 
			minUnix = (minUnix<lData.getTime()) ? minUnix: lData.getTime();
			maxUnix = (minUnix>lData.getTime()) ? maxUnix: lData.getTime();
		}
		Date startDate = new Date(minUnix);
		Date endDate = new Date(maxUnix); 
		//TODO - Fix wrong date strings
		dateStamp = startDate.toString() + " - " + endDate.toString(); 
		logger.debug("Map data generated for: " + dateStamp + " starting at " + startLat.toString() + "," + startLng.toString()); 
		JsBuilder jsBuild = new JsBuilder(gpsCoords); 
		jsPoint = jsBuild.getJsPoint(); 
		jsHeat = jsBuild.getJsHeat(); 
		jsPath = jsBuild.getJsPath(); 
		logger.debug("Generated js:" + jsPoint);
				
	}

	public List<LocationData> getGpsCoords() {
		return gpsCoords;
	}

	public void setGpsCoords(List<LocationData> gpsCoords) {
		this.gpsCoords = gpsCoords;
	}

	public String getStartLat() {
		return startLat;
	}

	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}

	public String getStartLng() {
		return startLng;
	}

	public void setStartLng(String startLng) {
		this.startLng = startLng;
	}

	public String getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getJsPoint() {
		return jsPoint;
	}

	public void setJsPoint(String js) {
		this.jsPoint = js;
	}

	public String getJsHeat() {
		return jsHeat;
	}

	public void setJsHeat(String jsHeat) {
		this.jsHeat = jsHeat;
	}

	public String getJsPath() {
		return jsPath;
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}
	
	
	
	
	
	
	

}
