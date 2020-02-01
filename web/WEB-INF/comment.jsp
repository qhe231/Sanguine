<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${user != null}">
    <button class="addComment" id="comment-${parent.articleId}-${rootArticle.articleId}">Add Comment</button>
</c:if>
<ul>
    <c:forEach var="comment" items="${parent.children}">
        <li>
            <div class="comment" id="${comment.articleId}">
                <div class="articleTitle" id="title-${comment.articleId}">${comment.title}</div>
                <div class="articleAuthor">${comment.author.userName}</div>
                <div class="articlePostTime">${comment.postedTimeStamp}</div>
                <div class="articleContent" id="content-${comment.articleId}">${comment.content}</div>
                <c:if test="${user != null}">
                    <c:if test="${user.userId == comment.author.userId}">
                        <button class="editButton" id="edit-${comment.articleId}">Edit</button>
                    </c:if>
                    <c:if test="${(user.userId == comment.author.userId || user.userId == rootArticle.author.userId)}">
                        <button class="deleteButton" id="delete-${comment.articleId}">Delete</button>
                    </c:if>
                </c:if>
            </div>
            <c:set var="parent" value="${comment}" scope="request" />
            <c:set var="rootArticle" value="${rootArticle}" scope="request" />
            <c:set var="user" value="${user}" scope="request" />
            <jsp:include page="comment.jsp" />
        </li>
    </c:forEach>

</ul>
