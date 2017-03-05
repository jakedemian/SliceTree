<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="./EnvironmentSetup.jspf" %>

<div id="header" style="">
	<c:choose>
		<c:when test="${ userLoggedOn }" >
			<a href="./Logout"><fmt:message key="HEADER_LOGOUT" /></a>
		</c:when>
		<c:otherwise>
			<a href="./SignUp"><fmt:message key="HEADER_CREATE_ACCOUNT" /></a>
		</c:otherwise>
	</c:choose>
</div>