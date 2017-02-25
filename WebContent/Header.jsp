<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>

<div id="header" style="">
	<c:choose>
		<c:when test="${ userLoggedOn }" >
			<a href="./Logout">Logout</a>
		</c:when>
		<c:otherwise>
			<form id="loginForm" method="post" action="./Login">
				<input type="text" name="email" placeholder="Email" />
				<input type="password" name="password" placeholder="Password" />
				<input type="submit" value="submit"/>
			</form>
		</c:otherwise>
	</c:choose>
</div>