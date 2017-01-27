package com.mobile.builder;
import java.util.List;

import org.apache.log4j.Logger;

import com.mobile.action.GpsStore;
import com.mobile.model.LocationData;

public class JsBuilder {
	
	List<LocationData> data = null; 
	final static Logger logger = Logger.getLogger(JsBuilder.class);
	public String jsPoint = ""; 
	public String jsPointHide = ""; 
	public String jsPointList = ""; 
	public String jsPointListActions = ""; 
	public String jsHeat = ""; 
	public String jsPath = ""; 
	public String markers = ""; 
	public String windows = ""; 
	
    
	
	public JsBuilder(){ 
		//
	}

	public JsBuilder(List list){ 
		data = list; 
		jsPoint = buildMapCoordinates(); 
		jsHeat = buildHeatMap(); 
		jsPath = buildPath(); 
		jsPointList = buildSlidingList(); 
		jsPointListActions = buildSlidingListJs(); 
	}
	
	public String buildMapCoordinates(){ 
		StringBuilder sb = new StringBuilder(); 
		StringBuilder points = new StringBuilder(); 
		StringBuilder marksList = new StringBuilder(); 
		StringBuilder infoList = new StringBuilder(); 
		marksList.append("var "); 
		infoList.append("var "); 
		for(int i =0; i<data.size(); i++){ 
			if(data.get(i) instanceof LocationData){ 
				marksList.append("marker" + i);
				infoList.append("infowindow" + i);
				LocationData dataEnt = (LocationData) data.get(i); 
				sb.append(" var loc" + i + " = {lat: " + dataEnt.latitude+ ", lng: " + dataEnt.longitude+"};");
				sb.append(" marker" + i + " =  new google.maps.Marker({ position: loc" + i + ", map: map });");
				sb.append(buildInfoWindow(dataEnt,i)); 
				points.append( "marker"+i+".setMap(marker"+i+".getMap() ? null : map); ");
				if((i+1)<data.size()){
					marksList.append(",");
					infoList.append(","); 
				}else{ 
					marksList.append(";"); 
					infoList.append(";"); 
				}
			}else{ 
				logger.error("Data is not valid location data.");
			}
		}
		markers = marksList.toString(); 
		windows = infoList.toString(); 
		jsPointHide=points.toString(); 
		return sb.toString(); 
	}
	
	//build a heatmap out of the data pulled from database using an array of latlng 
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
		sb.append("heatmap.set('radius', 17);");
		return sb.toString(); 
	}
	
	//build js for paths in between points using google api 
	public String buildPath(){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("path = new google.maps.Polyline({path: getPoints(), geodesic: true, strokeColor: '#FF0000', strokeOpacity: 1.0, strokeWeight: 2 });");
		return sb.toString(); 
	}
	
	//Build event listener and div window for displaying point information on map coordinate point
	public String buildInfoWindow(LocationData data, int i){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("var contentString"+i+"= ' <div><h1>"+ data.hrDate + "</h1><p>Latitude: "+ data.latitude + "</p><p>Longitude: "+ data.longitude + "</p><p>Speed: "+data.speed+"</p></div>';");
		sb.append("infowindow" + i + "= new google.maps.InfoWindow({content: contentString"+ i +"});"); 
		sb.append("marker"+i+".addListener('click',function(){infowindow"+i+".open(map,marker"+i+");});");
		return sb.toString(); 
	}
	
	
	// Build the sliding list that is in the collapsable panel on map page
	public String buildSlidingList(){ 
		StringBuilder sb = new StringBuilder(); 
		sb.append("<ul data-role=\"listview\" data-inset=\"true\">"); 
		for(int i=0; i<data.size(); i++){
			if(i==0){ // divide the first no matter what day
				sb.append("<li data-role=\"list-divider\">").append(data.get(0).getHrDate().substring(0,9)).append("</li>"); 
			}else{ // check the day and enter divider if needed
				if(!compareHrDate(data.get(i-1).getHrDate(),data.get(i).getHrDate())){  // check if same day
					sb.append("<li data-role=\"list-divider\">").append(data.get(i).getHrDate()).append("</li>"); 
				}
			}
			sb.append("<li><a href=\"javascript:panToMarker").append(i).append("();\"><h2>Marker #").append(i).append("</h2>");
			sb.append("<p> Lat: ").append(data.get(i).getLatitude()).append("</p>");
			sb.append("<p> Long: ").append(data.get(i).getLongitude()).append("</p>");
			sb.append("<p class=\"ui-li-aside\"><strong>").append(data.get(i).getHrDate().substring(10,19)).append("</strong></p></a></li>"); 
			
		}
		sb.append("</ul>"); 
		return sb.toString(); 
	}
	
	//build js functions for clicks generated in side bar list of points in order to zoom/focus on certain points and open their infowindows
	public String buildSlidingListJs(){ 
		StringBuilder sb = new StringBuilder(); 
		for(int i=0;i<data.size();i++){
			sb.append("function panToMarker").append(i).append("(){ map.panTo(marker").append(i).append(".getPosition());").append(" infowindow"+i+".open(map,marker"+i+");}");
		}
		return sb.toString();
	}
	
	//Check to see if day is the same by looking at first three letters of human readable date. 
	public boolean compareHrDate(String old, String nw){ 
		return old.substring(0,2).equals(nw.substring(0,2)); 
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
