<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<%@ include file="header.jsp" %>

<center>
<h2 style="letter-spacing: 3px;color:blue;text-shadow: 3px 2px red;">Location You Met</h1>

<div id="map" style="width:100%;height:400px;background:yellow"></div>
</center>
<script>
function myMap() {
var mapOptions = {

		center: new google.maps.LatLng(${lat}, ${lon}), zoom: 19,
    mapTypeId: google.maps.MapTypeId.HYBRID
}
var map = new google.maps.Map(document.getElementById("map"), mapOptions);
}
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA1KO2I6apkJSdxTXmG1YniZ6XryqrPlMU&callback=myMap"></script>
</body>
</html>
