<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" 

rel="stylesheet">   
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" 

src="http://maxcdn.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<style>
body {
	letter-spacing: 3px;
}
</style>
<title></title>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" 

 style="border: 0; border-top: 1px solid #777777; 
 border-bottom: 1px solid #777777; background-color: #ffffff;">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
          </div>
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
              <li class="active" style="border-right:2px solid silver;">
              <a href="/GoblinLocation/home">Home</a></li>
              <li style="border-right:2px solid silver;">
              <a href="/GoblinLocation/closeby">Profile You Met</a></li>
              <li style="border-right:2px solid silver;">
              <a href="relationship?action=friendList">FriendList to Message</a></li>
               <li style="border-right:2px solid silver;">
              <a href="relationship?action=awaitingFriend">Awaiting Friend List</a></li>
              <li style="border-right:2px solid silver;">
              <a href="message?action=allConv">Message History</a></li>                                     
               <li style="border-right:2px solid silver;">
               <a id="myLink" title="Click to do something"
 href="/GoblinLocation/logout" onclick="logout();return false;">Logout</a>
             
            </ul>
                    </div>
        </div>
      </nav>
      <div class="container" style="margin-top:70px">