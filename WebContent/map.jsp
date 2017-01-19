<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Palantir GPS </title>
</head>
<body>
<div id="map"></div>
    <script>
      function initMap() {
        var startCoord = {lat: "${map.startLat}", lng: "${map.startLng}"}; //lat: -25.363, lng: 131.044
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: startCoord
        });

      }
	</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDNuIYqeD6yWtm6IzFxN7cIjkdcStxaBz4&callback=initMap" async defer></script>

</body>
</html>