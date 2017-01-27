package com.mobile.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.mobile.builder.JsBuilder;

public class MapData {
	
	
	final static Logger logger = Logger.getLogger(MapData.class);
	
	List<LocationData> gpsCoords = new ArrayList<LocationData>();
	String jsPoint = new String(); 
	String jsHeat = new String(); 
	String jsPath = new String(); 
	String jsPointHide = new String(); 
	String jsPointList = new String(); 
	String jsPointListActions = new String(); 
	String markers = new String(); 
	String windows = new String(); 
	String startLat = new String(); 
	String startLng = new String(); 
	String dateStamp = new String(); 
	
	public MapData(List<LocationData> locationData, String time){ 
		gpsCoords = locationData; 
		startLat = locationData.get(0).latitude; 
		if(startLat.equals("")||startLat == null)
			startLat = "40.760";
		startLng = locationData.get(0).longitude; 
		if(startLng.equals("")||startLng==null)
			startLng = "-111.891"; 
		Long minUnix = Long.valueOf(locationData.get(0).getTime()); 
		Long maxUnix = Long.valueOf(locationData.get(0).getTime());
		String startDate = locationData.get(0).getHrDate(); 
		String endDate =locationData.get(0).getHrDate(); 
		for(LocationData lData : locationData){ 
			if(lData.getTime()!=null){ 
				if(Long.valueOf(lData.getTime())<minUnix){ 
					startDate = lData.getHrDate(); 
					minUnix = Long.valueOf(lData.getTime()); 
				}
				if(Long.valueOf(lData.getTime())>maxUnix){ 
					endDate = lData.getHrDate(); 
					maxUnix = Long.valueOf(lData.getTime()); 
				}
			}
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
		df.setTimeZone(TimeZone.getTimeZone("America/Denver"));
		//TODO - Fix wrong date strings
		dateStamp = startDate + " - " + endDate; 
		logger.debug("Map data available for: " + dateStamp + " starting at " + startLat.toString() + "," + startLng.toString() + "\n Time range may be larger than available data"); 
		JsBuilder jsBuild = new JsBuilder(gpsCoords); 
		jsPoint = jsBuild.getJsPoint(); 
		jsPointHide = jsBuild.getJsPointHide();
		jsHeat = jsBuild.getJsHeat(); 
		jsPath = jsBuild.getJsPath(); 
		jsPointList = jsBuild.getJsPointList(); 
		jsPointListActions = jsBuild.getJsPointListActions();
		windows = jsBuild.getWindows(); 
		markers = jsBuild.getMarkers(); 
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

	public String getJsPointHide() {
		return jsPointHide;
	}

	public void setJsPointHide(String jsPointHide) {
		this.jsPointHide = jsPointHide;
	}

	public String getMarkers() {
		return markers;
	}

	public void setMarkers(String markers) {
		this.markers = markers;
	}

	public String getJsPointList() {
		return jsPointList;
	}

	public void setJsPointList(String jsPointList) {
		this.jsPointList = jsPointList;
	}

	public String getJsPointListActions() {
		return jsPointListActions;
	}

	public void setJsPointListActions(String jsPointListActions) {
		this.jsPointListActions = jsPointListActions;
	}

	public String getWindows() {
		return windows;
	}

	public void setWindows(String windows) {
		this.windows = windows;
	}
	
	
	
	
	
	
	

}
