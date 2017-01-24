<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDNuIYqeD6yWtm6IzFxN7cIjkdcStxaBz4&libraries=visualization"></script>
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script> 
        <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script> 
        <title>${map.dateStamp}</title>
    </head>
    <body>
        <div data-role="page" id="map-page">            
            <div data-role="content" id="content">
            	    <div id="floating-panel">
      					<button onclick="toggleHeatmap()">Heatmap</button>
      					<button onclick="togglePath()">Paths</button>
      					<button onclick="togglePoints()">Points</button>
      					<button onclick="changeGradient()">List</button>
    				</div>
            	
                <div id="canvas-map" style="height:100%"/>
            </div>      
            <style>
                #content {
                    padding: 0 !important; 
                    position : absolute !important; 
                    top : 0 !important; 
                    right : 0 !important; 
                    bottom : 5px !important;  
                    left : 0 !important;     
                }       
            </style>            
            <script type="text/javascript">
            	var map, heatmap, path;
            	${map.markers}
                $(document).on('pageshow', '#map-page', function(e, data) {
                    var myLocation = { 
                            lat: 50, 
                            lon: -80 
                        }, 
                        mapOptions = { 
                            zoom: 12, 
                            mapTypeId: google.maps.MapTypeId.PLAN, 
                            center: new google.maps.LatLng(${map.startLat}, ${map.startLng}) 
                        }; 
                    $('#canvas-map').css("height", "100%").css("padding", "0px"); 
                    $('#floating-panel').css("position","absolute").css("top","50%").css("z-index","5").css("background-color","#fff").css("padding","5px").css("border","1px solid #999").css("text-align","center").css("font-family","'Roboto','sans-serif'").css("font-size","70%").css("line-height","30px").css("padding-left","10px")
                    map = new google.maps.Map(document.getElementById('canvas-map'), mapOptions); 
					${map.jsPoint}  
					${map.jsHeat}
					${map.jsPath}
                }); 
               function toggleHeatmap() {
                    heatmap.setMap(heatmap.getMap() ? null : map);
               }
               function togglePath(){ 
            	   path.setMap(path.getMap() ? null : map);
               }
               function togglePoints(){ 
            	   ${map.jsPointHide}
               }

            </script>           
        </div>
    </body>
</html>