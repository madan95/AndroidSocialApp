package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.SQLConnection;
public class UserAccountDataBase {
	
	private String name, id, email, gender;
	private String searchId;
	private Connection connection = null;
	
	public UserAccountDataBase(){
		
	}
	
	public void getUserAccount(String searchId){
		String getData = "SELECT * FROM account WHERE id=?";
		ResultSet res = null;
		try {
			connection = SQLConnection.getInstance().getConnection();
			PreparedStatement psst = connection.prepareStatement(getData);
			psst.setString(1, searchId);
			res = psst.executeQuery();
			while(res.next()){
				name = res.getString("name");
				email = res.getString("email");
				gender = res.getString("gender");
			
				//id = res.getString("id");
				//pass = res.getString("pass");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setPass(String pass) {
		this.email = pass;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String pass) {
		this.gender = pass;
	}
	
	
	
}
