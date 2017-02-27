<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title>Dashboard</title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1>Dashboard</h1>
	
	<c:choose>
		<c:when test="${ empty orgId or orgId == -1 }">
			You don't currently belong to an organization.  <br />  
			You can either <a href="#">create an organization</a>, 
			or request to <a href="#">join an organization</a>. 
		</c:when>
		<c:otherwise>
			you have an org! now load projects/tasks for this org
		</c:otherwise>
	</c:choose>
</body>
</html>