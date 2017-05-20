package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.UserAccountDataBase;
import utils.SQLConnection;
/**
 * Servlet implementation class Profile1
 */
@WebServlet("/Profile1")
public class Profile1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;
    private Statement stt = null;
    private ResultSet res = null;
	private PrintWriter out;
	//int userID = 0;
	String name = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session;
		long first=0, second = 0;
		session = request.getSession();
		String temp =(String)session.getAttribute("id");
		long sessionId = Long.parseLong(temp);
		long userID =0;
		userID=Long.parseLong(request.getParameter("userId"));
		if(sessionId>userID){
			first=userID;
			second=sessionId;
			}else{
			first=sessionId;
			second=userID;
			}
		
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("view")){
			userID=Long.parseLong(request.getParameter("userId"));
			try {
				String query = "SELECT * FROM account WHERE id='"+userID+"';";
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.createStatement();
				res = stt.executeQuery(query);
				while(res.next()){
					name = res.getString("name");
					
				}
				res.close();
				stt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		int status = checkRstatus(first, second, sessionId, userID);
		UserAccountDataBase accountDetail = new UserAccountDataBase();
		accountDetail.getUserAccount(String.valueOf(userID));
		String email = accountDetail.getEmail();
		String name = accountDetail.getName();
		String gender = accountDetail.getGender();
		User userProfile = new User(name, userID, email, gender, status ); 
		
		request.setAttribute("userProfile", userProfile);
		request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
		//response.setContentType("text/html");
		//out = response.getWriter(); 
		//respondPage(first, second, sessionId, userID);
		//response.getWriter().append("Served at: fck know"+userID+"").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void respondPage(long first, long second, long sessionId, long userID){
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Hola</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<div style='float:left'>");
	    out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+userID+"/picture?type=square' style='border-radius:50%'>");
	    out.println("</div>");
	    out.println("<H1>Profile of "+name+"</H1>");
	    int status = checkRstatus(first, second, sessionId, userID);
	    if(status==0){//if pending request
	    	out.println("<H2> Awaiting Friend Request</H2>");
	    }else if(status==1){//accepted
	    	out.println(" <a href='relationship?action=friendList'>Message</a><br>");
	    	out.println("<H2> You two are already Friends</H2>");
	    }else if(status==2){
	    	out.println("<H2>Friend request was declined</H2>");
	    }else if(status==3){
	    out.println("<h2>User is blocked </h2>");
	    }else if(status==4){
        //if not in firend list
	    	out.println("<a href=\"relationship?action=friendRequest&userId="+userID+"\">Add Request</a>");
	    }
        out.println("</body></html>");
	    out.close();
	   
	    
	}
	public Integer checkRstatus(long first, long second, long sessionId, long userID2){
		String query = "SELECT * FROM relationship WHERE (user_one_id=? AND user_two_id=?)";
		int status=0; 
		try {
				connection = SQLConnection.getInstance().getConnection();
				PreparedStatement stt = connection.prepareStatement(query);
				stt.setLong(1, first);
				stt.setLong(2, second);
				//stt.setLong(3, sessionId);
			    res = stt.executeQuery();
			    if(res.next()){
			    	status = res.getInt("status");
			    	stt.close();
				    connection.close();	
			    	
			    }else{
			    	status =4;
			    	stt.close();
				    connection.close();	
			    
			    }
			    		 
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return status;
	}
	private boolean checkIfFriendList(long userID) {
		return false;
		// TODO Auto-generated method stub
		
	}

}
