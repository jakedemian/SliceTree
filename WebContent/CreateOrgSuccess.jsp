<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="./EnvironmentSetup.jspf" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title><fmt:message key="CREATE_ORG_SUCCESS_TITLE" /></title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1>
		<fmt:message key="ORG_CREATED_SUCCESSFULLY">
			<fmt:param value="${ orgName }" />
		</fmt:message>
	</h1>
	
	<br />
	<c:set var="dashboardLink">
		<a href="Dashboard"><fmt:message key="ORG_CREATED_DASHBOARD_LINK" /></a>
	</c:set>
	<fmt:message key="ORG_CREATED_DASHBOARD_LINK_MSG" >
		<fmt:param value="${ dashboardLink }" />
	</fmt:message>	
</body>
</html>