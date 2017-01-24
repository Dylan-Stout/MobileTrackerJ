package com.mobile.builder;
import java.util.List;

import org.apache.log4j.Logger;

import com.mobile.action.GpsStore;
import com.mobile.jdbc.LocationData;

public class JsBuilder {
	
	List data = null; 
	final static Logger logger = Logger.getLogger(JsBuilder.class);
	public String jsPoint = ""; 
	public String jsHeat = ""; 
	public String jsPath = ""; 
    
	
	public JsBuilder(){ 
		//
	}

	public JsBuilder(List list){ 
		data = list; 
		jsPoint = buildMapCoordinates(); 
		jsHeat = buildHeatMap(); 
		jsPath = buildPath(); 
	}
	
	public String buildMapCoordinates(){ 
		StringBuilder sb = new StringBuilder(); 
		for(int i =0; i<data.size(); i++){ 
			if(data.get(i) instanceof LocationData){ 
				LocationData dataEnt = (LocationData) data.get(i); 
				sb.append(" var loc" + i + " = {lat: " + dataEnt.latitude+ ", lng: " + dataEnt.longitude+"};");
				sb.append(" var marker" + i + " =  new google.maps.Marker({ position: loc" + i + ", map: map });");
				sb.append(buildInfoWindow(dataEnt,i)); 
				
			}else{ 
				logger.error("Data is not valid location data.");
			}
		}
		return sb.toString(); 
	}
	
	public String buildHeatMap(){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("heatmap = new google.maps.visualization.HeatmapLayer({data: getPoints(),map: null });");
		sb.append("function getPoints(){ return [");
		for(int i=0; i<data.size(); i++){ 
			if(data.get(i) instanceof LocationData){ 
				LocationData dataEnt = (LocationData) data.get(i); 
				sb.append("new google.maps.LatLng(" + dataEnt.latitude + ","+dataEnt.longitude+")");
				if(i+1<data.size())
					sb.append(",");
			}else{ 
				logger.error("Data is not valid location data.");
			}
		}
		sb.append("];}");
		sb.append("heatmap.set('radius', 10);");
		return sb.toString(); 
	}
	
	public String buildPath(){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("path = new google.maps.Polyline({path: getPoints(), geodesic: true, strokeColor: '#FF0000', strokeOpacity: 1.0, strokeWeight: 2 });");
		return sb.toString(); 
	}
	
	public String buildInfoWindow(LocationData data, int i){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("var contentString"+i+"= ' <div><h1>"+ data.hrDate + "</h1><p>Latitude: "+ data.latitude + "</p><p>Longitude: "+ data.longitude + "</p><p>Speed: "+data.speed+"</p></div>';");
		sb.append("var infowindow" + i + "= new google.maps.InfoWindow({content: contentString"+ i +"});"); 
		sb.append("marker"+i+".addListener('click',function(){infowindow"+i+".open(map,marker"+i+");});");
		return sb.toString(); 
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getJsPoint() {
		return jsPoint;
	}

	public void setJsPoint(String jsPoint) {
		this.jsPoint = jsPoint;
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
