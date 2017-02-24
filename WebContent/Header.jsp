<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<style>
#header{
	width: 100%; 
	height: 35px; 
	background-color: black; 
	color: white;
}

a{
color: white;
text-decoration: none;
margin: 10px;
float: right;
}

a:hover{
	text-decoration: underline;
}

#loginForm{
margin: 5px;
float: right;
}
</style>

<div id="header" style="">
	<c:choose>
		<c:when test="${ userLoggedOn }" >
			<a href="./Logout">Logout</a>
		</c:when>
		<c:otherwise>
			<form id="loginForm" method="post" action="./Login">
				<input type="text" name="email" placeholder="Email" />
				<input type="password" name="password" placeholder="Password" />
				<input type="submit" value="submit"/>
			</form>
		</c:otherwise>
	</c:choose>
</div>