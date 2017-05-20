<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="oauth.ConnectFB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script> 
$(document).ready(function(){
	var div = $("div");  
    div.animate({left: '100px'}, "slow");
    div.animate({fontSize: '1em'}, "slow");
});
</script>
<style>
body{
letter-spacing: 3px;
}
h3{
	color: red;
}
a{
	color: blue;
}
.button {
    background-color: #008CBA;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 1em;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
<title>Social Network Application</title>
</head>
 
<body style="background:lightblue; letter-spacing: 3px;">
<center>
    <%
        ConnectFB fbConnection = new ConnectFB();
    %>
   <div>
    <h3>Social Network Application</h3>
    <!--<a href="<%=fbConnection.getFBLoginDialogUrl()%>">Login Using Facebook</a>
    -->  
    <button class="button" style="letter-spacing: 3px;" onclick="loginFunction(); return flase;">Login Using Facebook</button>
    </div>
    </center>
</body>

<script type="text/javascript">
function start(){
	
}
function loginFunction(){
	window.location="<%=fbConnection.getFBLoginDialogUrl()%>";
}
</script>
</html>
