<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  UserAuthentication: qhe231
  Date: 28/01/2020
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:choose>
        <c:when test="$user.blogName != null}">
            <title>${user.blogName}</title>
        </c:when>
        <c:otherwise>
            <title>${user.userName}'s Blog</title>
        </c:otherwise>
    </c:choose>
</head>
<body>

<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>

<c:choose>
    <c:when test="$user.blogName != null}">
        <h1>${owner.blogName}</h1>
    </c:when>
    <c:otherwise>
        <h1>${owner.userName}'s Blog</h1>
    </c:otherwise>
</c:choose>

<table>
    <caption>Articles</caption>
    <thead>
    <tr>
        <th>Title</th>
        <th>Posted Time</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
        <tr>
            <td><a href="">${article.title}</a></td>
            <td>${article.datePosted}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${reader.userId == owner.userId}">
    <form action="" method="post">
        <input type="submit" name="postANewArticle" value="Post a New Article">
    </form>
</c:if>

</body>
</html>
