<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qhe231
  Date: 3/02/2020
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Result</title>
</head>
<body>

<h1>Your Search Result</h1>

<%--display the users that match--%>
<h2>Users</h2>
<c:choose>
    <c:when test="${users.size()==0}">
        <p>Sorry, no user matches your search.</p>
    </c:when>
    <c:otherwise>
        <c:forEach var="user" items="${users}">
            <p><a href="./userHomePage?owner=${user.userName}">${user.userName}</a></p>
        </c:forEach>
    </c:otherwise>
</c:choose>


<%--display the articles that match--%>
<h2>Articles and Comments</h2>
<c:choose>
    <c:when test="${articles.size()==0}">
        <p>Sorry, no article matches your search.</p>
    </c:when>
    <c:otherwise>
        <c:forEach var="article" items="${articles}">
            <p><a href="./article?articleId=${article.articleId}">${article.title}</a></p>
        </c:forEach>
    </c:otherwise>
</c:choose>

</body>
</html>
