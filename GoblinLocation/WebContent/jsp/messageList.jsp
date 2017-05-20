<%@ page import="java.util.ArrayList"%>
<%@ page import="static utils.Reversed.reversed"%>
<%@ page import="model.MessageData" %>
<%@ page import="utils.EscapeUtils" %>
<%@ include file="header.jsp" %>

<style>
.list{
width: 320px;
    padding: 10px;
    border: 5px solid orange;
    margin: 0;
    text-align: left;
    }
</style>
      <center>
       <% ArrayList<MessageData> messageList = (ArrayList<MessageData>)request.getAttribute("messageList"); 
 
     out.println("<form action=\"message?action=send&convId="+request.getAttribute("convId")+"&userId="+request.getAttribute("messageRe")+"\" method=\"post\">"
  	     	+ "<input type=\"text\" name=\"message\" placeholder='Message'><br><br>"
  	     		+ "<p><input type=\"submit\" value=\"send\" onsubmit=\"setTimeout(function () { window.location.reload(true); }, 10)\"></form><p><br>");
     for(MessageData message: reversed(messageList)){
    	 out.println("<div class='list'>");
    	 out.println("<p>Time: "+ message.getTimeSend()+"<p>");
    	 out.println("<p style='color:red;'>Sender: "+ message.getSenderName()+"<p>");
    	 out.println("<p style='color:green;'>Reciever : "+ message.getReceiverName()+"<p>");
    	// out.println("<p>Send By    : "+ list.get(i).longValue() +" To : "+recp.get(i).longValue()+" </p>");
    	
    	// for(int f=0; f<=message.size()-1; f++){
    	 out.println("<p style='color:blue;'>Mesage     : "+ EscapeUtils.escapeHtml(message.getMessage()) +"</p><br>");
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