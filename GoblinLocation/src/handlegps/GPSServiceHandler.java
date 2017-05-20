package handlegps;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SQLConnection;

/**
 * Servlet implementation class GPSServiceHandler
 */
@WebServlet("/GPSServiceHandler")
public class GPSServiceHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
    private PreparedStatement ptmt = null, stt=null, stt2=null ;
    private ResultSet res=null, res2= null;
    private int id=0;
    private String lat="", lon="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPSServiceHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String id = request.getParameter("id");
	   String token = request.getParameter("token");
		System.out.println(id + token);
		lat = request.getParameter("lat");
		lon = request.getParameter("lon");
		try{
			String query="SELECT * FROM account WHERE id=? and token=?";
			connection = SQLConnection.getInstance().getConnection();
			ptmt = connection.prepareStatement(query);
			ptmt.setString(1, id);
			ptmt.setString(2, token);
			res = ptmt.executeQuery();
			if(res.next()){
				System.out.println("inside res.next()");
				System.out.println("From mysql  " + res.getString("token"));
				if(lon!=null){
		    String queryCheck="SELECT * FROM livedata where user_id=?";
		    stt2 = connection.prepareStatement(queryCheck);
		    stt2.setString(1, id);
		    res2 = stt2.executeQuery();
		    if(res2.next()){
		    	System.out.println("Inside res2.next(");
		    	//if it is in live data
		    	 String queryUpdate="UPDATE livedata SET lat=?, log=?, time=now() WHERE user_id=?";
		         stt=connection.prepareStatement(queryUpdate);
		         stt.setString(1, lat);
		         stt.setString(2, lon);
		         stt.setString(3, id);
		         stt.executeUpdate();
		         stt.close();
		    }else{
		    	System.out.println("inside first else");
		    	//if not in live data
		    	String queryInsert="INSERT INTO livedata (user_id, lat, log) VALUES(?,?,?) ";
				stt = connection.prepareStatement(queryInsert);
				stt.setString(1, id);
				stt.setString(2, lat);
				stt.setString(3, lon);
				stt.executeUpdate();
				stt.close();
		    }
			}else{
				System.out.println("inside 2 else");
				//if no location
				}
			}else{
				System.out.println("inside 3 else");
				//if password or username wrong
			}
			System.out.println("OUTSIDE");
			
			res.close();
			res2.close();
			ptmt.close();
			stt2.close();
			connection.close();
			}catch(SQLException e){
			e.printStackTrace();
		}
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		System.out.println(id + " -- " + token + "  AND  "
				+lat + " --- " + lon + " Time : " + timeStamp);
	
		response.getWriter().append("Servered at Serive Test").append(request.getContextPath());
		//Calculating people around then put into database who u connected
		try {
			NearV2 n2 = new NearV2(id, lat, lon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
