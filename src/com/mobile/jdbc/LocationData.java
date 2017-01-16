package com.mobile.jdbc;

import java.util.function.LongToIntFunction;

public class LocationData {
	
	public Integer time; 
	public String longitude; 
	public String latitude; 
	public String speed; 
	
	public LocationData(Integer ti, String longt, String lat, String sp){ 
		time = ti; 
		longitude = longt; 
		latitude = lat; 
		speed = sp; 
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
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
	
	

}
