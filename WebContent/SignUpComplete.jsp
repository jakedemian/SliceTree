<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="./EnvironmentSetup.jspf" %>

<c:set var="NO_ORG_MSG_BLOCK">
	<fmt:message key="SUC_NO_ORG_MSG" />  <br />
	<c:set var="createOrgLink">
		<a href="CreateOrganization"><fmt:message key="SUC_LINK_CREATE_ORG" /></a>
	</c:set>
	<c:set var="joinOrgLink">
		<a href="#"><fmt:message key="SUC_LINK_JOIN_ORG" /></a>
	</c:set>
	<fmt:message key="SUC_NO_ORG_OPTIONS_MSG" >
		<fmt:param value="${ createOrgLink }" />
		<fmt:param value="${ joinOrgLink }" />
	</fmt:message>
</c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title><fmt:message key="SUC_TITLE" /></title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	<h1>
		<fmt:message key="SUC_MAIN_MESSAGE">
			<fmt:param value="${ firstName }" />
		</fmt:message>		
	</h1>
	
	<c:choose>
		<c:when test="${ empty orgId or orgId == -1 }">
			<c:out value="${ NO_ORG_MSG_BLOCK }" escapeXml="false" />
		</c:when>
		<c:otherwise>
			<fmt:message key="SUC_ORG_IS_READY">
				<fmt:param value="{TODO!!! ACTUALLY GET THE ORG NAME AND PUT IT HERE!}" />
			</fmt:message>			
			<a href="Dashboard"><fmt:message key="SUC_GO_TO_DASHBOARD" /></a>
		</c:otherwise>
	</c:choose>
	
</body>
</html>