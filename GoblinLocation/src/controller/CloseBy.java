package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
import utils.SQLConnection;
/**
 * Servlet implementation class CloseBy
 */
@WebServlet("/CloseBy")
public class CloseBy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<User> userCloseBy = new ArrayList<>();
	private Connection connection = null;
	private PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseBy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String lat = null, log=null;
		try {
			getFromCrossed(id);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.setContentType("text/html");
		//out = response.getWriter();
		if(!userCloseBy.isEmpty()){
			request.setAttribute("userClosebyList", userCloseBy);
			//request.getRequestDispatcher("/jsp/closeby.jsp").forward(request, response);
		//respondPage();
			request.getRequestDispatcher("/jsp/listOfProfiles.jsp").forward(request, response);
			
		}else{
			//if no user CloseBy
			request.setAttribute("nothing", "closeby");
			request.getRequestDispatcher("/jsp/nothing.jsp").forward(request, response);
			//out.println("******************* No One Close By M8 *****************");
			//;out.close();
			}
		userCloseBy.clear();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void getFromCrossed(String id) throws SQLException{
		PreparedStatement psst = null;
		ResultSet res  = null;
		String getCrossedIfExist = "SELECT * FROM crossed WHERE user_one_id=? OR user_two_id=? ";
		connection = SQLConnection.getInstance().getConnection();
		psst = connection.prepareStatement(getCrossedIfExist);
		psst.setString(1, id);
		psst.setString(2, id);
		res = psst.executeQuery();
		while(res.next()){
			String idOfOtherUser = "";
			if(id.equalsIgnoreCase(res.getString("user_one_id"))){
				idOfOtherUser = res.getString("user_two_id");
			}else{
				idOfOtherUser = res.getString("user_one_id");
			}
			UserAccountDataBase accountDetail = new UserAccountDataBase();
			accountDetail.getUserAccount(idOfOtherUser);
			userCloseBy.add(new User(idOfOtherUser,res.getString("lat"),res.getString("log"),res.getString("time"), accountDetail.getName()));
			System.out.println(idOfOtherUser);
		}
		res.close();
		psst.close();
	}
	
	public void respondPage(){
		out.println("<%@ include file='header.jsp' %>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<H1>People You Met</H1>");
	    for(int i=0; i<=userCloseBy.size()-1; i++){
	    	 out.println("<p>ID    : "+ userCloseBy.get(i).getId() +"</p>");
	    	 out.println("<p>Name    : "+ userCloseBy.get(i).getName() +"</p>");
	 	     out.println("<p>Time Met  : "+ userCloseBy.get(i).getLive_time() +"</p>");
	 	     out.println("<p>Location Met at  : Lat = "+ userCloseBy.get(i).getLat()+", Longitude = " + userCloseBy.get(i).getLon() +  "</p>");
	 	     out.println("<a href=\"view?action=view&userId="+userCloseBy.get(i).getId()+"\">View Profile</a>");
	 	     out.println("<p>--------------------------------------------------------------</p><br>");
	    }
	    out.println("</body></html>");
	    out.close();
	   
	    
	}
	
}
