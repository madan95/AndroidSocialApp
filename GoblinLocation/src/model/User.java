package model;

import java.text.ParseException;

import utils.DateConverter;

public class User {

	private long id;
	private String lat, lon;
	private String live_time;
	private String userName;
	private String email;
	private String gender;
	private int status;
	private int[] times;

	public User(long id, String lat, String lon, String live_time){
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.live_time = live_time;
	}
	
	public User(String userName, long id,  String email, String gender, int status){
		this.userName = userName;
		this.email = email;
		this.gender = gender;
		this.id = id;
		this.status = status;
	}
	
	public User(String id, String lat, String lon, String live_time, String name){
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
		this.live_time = live_time;
		DateConverter conv = new DateConverter();
		try {
		times =	conv.tweakTime(conv.parseDate(live_time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.userName = name;
	}
	
	public User(String name, long longValue, String gender2) {
		this.userName = name;
		this.id = longValue;
		this.gender = gender2;
		// TODO Auto-generated constructor stub
	}

	public int[] getTimes() {
		return times;
	}

	public void setYear(int[] times) {
		this.times = times;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName(){
		return userName;
	}
	
	public void setName(String userName){
		this.userName = userName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLive_time() {
		return live_time;
	}

	public void setLive_time(String live_time) {
		this.live_time = live_time;
	}
	
	
}
