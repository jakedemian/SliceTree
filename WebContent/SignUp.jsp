<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="./EnvironmentSetup.jspf" %>

<c:set var="LOG_IN_LINK">
	<a href="Home"><fmt:message key="SIGN_UP_LOG_IN" /></a>
</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title><fmt:message key="SIGN_UP_TITLE" /></title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	
	<div class="wrapper">
		<h1><fmt:message key="SIGN_UP_HEADER" /></h1>
		<div>
			<form method="post" action="SignUpService">
				<input type="text" name="first-name" 
					placeholder="<fmt:message key="SIGN_UP_FIRST_NAME_PLACEHOLDER" />" required/>
				<input type="text" name="last-name" 
					placeholder="<fmt:message key="SIGN_UP_LAST_NAME_PLACEHOLDER" />" required/><br />
				<input type="email" name="email" 
					placeholder="<fmt:message key="SIGN_UP_EMAIL_PLACEHOLDER" />" required/><br />
				<input type="password" name="password" 
					placeholder="<fmt:message key="SIGN_UP_PASSWORD_PLACEHOLDER" />" required/><br />
				<input type="password" name="confirm-password" 
					placeholder="<fmt:message key="SIGN_UP_CONFIRM_PASSWORD_PLACEHOLDER" />" required/><br />
				<input type="submit" name="submit" value="Continue" /><br />
				<br />
			</form>
			<c:if test="${ !empty createUserError }">
				<p style="color: red;"><c:out value="${ createUserError }" /></p>
			</c:if>
		</div>
		<fmt:message key="SIGN_UP_ALREADY_HAVE_ACCOUNT">
			<fmt:param value="${ LOG_IN_LINK }" />
		</fmt:message>
	</div>
</body>
</html>