package com.mobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GPS")
public class Gps {
	
	@Id
	@Column(name="id")
	String id; 

	@Column(name="date")
	String date; 
	
	@Column(name="latitude")
	String latitude; 
	
	@Column(name="longitude")
	String longitude; 
	
	@Column(name="speed")
	String speed;

	public Gps(){
		
	}
	
	public Gps(String dt, String lat, String lon, String sp){ 
		this.date = dt; 
		this.latitude = lat; 
		this.longitude = lon; 
		this.speed = sp; 
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	} 
	
	

}
