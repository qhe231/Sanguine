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

    <style>

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        li a:hover {
            color: blue;
            text-decoration: none;

        }

        nav {
            background-color: grey;
            overflow: hidden;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            border-bottom: 2px solid black;
        }

        nav a {
            color: white;
            text-align: center;
            padding: 30px;
            text-decoration: none;
            font-size: 25px;
            float: left;
            font-family: "Arial", "sans-serif";
        }

        .left {
            float: left;
        }

        .right {
            float: right;
        }

        a:visited, a:active {
            color: white;
        }

    </style>

    <title>Title</title>
</head>
<body>

<nav>
    <ul>
        <li class="left"><a href="./index">Home</a></li>

        <c:choose>

        <c:when test="${user.userName != null}">
        <li class="right"><a href="./">Log out</a></li>
        <li class="right"><a href="./UserAccountPage.jsp">Account</a></li>
        <li class="right"><a href="./userHomePage?owner=${user.userName}">${user.blogName}</a></li>
        </c:when>

        <c:otherwise>
        <li class="right"><a href="./SignUp.jsp">Sign up</a></li>
        <li class="right"><a href="./login.jsp">Log in</a></li>
            </c:otherwise>

        </c:choose>
    </ul>
</nav>

<br><br><br><br>

</body>