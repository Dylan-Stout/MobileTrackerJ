package com.mobile.jdbc;

import java.util.function.LongToIntFunction;

public class LocationData {
	
	public String time; 
	public String hrDate; // human readable date
	public String longitude; 
	public String latitude; 
	public String speed; 
	
	
	public LocationData(String ti, String hr, String longt, String lat, String sp){ 
		time = ti; 
		hrDate = hr; 
		longitude = longt; 
		latitude = lat; 
		speed = sp; 
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getHrDate() {
		return hrDate;
	}

	public void setHrDate(String hrDate) {
		this.hrDate = hrDate;
	}
	
	

}
