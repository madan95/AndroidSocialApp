package controller;


import java.io.IOException;
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
import model.MessageData;
import model.Conversation;
import utils.SQLConnection;
/**
 * Servlet implementation class Message
 */
@WebServlet("/Message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;
    private PreparedStatement stt = null, stt2=null;
    private ResultSet res = null, res2 =null, res3 = null;
	private PrintWriter out;
	private List<Long> list = new ArrayList<Long>();
	private List<Long> recp = new ArrayList<Long>();
	private List<String> message = new ArrayList<String>();
	//private List<Integer> convid = new ArrayList<>();
	private List<Conversation> conversationList = new ArrayList<>(); 
	private List<MessageData> messageList = new ArrayList<>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
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
		long messageRe =0, message1=0, message2=0;
		
		String action= request.getParameter("action");
		if(action.equalsIgnoreCase("aConv")){
			String convId=request.getParameter("convId");
			String queryToGetMessage="SELECT * FROM message WHERE conv_Id=? ORDER BY conv_Id ASC ";
			try {
				connection = SQLConnection.getInstance().getConnection();
				stt2 = connection.prepareStatement(queryToGetMessage);
				stt2.setString(1, request.getParameter("convId"));
				res2 = stt2.executeQuery();
				while(res2.next()){
					String senderName = getNameFromID(res2.getLong("user_id"));
					String receverName = getNameFromID(res2.getLong("recever_id"));
					messageList.add(new MessageData(res2.getString("message"), 
							res2.getLong("user_id"),
							res2.getLong("recever_id"), senderName, receverName,res2.getString("time")));
					message1 = res2.getLong("user_id");
					message2 = res2.getLong("recever_id");
				}
				stt2.close();
				connection.close();
				if(messageList.isEmpty()){
			    	request.setAttribute("nothing", "Messages.");
					request.getRequestDispatcher("/jsp/nothing.jsp").forward(request, response);
			    }else{
			    	if(sessionId==message2){
			    		messageRe = message1;
			    	}else{
			    		messageRe = message2;
			    	}
			    	request.setAttribute("convId", convId);
			    	request.setAttribute("messageRe", messageRe);
				request.setAttribute("messageList", messageList);
				request.getRequestDispatcher("/jsp/messageList.jsp").forward(request, response);
			    }
				messageList.clear();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(action.equalsIgnoreCase("allConv")){
			try{
				String query="SELECT * FROM conversation WHERE user_one_id=? OR user_two_id=? ORDER BY conversation.convId ASC ";
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, sessionId);
				stt.setLong(2, sessionId);
				res = stt.executeQuery();
				while(res.next()){
					//convid.add(res.getInt("convId"));
					long secondId = 0;
					if(res.getLong("user_one_id")==sessionId){
						secondId = res.getLong("user_two_id");
					}else{
						secondId = res.getLong("user_one_id");
					}
					String secondName = getNameFromID(secondId);
					conversationList.add(new Conversation(res.getInt("convId"), secondId, secondName));
					
					//list.add(res.getInt("convId"));
					//String query2="SELECT * FROM message WHERE conv_Id=? ORDER BY conv_Id DESC";
					//stt2 = connection.prepareStatement(query2);
					//stt2.setLong(1, res.getLong("convId"));
					//res2 = stt2.executeQuery();
					//while(res2.next()){
					//	recp.add(res2.getLong("recever_id"));
					//	list.add(res2.getLong("user_id"));
					//	message.add(res2.getString("message"));
					//}
				}
				
				stt.close();
			    connection.close();
			    if(conversationList.isEmpty()){
			    	request.setAttribute("nothing", "Conversation.");
					request.getRequestDispatcher("/jsp/nothing.jsp").forward(request, response);
			    }else{
			    request.setAttribute("userConversation", conversationList);
				request.getRequestDispatcher("/jsp/conversationList.jsp").forward(request, response);
			    }
				conversationList.clear();
			 //   String senderName = getNameFromID(list.get(0).longValue());
			   // String receiverName = getNameFromID(recp.get(0).longValue());
			   // response.setContentType("text/html");
				//out = response.getWriter();
			//	respondPage(senderName, receiverName);
				//list.clear();
			//	recp.clear();
			//	message.clear();
			    return;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(action.equalsIgnoreCase("send")){
			long userID=Long.parseLong(request.getParameter("userId"));
			if(sessionId>userID){
				first=userID;
				second=sessionId;
				}else{
				first=sessionId;
				second=userID;
				}
			String text = request.getParameter("message");
			String query="SELECT convId FROM conversation WHERE user_one_id=? AND user_two_id=?";
			try {
				connection = SQLConnection.getInstance().getConnection();
				stt = connection.prepareStatement(query);
				stt.setLong(1, first);
				stt.setLong(2, second);
				res = stt.executeQuery();
				if(!res.isBeforeFirst()){
					String query3="INSERT INTO conversation (user_one_id, user_two_id) VALUES(?,?)";
				    stt2 = connection.prepareStatement(query3);
				    stt2.setLong(1, first);
				    stt2.setLong(2, second);
			        stt2.executeUpdate();
			        res2 = stt.executeQuery();
			        while(res2.next()){
			        	  String query2="INSERT INTO message (conv_Id, user_id, message, recever_id) VALUES(?,?,?,?)";
						    stt2 = connection.prepareStatement(query2);
							stt2.setLong(1, res2.getLong("convId"));
						    stt2.setLong(2, sessionId);
						    
						    stt2.setString(3, text);
						    stt2.setLong(4, userID);
						    stt2.executeUpdate();
			        }
			      
				}else{
					while(res.next()){
						String query2="INSERT INTO message (conv_Id, user_id, message, recever_id) VALUES(?,?,?,?)";
					    stt2 = connection.prepareStatement(query2);
						stt2.setLong(1, res.getInt("convId"));
					    stt2.setLong(2, sessionId);
					    stt2.setString(3, text);
					    stt2.setLong(4, userID);
					    stt2.executeUpdate();
					}
				}
				stt.close();
				stt2.close();
		     	connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!(request.getParameter("convId")==null)){	
				response.sendRedirect("message?action=aConv&convId="+request.getParameter("convId"));
			}
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
			while(res3.next()){
				name = res3.getString("name");
				//String updateToken = "UPDATE account SET token=?, name=?, email=?, gender=? WHERE id=?";
			}
			 ptmt.close();
			 
	}catch(Exception e){
		
	}
		return name;
	}
	
	public void respondPage(String senderName, String recieverName){
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Hola</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<H1>Message List</H1>");
	    for(int i=0; i<=list.size()-1; i++){
	    	 out.println("<p>Sender Name: "+ senderName);
	    	 out.println("<p>Reciever Name: "+ recieverName);
	    	// out.println("<p>Send By    : "+ list.get(i).longValue() +" To : "+recp.get(i).longValue()+" </p>");
	    	
	    	// for(int f=0; f<=message.size()-1; f++){
	    	 out.println("<p>Mesage     : "+ message.get(i).toString() +"</p><br>");
	    	 out.println("------------------------------------------------------------<br>");
	    	 //}
	    	 //out.println("<a href=\"message?action=send&userId="+list.get(i).intValue()+"\">Check Messages</a>");
	 	    // out.println("<p>Name  : "+ list.get(i).getName() +"</p><br>");
	 	  //  out.println("<a href=\"relationship?action=acceptRequest&userId="+list.get(i).intValue()+"\">Accecpt Request</a>");
	 	     	
	    }
	    out.println("</body></html>");
	    out.close();
	   
	    
	}

}
