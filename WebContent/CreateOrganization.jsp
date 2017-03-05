<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="./EnvironmentSetup.jspf" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title><fmt:message key="CREATE_ORGANIZATION_TITLE" /></title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1><fmt:message key="CREATE_ORGANIZATION_HEADER" /></h1>
	
	<form action="CreateOrganizationService" method="post">
		<input type="hidden" name="org-master-user-id" value="<c:out value="${ userId }" />" />
		
		<input type="text" name="org-name" placeholder="<fmt:message key="ORG_NAME_PLACEHOLDER" />" />
		
		<h3><fmt:message key="ORGANIZATION_TYPE" /></h3>
		<input type="radio" name="org-type" value="S" checked><fmt:message key="ORG_TYPE_STANDARD" /><br />
		<input type="radio" name="org-type" value="P"><fmt:message key="ORG_TYPE_PREMIUM" /><br />
		<input type="radio" name="org-type" value="U"><fmt:message key="ORG_TYPE_ULTIMATE" /><br />
		
		<p style="color: #ccc; font-style: italic;">{ TODO : payment? }</p>
		<br />
		<input type="submit" value="<fmt:message key="CREATE_ORGANIZATION_SUBMIT_TEXT" />" />
	</form>
</body>
</html>