<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<%@ include file="header.jsp" %>
      <center>
     <% ArrayList<User> userClosebyList = (ArrayList<User>)request.getAttribute("userClosebyList"); 
     for(User user: userClosebyList){
    	 out.println("<div style='float:left'>");
 	     out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getId()+"/picture?type=square' style='border-radius:80%'>");
 	     out.println("</div>");
 	  out.println("<p>ID         : "+ user.getId() +"</p>");
    	 out.println("<p>Name       : "+ user.getName() +"</p>");
 	     out.println("<p>Time Met   : "+ user.getLive_time() +"</p>");
 	     out.println("<p>Latitude   : "+ user.getLat()+ "</p>");
 	     out.println("<p>Longitude  : "+user.getLon() + "</p>");
 	     out.println("<a href=\"view?action=view&userId="+user.getId()+"\">View Profile</a>");
 	     out.println("<p>--------------------------------------------------------------</p><br>");
     }
     
     %>
      
      
      
      </body>
</center>
<script type="text/javascript">
function start(){
	
}
function logout(){
	JSInterface.logout();
	locaiton.href="/GoblinLocation/logout";
	
}
</script>
</html>