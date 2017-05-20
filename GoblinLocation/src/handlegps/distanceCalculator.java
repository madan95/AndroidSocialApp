package handlegps;

public class distanceCalculator {
	private Double lat, lon, lat2, lon2;
	private String unit;
	
	public distanceCalculator(String lat, String lon, String lat2, String lon2, String unit){
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
		this.lat2 = Double.parseDouble(lat2);
		this.lon2 = Double.parseDouble(lon2);
		this.unit = unit;
	}
	
	public double distanceBetween(){
		double theta = lon - lon2;
		  double dist = Math.sin(deg2rad(lat)) 
				  		* Math.sin(deg2rad(lat2))
				  		+ Math.cos(deg2rad(lat)) 
				  		* Math.cos(deg2rad(lat2)) 
				  		* Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == "K") {
		    dist = dist * 1.609344;
		  } else if (unit == "N") {
		  dist = dist * 0.8684;
		    }
		return dist;
	}
	
	private double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}

	private double rad2deg(double rad) {
		  return (rad * 180.0 / Math.PI);
		}
}
