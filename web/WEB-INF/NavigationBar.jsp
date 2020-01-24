<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ekoe938
  Date: 24/01/2020
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<nav>
    <ul>
        <li>Main</li>

        <c:choose>
            <c:when test="">
        <li>Homepage</li>
        <li>User account</li>
            </c:when>

            <c:otherwise>
        <li>Login</li>
         <li>Signup</li>
            </c:otherwise>

        </c:choose>
    </ul>
</nav>

</body>