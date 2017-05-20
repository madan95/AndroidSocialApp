<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<%@ include file="header.jsp" %>
      <center>
     <% ArrayList<User> userRequest = (ArrayList<User>)request.getAttribute("userRequest"); 
     for(User user: userRequest){
    	 out.println("<div style='float:left'>");
 	     out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getId()+"/picture?type=square' style='border-radius:50%'>");
 	     out.println("</div>");
    	 out.println("<p>ID         : "+ user.getId() +"</p>");
    	 out.println("<p>Name       : "+ user.getUserName() +"</p>");
 	     out.println("<p>Gender     : "+ user.getGender() +"</p>");
 	     out.println("<a href=\"relationship?action=acceptRequest&userId="+user.getId()+"\">Accecpt Request</a>");
	 	
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