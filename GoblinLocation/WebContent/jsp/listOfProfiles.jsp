<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<%@ include file="header.jsp" %>

<style>


div.polaroid {
  width: 80%;
  background-color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  margin-bottom: 25px;
}
div.container {
  text-align: center;
  padding: 10px 20px;
}
</style>
 <center>
     
     <% ArrayList<User> userClosebyList = (ArrayList<User>)request.getAttribute("userClosebyList"); 
     for(User user: userClosebyList){
    	/* out.println("<div style='float:left'>");
 	     out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getId()+"/picture?type=square' style='border-radius:80%'>");
 	     out.println("</54div>");
    	 out.println("<p>ID         : "+ user.getId() +"</p>");
    	 out.println("<p>Name       : "+ user.getName() +"</p>");
 	     out.println("<p>Time Met   : "+ user.getLive_time() +"</p>");
 	     out.println("<p>Latitude   : "+ user.getLat()+ "</p>");
 	     out.println("<p>Longitude  : "+user.getLon() + "</p>");
 	     out.println("<a href=\"view?action=view&userId="+user.getId()+"\">View Profile</a>");
      out.println("<p>--------------------------------------------------------------</p><br>");
 */		out.println("<div class='polaroid'>");
 out.println("<a href=\"view?action=view&userId="+user.getId()+"\">");
    	 out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getId()+"/picture?type=large' style='width:100%'>"); 	 
    	 out.println("</a>");
    	 out.println("<div class='container'");
   // 	 out.println("<p>Time Met   : "+ user.getLive_time() +"</p>");
    	 out.println("<p>Date   : "+ user.getTimes()[0]+"-" +user.getTimes()[1]+"-"+user.getTimes()[2] +"</p>");
    	 out.println("<p>Time   : "+ user.getTimes()[3]+":" +user.getTimes()[4] +"</p>");
    	
       	
    	 out.println("<p>Latitude   : "+user.getLat()+ "</p>");
 	     out.println("<p>Longitude  : "+user.getLon() + "</p>");
 	    out.println("<a style='color: red; text-align: center;' href=\"map?lat="+user.getLat()+"&lon="+user.getLon()+"\">View Location</a>");
 	     out.println("</div>");
    	    	 out.println("</div>");
 
 }
     
     %>
    
      </div>
       </center> 
      </body>

<script type="text/javascript">
function start(){
	
}
function logout(){
	JSInterface.logout();
	locaiton.href="/GoblinLocation/logout";
	
}
</script>
</html>