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
	
	<h3>Create Account</h3>
	
	<form action="SignUp" method="post">
		<input type="text" name="email" placeholder="Email" /> <br />
		<input type="password" name="password" placeholder="Password" /> <br />
		<input type="password" name="confirmPassword" placeholder="Confirm Password" /> <br />
		<input type="submit" value="Submit" /> <br />
	</form>
	<c:if test="${ !empty param['empty_input'] && param['empty_input'] == 1 }">
		<p>You forgot to type something ya dingus.</p>
	</c:if>
</body>
</html>