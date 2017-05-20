package handlegps;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import utils.SQLConnection;
public class HandleCrossed {
	
	private long other_id, first, second;
	private int crossed_id;
	private long logged_in_id;
	private Connection connection = null;
	private String lat, lon, other_time;
	
	public void changeData(long logged_in_id, long other_id, String lat, String lon, String other_time) throws ParseException, SQLException{
	System.out.println("INSIDE HandleCrossed Change Data");
		this.logged_in_id = logged_in_id;
		this.other_id = other_id;
		this.lat = lat;
		this.lon = lon;
		this.other_time = other_time;
		setFirstAndSecond();
		simplyAddOrUpdateCrossed();
		//setFirstAndSecond();
		//check_if_exist_in_crossed();
	}
	
	public void simplyAddOrUpdateCrossed() throws SQLException{
		System.out.println("Inside ad or upate crossed");
		PreparedStatement psst = null;
		ResultSet res = null;
		String checkInDB = "SELECT * FROM crossed WHERE user_one_id=? and user_two_id=?";
		connection = SQLConnection.getInstance().getConnection();
		psst = connection.prepareStatement(checkInDB);
		psst.setLong(1, first); 
		psst.setLong(2, second);
		res = psst.executeQuery();
		if(res.next()){
			System.out.println("Crossed Updated 1.o");
			
			System.out.println("Crossed Updated");
			//if crossed already, just update locaiton and time
			String updateCrossed = "UPDATE crossed SET lat=?, log=? WHERE crossed_id=?";
			
			PreparedStatement stt = connection.prepareStatement(updateCrossed);
			stt.setString(1, lat);
			stt.setString(2, lon);
			stt.setInt(3, res.getInt("crossed_id"));
			stt.executeUpdate();
			stt.close();
			
			
		}else{
			System.out.println("Crossed Inserted");
			//if crossed first time, add new entry to crossed
			String putIntoCrossed = "INSERT INTO crossed (user_one_id, user_two_id, lat, log) VALUES(?,?,?,?)";
			PreparedStatement sst = connection.prepareStatement(putIntoCrossed);
			sst.setLong(1, first);
			sst.setLong(2, second);
			sst.setString(3, lat);
			sst.setString(4, lon);
			sst.executeUpdate();
			sst.close();
			
		}
	}
	
	public void check_if_exist_in_crossed() throws SQLException, ParseException{
		PreparedStatement psst = null;
		ResultSet res = null;
		String checkInDB = "SELECT * FROM crossed WHERE user_one_id=? and user_two_id=?";
		connection = SQLConnection.getInstance().getConnection();
		psst = connection.prepareStatement(checkInDB);
		psst.setLong(1, first); 
		psst.setLong(2, second);
		res = psst.executeQuery();
		if(res.next()){
			//it exist
			crossed_id = res.getInt("crossed_id");
		//	TimeDifferenceCalculator td = new TimeDifferenceCalculator(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()), other_time);
		//	if(!td.is_within_time()){
			putIntoLocation(crossed_id);
		//	}
		}else{
			//doesn't exist, hence create new crossed id and return it as parameter 
			putIntoLocation(createNewCrossed());
		}
		psst.close();
	}
	
	public void putIntoLocation(int crossed_id) throws SQLException{
		String putIntoLocation = "INSERT INTO location (crossed_id_fk, lat, log) VALUES(?,?,?)";
		PreparedStatement psst = null;
		psst = connection.prepareStatement(putIntoLocation);
		psst.setInt(1, crossed_id);
		psst.setString(2, lat);
		psst.setString(3, lon);
		psst.executeUpdate();
		psst.close();
	}
	
	public int createNewCrossed() throws SQLException{
		String newCrossed = "INSERT INTO crossed(user_one_id, user_two_id, lat, log) VALUES(?,?,?,?)";
		String getCrossedId = "SELECT crossed_id FROM crossed WHERE user_one_id=? and user_two_id=?";
		PreparedStatement psst = connection.prepareStatement(newCrossed);
		ResultSet result = null;
		psst.setLong(1, first);
		psst.setLong(2, second);
		psst.setString(3, lat);
		psst.setString(4, lon);
		psst.executeUpdate();
		psst.close();
		
		//Geting New Crossed Id
		psst = connection.prepareStatement(getCrossedId);
		psst.setLong(1, first);
		psst.setLong(2, second);
		result = psst.executeQuery();
		if(result.next()){
			crossed_id = result.getInt("crossed_id");
		}
		psst.close();
		result.close();
		return crossed_id;
	}
	
	public void setFirstAndSecond(){
		if(logged_in_id<other_id){
			first=logged_in_id;
			second=other_id;
		}else if(other_id<logged_in_id){
			first=other_id;
			second=logged_in_id;
		}
	}
}
