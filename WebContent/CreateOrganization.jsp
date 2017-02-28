<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title>Create Organization</title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1>Create Organization</h1>
	
	<form action="CreateOrganizationService" method="post">
		<input type="hidden" name="org-master-user-id" value="<c:out value="${ userId }" />" />
		
		<input type="text" name="org-name" placeholder="Organization Name" />
		
		<h3>Organization Type</h3>
		<input type="radio" name="org-type" value="S" checked> Standard (1-10 users)<br />
		<input type="radio" name="org-type" value="P"> Premium (11-100 users)<br />
		<input type="radio" name="org-type" value="U"> Ultimate (100+ users) <br />
		
		<p style="color: #ccc; font-style: italic;">{ TODO : payment? }</p>
		<br />
		<input type="submit" value="Create Organization" />
	</form>
</body>
</html>