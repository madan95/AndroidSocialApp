<%@ page import="java.util.ArrayList"%>
<%@ page import="model.MessageFunction" %>
<%@ include file="header.jsp" %>
<style>
.list{
width: 320px;
    padding: 10px;
    border: 5px solid orange;
    margin: 0;
    
    }
p.name{
color:green;
}
</style>
      <center>
     <% ArrayList<MessageFunction> conversationList = (ArrayList<MessageFunction>)request.getAttribute("messageTo"); 
     for(MessageFunction user: conversationList){
    	 out.println("<div class='list'>");
    	// out.println("<a href=https://www.facebook.com/app_scoped_user_id/"+user.getRecevierId()+">Link to Facebook Profile</a>");
    	
    	 out.println("<p>Recevier ID    : "+ user.getRecevierId() +"</p>");
    	 out.println("<p class='name' >Recevier Name  : "+ user.getRecevierName() +"</p>");
    	 //out.println("<a href=\"message?action=send&userId="+list.get(i).intValue()+"\">Check Messages</a>");
 	    out.println("<form action=\"message?action=send&userId="+user.getRecevierId()+"\" method=\"post\">"
 	     	+ "<input type=\"text\" name=\"message\" placeholder='Message'><br><br>"
 	     		+ "<input type=\"submit\" value=\"send\"></form><br>");
    	 // out.println("<p>Name  : "+ list.get(i).getName() +"</p><br>");
 	//  out.println("<a href=\"relationship?action=acceptRequest&userId="+list.get(i).intValue()+"\">Accecpt Request</a>");
 		 out.println("</div>"); }
     
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