<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Conversation" %>
<%@ include file="header.jsp" %>
<style>
.list{
width: 320px;
    padding: 10px;
    border: 5px solid gray;
    margin: 0;
    }
</style>
      <center>
     <% ArrayList<Conversation> conversationList = (ArrayList<Conversation>)request.getAttribute("userConversation"); 
     for(Conversation user: conversationList){
    	 out.println("<div class='list'>");
 	     out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getOtherId()+"/picture?type=square' style='border-radius:80%'>");
 	    
    	 out.println("<p>ID         : "+ user.getOtherId() +"</p>");
    	 out.println("<p>Name       : "+ user.getSecondName() +"</p>");
 	     out.println("<a href=\"message?action=aConv&convId="+user.getConvId()+"\">Click to View Conversation History</a>");
 	     out.println("</div>");
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