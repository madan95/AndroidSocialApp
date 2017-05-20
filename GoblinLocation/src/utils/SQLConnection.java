package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

	private static SQLConnection con = null;
	
	private SQLConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/simple", "root", "");
		return conn;
	}
	
	public static SQLConnection getInstance(){
		if(con == null){
			con = new SQLConnection();
		}
		return con;
	}
}
