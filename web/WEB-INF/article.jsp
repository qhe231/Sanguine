<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sbro190
  Date: 29/01/2020
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JEPS Blog: ${article.title}</title>
</head>
<body>
<div class="mainArticle" id="${article.articleId}">
    <div class="articleTitle">${article.title}</div>
    <div class="articleAuthor">${article.author.userName}</div>
    <div class="articlePostTime">${article.postedTimeStamp}</div>
    <div class="articleContent">${article.content}</div>
    <c:if test="${user != null && user.userId == article.author.userId}">
        <button name="editArticle" class="editButton">Edit</button>
        <button name="deleteArticle" onclick="window.location.href = './DeleteArticle?articleId=${article.articleId}'">Delete</button>
    </c:if>
</div>
<c:set var="comment" value="${article}" scope="request" />
<c:set var="rootArticle" value="${article}" scope="request" />
<c:set var="user" value="${user}" scope="request" />
<jsp:include page="comment.jsp" />
</body>
</html>
