<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title>Success</title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1><c:out value="${ orgName }" /> created!</h1>
	<p>You have successfully created your organization: <c:out value="${ orgName }" /></p>
	
	<br />
	To get started creating projects and tasks, go to your <a href="Dashboard">Dashboard</a>.	
</body>
</html>