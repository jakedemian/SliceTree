<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title>SliceTree</title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1>SliceTree</h1>	
	<h3>Log In</h3>
	
	<form id="loginForm" method="post" action="./Login">
		<input type="text" name="email" placeholder="Email" /><br />
		<input type="password" name="password" placeholder="Password" /><br />
		<input type="submit" value="submit"/><br />
	</form>
</body>
</html>