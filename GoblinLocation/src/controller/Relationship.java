package controller;


import java.io.IOException;
import model.MessageFunction;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class Relationship
 */
@WebServlet("/Relationship")
public class Relationship extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;
    private PreparedStatement stt = null;
    private Statement stt2 = null;
    private ResultSet res = null, res3 = null;
	private PrintWriter out;
	long userID = 0;
	String name = null;
	 private List<Long> list = new ArrayList<Long>();
	 private ArrayList<User> userRequest = new ArrayList<>();
	 private ArrayList<MessageFunction> friendList = new ArrayList<>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Relationship() {
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
		
	
		
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("friendRequest")){
			try {
				userID=Long.parseLong(request.getParameter("userId"));
				if(sessionId>userID){
					first=userID;
					second=sessionId;
					}else{
					first=sessionId;
					second=userID;
					}
				if(checkFRdublicate(first, second, sessionId, userID)){
					//friend request already send	
				}else{
				String query = "INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) VALUES(?,?,?,?)";
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, first);
				stt.setLong(2, second);
				stt.setInt(3, 0);
				stt.setLong(4, sessionId);
				stt.executeUpdate();
				stt.close();
				connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//response.sendRedirect("view?action=view&userId="+userID);
			}
		if(action.equals("awaitingFriend")){
			String query = "SELECT * FROM relationship WHERE (user_one_id=? OR user_two_id=?) AND status=0 AND action_user_id!=?";
			 try {
					connection = SQLConnection.getInstance().getConnection();
					stt = connection.prepareStatement(query);
					stt.setLong(1, sessionId);
					stt.setLong(2, sessionId);
					stt.setLong(3, sessionId);
				    res = stt.executeQuery();
				    while(res.next()){
				    	if(res.getLong("user_one_id")==sessionId){
							list.add(res.getLong("user_two_id"));
						}else{
						list.add(res.getLong("user_one_id"));					
						}
				    }
				    stt.close();
				    connection.close();
				   // response.setContentType("text/html");
					//out = response.getWriter();
				    if(list.isEmpty()){
				    	request.setAttribute("nothing", "Awaiting request.");
						request.getRequestDispatcher("/jsp/nothing.jsp").forward(request, response);
				    }else{
					request.setAttribute("userRequest", getUserdetails(list));
					request.getRequestDispatcher("/jsp/awaitingrequest.jsp").forward(request, response);
				    }
					//respondPageFriendRequest();
					userRequest.clear();
					list.clear();
				    return;
				   
				 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(action.equalsIgnoreCase("acceptRequest")){
			 String query = "UPDATE relationship SET status = 1, action_user_id=? WHERE user_one_id=? AND user_two_id=?";
			 try {
				 userID=Long.parseLong(request.getParameter("userId"));
					if(sessionId>userID){
						first=userID;
						second=sessionId;
						}else{
						first=sessionId;
						second=userID;
						}
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, sessionId);
				stt.setLong(2, first);
				stt.setLong(3, second);
			    stt.executeUpdate();
			    stt.close();
			    connection.close();
			    
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
			 
		}
		if(action.equalsIgnoreCase("friendList")){
			 String query = "SELECT * FROM relationship WHERE (user_one_id=? OR user_two_id=?) AND status=1";
			 try {
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, sessionId);
				stt.setLong(2, sessionId);
			    res = stt.executeQuery();
			    while(res.next()){
			    	long reciverid = 0;
			    	if(res.getLong("user_one_id")==sessionId){
			    	reciverid = res.getLong("user_two_id");
			    			//list.add(res.getLong("user_two_id"));
					}else{
					reciverid=res.getLong("user_one_id");					
					}
			    	
			    	friendList.add(new MessageFunction(getNameFromID(reciverid), reciverid));
							
			    }
			    stt.close();
			    connection.close();
			    if(friendList.isEmpty()){
			    	request.setAttribute("nothing", "Friend.");
					request.getRequestDispatcher("/jsp/nothing.jsp").forward(request, response);
			    }else{
				request.setAttribute("messageTo", friendList);
				request.getRequestDispatcher("/jsp/friendList.jsp").forward(request, response);

friendList.clear();
			    }
			   // response.setContentType("text/html");
				//out = response.getWriter();
				//respondPage();
				//list.clear();
			    return;
			   
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
			 
		}
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);

	}
	public boolean checkFRdublicate(long first, long second, long sessionId, long userID2){
		boolean is=false;
		String query = "SELECT * FROM relationship WHERE (user_one_id=? AND user_two_id=?)";
		 try {
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, first);
				stt.setLong(2, second);
				//stt.setLong(3, sessionId);
			    res = stt.executeQuery();
			    if(res.next()){
			    	stt.close();
				    connection.close();	
			    	is= true;
			    }else{
			    	stt.close();
				    connection.close();	
			    	is= false;
			    }
			    		 
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return is;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public String getNameFromID(long list){
		String name="";
		try{
			connection = SQLConnection.getInstance().getConnection();
			String getIdList = "SELECT * FROM account WHERE id=?";
			PreparedStatement ptmt = connection.prepareStatement(getIdList);
			ptmt.setLong(1, list);
			res3 = ptmt.executeQuery();
			//if the account is already created
			if(res3.next()){
				name = res3.getString("name");
				//String updateToken = "UPDATE account SET token=?, name=?, email=?, gender=? WHERE id=?";
			}
			 ptmt.close();
			 
	}catch(Exception e){
		
	}
		return name;
	}
	public void respondPage(){
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Hola</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<H1>Friend List</H1>");
	    for(int i=0; i<=list.size()-1; i++){
	    	 String name = getNameFromID(list.get(i).longValue());
	    	 
	    	 out.println("<p>ID    : "+ list.get(i).longValue() +"</p>");
	    	 out.println("<p>Name  : "+ name +"</p>");
	    	 //out.println("<a href=\"message?action=send&userId="+list.get(i).intValue()+"\">Check Messages</a>");
	 	    out.println("<form action=\"message?action=send&userId="+list.get(i).longValue()+"\" method=\"post\">"
	 	     	+ "Send Message   : <input type=\"text\" name=\"message\"><br>"
	 	     		+ "<input type=\"submit\" value=\"send\"></form><br>");
	    	 // out.println("<p>Name  : "+ list.get(i).getName() +"</p><br>");
	 	//  out.println("<a href=\"relationship?action=acceptRequest&userId="+list.get(i).intValue()+"\">Accecpt Request</a>");
	 	     	out.println("------------------------------------------------------<br>");
	    }
	    out.println("</body></html>");
	    out.close();
	   
	    
	}
	
	public ArrayList<User> getUserdetails(List<Long> list2){
		for(int i=0; i<=list2.size()-1; i++){
			UserAccountDataBase accountDetail = new UserAccountDataBase();
			accountDetail.getUserAccount(String.valueOf(list2.get(i).longValue()));
			//String email = accountDetail.getEmail();
			String name = accountDetail.getName();
			String gender = accountDetail.getGender();
			userRequest.add(new User(name, list2.get(i).longValue(), gender)); 
		}
		return userRequest;
	}
	
	public void respondPageFriendRequest(){
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Hola</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<H1>Awaiting Friend Requests</H1>");
	    for(int i=0; i<=list.size()-1; i++){
	    	 out.println("<p>ID   : "+ list.get(i).longValue() +"</p>");
	    	 //out.println("<a href=\"message?action=send&userId="+list.get(i).intValue()+"\">Check Messages</a>");
	 	   // out.println("<form action=\"message?action=send&userId="+list.get(i).intValue()+"\" method=\"post\">"
	 	     	//+ "Send Message :<input type=\"text\" name=\"message\"><br>"
	 	     	//	+ "<input type=\"submit\" value=\"send\"></form><br>");
	    	 // out.println("<p>Name  : "+ list.get(i).getName() +"</p><br>");
	 	 out.println("<a href=\"relationship?action=acceptRequest&userId="+list.get(i).longValue()+"\">Accecpt Request</a>");
	 	     	
	    }
	    out.println("</body></html>");
	    out.close();
	   
	    
	}

}
