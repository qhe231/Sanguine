<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ekoe938
  Date: 24/01/2020
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta name="google-signin-client_id" content="28193811864-vj7d671e3btucp1bopgidr8ulso973cd.apps.googleusercontent.com">

    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="logout.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

</head>
<body>

<c:choose>
    <c:when test="${user.getTheme() == 'Snow'}">
        <link rel="stylesheet" type="text/css" href="./css/Snow.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Hot Air Balloons'}">
        <link rel="stylesheet" type="text/css" href="./css/HotAirBalloons.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Beach'}">
        <link rel="stylesheet" type="text/css" href="./css/Beach.css">
    </c:when>
    <c:when test="${user.getTheme() == 'Stationery'}">
        <link rel="stylesheet" type="text/css" href="./css/Stationery.css">
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" type="text/css" href="./css/Botanical.css">
    </c:otherwise>
</c:choose>

<nav class="navbar navbar-expand-md">

    <a class="navbar-brand" href="./index">Home</a>

    <button class="navbar-toggler navbar-dark" type="button" data-toggle="collapse" data-target="#main-navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="main-navigation">
        <ul class="navbar-nav">

            <c:choose>

                <c:when test="${user.userName != null}">
                    <li class="nav-item"><a href="./newArticle.jsp" class="nav-link">Create Article</a></li>
                    <li class="nav-item"><a href="./userHomePage?owner=${user.userName}"
                                            class="nav-link">${user.blogName}</a></li>
                    <li class="nav-item"><a href="./UserAccountPage.jsp" class="nav-link">Account</a></li>
                    <li class="nav-item"><a href="./logout" class="nav-link" id="logout">Log out</a></li>
                </c:when>

                <c:otherwise>
                    <li class="nav-item"><a href="./login.jsp" class="nav-link">Log in</a></li>
                    <li class="nav-item"><a href="./SignUp.jsp" class="nav-link">Sign up</a></li>
                </c:otherwise>

            </c:choose>
        </ul>
    </div>
</nav>

</body>