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

import utils.SQLConnection;
/**
 * Servlet implementation class friend
 */
@WebServlet("/Friend")
public class Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
    private Statement stt = null;
    private ResultSet res = null;
    private HttpSession session;
    private long id =0;
    private PrintWriter out = null;
    private List<Long> list = new ArrayList<Long>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session=request.getSession();
		String temp= (String)session.getAttribute("id");
		id=Long.parseLong(temp);
		String query="SELECT * FROM relationship WHERE (user_one_id="+id+" OR user_two_id="+id+")"
			          +"AND status=0 AND action_user_id !="+id;
		try {
			connection = SQLConnection.getInstance().getConnection();
			stt = connection.createStatement();
			res = stt.executeQuery(query);
			while(res.next()){
				if(res.getLong("user_one_id")==id){
					list.add(res.getLong("user_two_id"));
				}else{
				list.add(res.getLong("user_one_id"));					
				}
			}
			res.close();
			stt.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html");
		out = response.getWriter();
		respondPage();
		list.clear();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void respondPage(){
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Hola</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=\"white\">");
	    out.println("<H1>People That Send Friend Request to You</H1>");
	    for(int i=0; i<=list.size()-1; i++){
	    	 out.println("<p>ID   : "+ list.get(i).longValue() +"</p>");
	 	    // out.println("<p>Name  : "+ list.get(i).getName() +"</p><br>");
	 	    out.println("<a href=\"relationship?action=acceptRequest&userId="+list.get(i).longValue()+"\">Accecpt Request</a>");
	 	     	
	    }
	    out.println("</body></html>");
	    out.close();
	   
	    
	}
	

}
