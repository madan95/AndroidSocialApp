<%@ include file="header.jsp" %>
      <center>
<h2 style="letter-spacing: 3px;color:blue;text-shadow: 3px 2px red;">My Profile </h2> 
<div >
<img alt='Profile Pic' src="https://graph.facebook.com/${id}/picture?type=large" style='border-radius:50%; border: 1px solid #ddd;padding: 5px;
    width: 250px;'>
</div>

<div id="detail" style="letter-spacing: 3px;" >
ID       : ${id} <br>
Name	 : ${name}<br>
Email	 : ${email}<br>
Gender	 : ${gender}<br>
</div>
<form name=gp >
<input type="hidden" id="id" value='${id}'>
<input type="hidden" id="token" value='${token}'>
</form>
</body>
</center>
<script type="text/javascript">
function start(){
	JSInterface.setData(document.gp.id.value, document.gp.token.value);
}
function logout(){
	JSInterface.logout();
	locaiton.href="/GoblinLocation/logout";
	
}
</script>
</html>