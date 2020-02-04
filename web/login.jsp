<%--
  Created by IntelliJ IDEA.
  User: jluo669
  Date: 28/01/2020
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="login.js"></script>
</head>

<body>

<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>
<br>

<div id="container">
    <div id="box">
        <form action="./login" method="post">
            <p class="main">
                <label>User Name: </label>
                <input name="username" value="" required><br>
                <label>Password: </label>
                <input type="password" name="password" value="" required>
            </p>
            <p class="space">
                <input type="submit" value="Login" class="Login">
            </p>
        </form>
    </div>
</div>

<meta name="google-signin-client_id" content="28193811864-vj7d671e3btucp1bopgidr8ulso973cd.apps.googleusercontent.com">
<div class="g-signin2" data-onsuccess="googleSignIn"></div>

<c:if test="${ErrorMessage != null}">
    <div> Error: ${ErrorMessage} <br>
        Do you need to sign up? <a href="SignUp.jsp">Click here</a>
    </div>
</c:if>
</body>
</html>
