<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<%@ include file="header.jsp" %>
<style>
a.fblink{
color: blue;

}
h2{
color: green;
}
a.request{
color: orange;
}
</style>
      <center>
     <% User user = (User)request.getAttribute("userProfile"); 

    	 out.println("<div style='float:center'>");
 	     out.println("<img alt='Profile Pic' src='https://graph.facebook.com/"+user.getId()+"/picture?type=large' style='max-width: 100%; height: auto;'>");
 	     out.println("</div><br>");
    	 out.println("<p>ID         : "+ user.getId() +"</p>");
    	 out.println("<p>Name       : "+ user.getUserName() +"</p>");
    	 out.println("<p>Email       : "+ user.getEmail() +"</p>");
    	 out.println("<p>Gender       : "+ user.getGender() +"</p>");
    	   out.println("<a class='fblink' href=https://www.facebook.com/app_scoped_user_id/"+user.getId()+">Link to Facebook Profile</a><br>");
          	
 	 	     int status = user.getStatus();
 	    if(status==0){//if pending request
	    	out.println("<H2> Awaiting Friend Request</H2>");
 	     }else if(status==1){//accepted
	    //	out.println(" <a href='relationship?action=friendList'>Message</a><br>");
	    	out.println("<H2> You two are already Friends</H2>");
	      	
	    	
	    }else if(status==2){
	    	out.println("<H2>Friend request was declined</H2>");
	    }else if(status==3){
	    out.println("<h2>User is blocked </h2>");
	    }else if(status==4){
        //if not in firend list
	    	out.println("<a class='request' href=\"relationship?action=friendRequest&userId="+user.getId()+"\">Add Request</a>");
	  
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