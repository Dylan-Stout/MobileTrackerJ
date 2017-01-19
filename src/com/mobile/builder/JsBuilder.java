package com.mobile.builder;
import java.util.List;

import org.apache.log4j.Logger;

import com.mobile.action.GpsStore;
import com.mobile.jdbc.LocationData;

public class JsBuilder {
	
	List data = null; 
	final static Logger logger = Logger.getLogger(JsBuilder.class);
	public String js = ""; 
    
	
	public JsBuilder(){ 
		//
	}

	public JsBuilder(List list){ 
		data = list; 
		js = buildMapCoordinates(); 
	}
	
	public String buildMapCoordinates(){ 
		StringBuilder sb = new StringBuilder(); 
		for(int i =0; i<data.size(); i++){ 
			if(data.get(i) instanceof LocationData){ 
				LocationData dataEnt = (LocationData) data.get(i); 
				sb.append(" var loc" + i + " = {lat: " + dataEnt.latitude+ ", lng: " + dataEnt.longitude+"};");
				sb.append(" var marker" + i + " =  new google.maps.Marker({ position: loc" + i + ", map: map });");
				
			}else{ 
				logger.error("Data is not valid location data.");
			}
		}
		return sb.toString(); 
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}
	
	
	
	
}
