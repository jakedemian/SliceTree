<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<div id="header" style="">
	<c:choose>
		<c:when test="${ userLoggedOn }" >
			<a href="./Logout">Logout</a>
		</c:when>
		<c:otherwise>
			<a href="./SignUp">Create Account</a>
		</c:otherwise>
	</c:choose>
</div>