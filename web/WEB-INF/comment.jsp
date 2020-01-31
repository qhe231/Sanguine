<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul>
    <c:forEach var="comment" items="${comment.children}">
        <li>
            <div class="comment" id="${comment.articleId}">
                <div class="articleTitle">${comment.title}</div>
                <div class="articleAuthor">${comment.author.userName}</div>
                <div class="articlePostTime">${comment.postedTimeStamp}</div>
                <div class="articleContent" id="content-${comment.articleId}">${comment.content}</div>
                <c:if test="${user != null}">
                    <c:if test="${user.userId == comment.author.userId}">
                        <button name="editArticle" class="editButton" id="edit-${comment.articleId}">Edit</button>
                    </c:if>
                    <c:if test="${(user.userId == comment.author.userId || user.userId == rootArticle.author.userId)}">
                    <button name="deleteArticle" class="deleteButton" id="delete-${comment.articleId}>Delete</button>
                    </c:if>
                </c:if>
            </div>
            <c:set var="comment" value="${comment}" scope="request" />
            <jsp:include page="comment.jsp" />
        </li>
    </c:forEach>
</ul>
