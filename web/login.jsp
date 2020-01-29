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
</head>
<body>
<div id="container">
    <div id="box">
        <form action="./Login" method="post">
            <p class="main">
                <label>User Name: </label>
                <input name="username" value=""><br>
                <label>Password: </label>
                <input type="password" name="password" value="">
            </p>
            <p class="space">
                <input type="submit" value="Login" class="Login">
            </p>
        </form>
    </div>


</div>

<c:if test="${Error != null}">
    <div> Error: ${Error} <br>
        Do you need to sign up? <a href="SignUp.jsp">Click here</a>
    </div>
</c:if>
</body>
</html>
