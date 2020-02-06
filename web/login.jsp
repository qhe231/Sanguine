<%--
  Created by IntelliJ IDEA.
  User: jluo669
  Date: 28/01/2020
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>

 <%--Log in page--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="login.js"></script>
</head>

<c:choose>
    <c:when test="${user.getTheme() == 'Snow'}">
        <link rel="stylesheet" type="text/css" href="./CSS/Snow.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Hot Air Balloons'}">
        <link rel="stylesheet" type="text/css" href="./CSS/HotAirBalloons.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Beach'}">
        <link rel="stylesheet" type="text/css" href="./CSS/Beach.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Stationery'}">
        <link rel="stylesheet" type="text/css" href="./CSS/Stationery.css">
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" type="text/css" href="CSS/Botanical.css">
    </c:otherwise>
</c:choose>

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>

<body>

<header class="page-header header container-fluid">
        <div class="content center heightFit">
            <h1 class="center">Welcome back!</h1>

            <div id="box">
                <form action="./login" method="post">
                    <p class="main">

                    <div class="row">
                        <div class="col-2"></div>
                        <div class="col-2"><label>Username: </label></div>
                        <div class="col-3"><input name="username" required></div>
                    </div>

                    <div class="row">
                        <div class="col-2"></div>
                        <div class="col-2"><label>Password: </label></div>
                        <div class="col-3"><input type="password" name="password" required></div>
                    </div>

                    <br>

                    <input type="submit" value="Login" class="Login center button">

                </form>
            </div>

            <c:if test="${ErrorMessage != null}">
                <div class="center"> Error: ${ErrorMessage} <br>
                    Do you need to sign up? <a href="SignUp.jsp">Click here</a>
                </div>
            </c:if>

<div class="g-signin2" data-onsuccess="googleSignIn"></div>

<c:if test="${ErrorMessage != null}">
    <div> Error: ${ErrorMessage} <br>
        Do you need to sign up? <a href="SignUp.jsp">Click here</a>
    </div>
</c:if>
</div>
</header>
</body>
</html>
