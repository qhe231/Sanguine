<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="comment" id="${comment.articleId}">
    <div class="articleTitle">${comment.title}</div>
    <div class="articleAuthor">${comment.author.userName}</div>
    <div class="articlePostTime">${comment.postedTimeStamp}</div>
    <div class="articleContent">${comment.content}</div>
    <button name="editArticle">Edit</button>
    <button name="deleteArticle">Delete</button>
</div>
<c:set var="comment" value="${comment}" scope="request" />
<jsp:include page="comment.jsp" />
