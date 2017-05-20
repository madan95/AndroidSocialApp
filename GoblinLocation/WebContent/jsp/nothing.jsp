<%@ include file="header.jsp" %>
<center>
<H2 style="color:green;"> There were nothing in ${nothing} </H2>
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