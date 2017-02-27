<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./EnvironmentSetup.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./assets/css/main.css">
	<title>Sign Up</title>
</head>
<body>
	<c:import url="./Header.jsp" />		
	
	<div class="wrapper">
		<h1>Sign Up</h1>
		<div>
			<form method="post" action="SignUpService">
				<input type="text" name="first-name" placeholder="First" required/>
				<input type="text" name="last-name" placeholder="Last" required/><br />
				<input type="email" name="email" placeholder="Email" required/><br />
				<input type="password" name="password" placeholder="Password" required/><br />
				<input type="password" name="confirm-password" placeholder="Confirm" required/><br />
				<input type="submit" name="submit" value="Continue" /><br />
				<br />
			</form>
			<c:if test="${ !empty createUserError }">
				<p style="color: red;"><c:out value="${ createUserError }" /></p>
			</c:if>
		</div>
		Already have an account? Go home and <a href="Home">Log In</a>.
	</div>
</body>
</html>