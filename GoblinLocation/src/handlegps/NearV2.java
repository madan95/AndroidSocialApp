package handlegps;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import utils.*;
import model.User;
public class NearV2 {

	private ArrayList<User> user_closeby = new ArrayList<>();
	private Connection connection = null;
	private String logged_in_id;
	private String logged_in_lat = "", logged_in_lon ="";
	
	private final String get_info_from_all="SELECT * FROM livedata WHERE user_id!=?";
	
	public NearV2(String id, String lat, String lon) throws ParseException, SQLException{
		System.out.println("INSIDE NEARV2"); 
		logged_in_id = id;
		logged_in_lat = lat;
		logged_in_lon = lon;
		checkNearBy();
		System.out.println("After CheckNearBy");
		putIntoDataIfNearBy();
		System.out.println("After puIntoDataIfNearBy");
	}
	
	private void checkNearBy() throws ParseException{
		PreparedStatement psst= null;
		ResultSet res = null;
		Double distanceBetween = 0.0;
		try {
			connection = SQLConnection.getInstance().getConnection();
			psst = connection.prepareStatement(get_info_from_all);
			psst.setString(1, logged_in_id);
			res = psst.executeQuery();
			while(res.next()){
				distanceCalculator ds = new distanceCalculator(
										logged_in_lat, logged_in_lon,
										res.getString("lat"), 
										res.getString("log"), "K");
				distanceBetween = ds.distanceBetween();
				if(distanceBetween<0.1){
					TimeDifferenceCalculator td =   new TimeDifferenceCalculator(
													new SimpleDateFormat("yyyy-MM-dd HH:mm")
													.format(Calendar.getInstance().getTime()),
													res.getString("time"));
					if(td.is_within_time()){
					user_closeby.add(new User(	res.getLong("user_id"),
												res.getString("lat"), 
												res.getString("log"),
												res.getString("time")));
					}
				}
			}
			res.close();
			psst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void putIntoDataIfNearBy() throws ParseException, SQLException{
		HandleCrossed hc = new HandleCrossed();
		if(!user_closeby.isEmpty()){
			for(User u : user_closeby){
				System.out.println("before hc.change");
				hc.changeData(Long.parseLong(logged_in_id), u.getId(), u.getLat(), u.getLon(), u.getLive_time());
				System.out.println("After hc change");
			}
		}
	}
	
}
