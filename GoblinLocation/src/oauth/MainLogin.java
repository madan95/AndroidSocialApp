package oauth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.SQLConnection;

/**
 * Servlet implementation class MainLogin
 */
@WebServlet("/MainLogin")
public class MainLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String code = "";
    private Connection connection = null;
    private ResultSet res = null;
    
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        code = request.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }
        
        ConnectFB fbConnection = new ConnectFB();
        String shortlivedaccessToken = fbConnection.getShortLivedAccessToken(code);
        //get long-lived access-token
        String longLivedAccessToken = fbConnection.getLongLivedAccessToken(shortlivedaccessToken);
        
        FBGraphAPI fbGraph = new FBGraphAPI(longLivedAccessToken);
        String graph = fbGraph.getFBGraph();   
        @SuppressWarnings("unchecked")
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        System.out.println(fbProfileData.get("link"));
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(-1);
		session.setAttribute("id", fbProfileData.get("id"));
		session.setAttribute("name", fbProfileData.get("name"));
		session.setAttribute("email", fbProfileData.get("email"));
		session.setAttribute("gender", fbProfileData.get("gender"));
		session.setAttribute("token", longLivedAccessToken);
		response.sendRedirect("/GoblinLocation/home");
        //Check if this account exist in My Database
        checkIfAccountExist(fbProfileData.get("id"), longLivedAccessToken, request, response, 
        		fbProfileData.get("name"), fbProfileData.get("email"), fbProfileData.get("gender"));
	}
	
	public void checkIfAccountExist(String id, String longLivedAccessToken, 
			HttpServletRequest request, HttpServletResponse response,
			String name, String email, String gender){	  
        //Use GraphAPI to get FB Data
       /* FBGraphAPI fbGraph = new FBGraphAPI(longLivedAccessToken);
        String graph = fbGraph.getFBGraph();   
        @SuppressWarnings("unchecked")
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);*/
	    
        
		try{
			connection = SQLConnection.getInstance().getConnection();
			String getIdList = "SELECT * FROM account WHERE id=?";
			PreparedStatement ptmt = connection.prepareStatement(getIdList);
			ptmt.setString(1, id);
			res = ptmt.executeQuery();
			//if the account is already created
			if(res.next()){
				/*sendBackHomePage(fbProfileData.get("id"),
						fbProfileData.get("name"),
						fbProfileData.get("email"),
						fbProfileData.get("gender"),
						request, response);		*/
				if(email!=null){
				String updateToken = "UPDATE account SET token=?, name=?, email=?, gender=? WHERE id=?";
				PreparedStatement stt = connection.prepareStatement(updateToken);
				stt.setString(1, longLivedAccessToken);
				stt.setString(2, name);
				stt.setString(3, email);
				stt.setString(4, gender);
				stt.setString(5, id);
				stt.executeUpdate();
				stt.close();
				}else{
					System.out.println("Email is Null");
				}
			}else{
				//if account doesn't exist , Create new account with that ID
				String cn="";
				if(email==null){
				 cn = "INSERT INTO account(id, token, name, email, gender) VALUES(?,?,?,'NoEmail',?)";
				 PreparedStatement stt = connection.prepareStatement(cn);
					stt.setString(1, id);
					stt.setString(2, longLivedAccessToken);
					stt.setString(3, name);
					
					stt.setString(4, gender);
					stt.executeUpdate();
					stt.close();
				}else{
			    cn = "INSERT INTO account(id, token, name, email, gender) VALUES(?,?,?,?,?)";
				PreparedStatement stt = connection.prepareStatement(cn);
				stt.setString(1, id);
				stt.setString(2, longLivedAccessToken);
				stt.setString(3, name);
				stt.setString(4, email);
				stt.setString(5, gender);
				stt.executeUpdate();
				stt.close();
				}
				/*sendBackHomePage(fbProfileData.get("id"),
						fbProfileData.get("name"),
						fbProfileData.get("email"),
						fbProfileData.get("gender"),
						request, response);	*/
			}
			res.close();
			ptmt.close();
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void sendBackHomePage(String id, String name, String email, String gender, HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		session.setAttribute("id", id);
		session.setAttribute("name", name);
		session.setAttribute("email", email);
		session.setAttribute("gender", gender);
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("gender", gender);
		try {
		   request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

}
