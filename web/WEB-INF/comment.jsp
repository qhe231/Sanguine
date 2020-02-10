<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="./UserAccountJS.js"></script>


<c:if test="${user != null}">
    <button class="addComment" id="comment-${parent.articleId}-${rootArticle.articleId}">Add Comment</button>
</c:if>
<br>
<hr>

<ul>
    <c:forEach var="comment" items="${parent.children}">

        <li>
            <div class="comment" id="${comment.articleId}">

                <div class="articleTitle" id="title-${comment.articleId}">${comment.title}</div>

                <div class="col">
                    <span><img src="${comment.author.getAvatarURL()}" width="64px"></span>
                    <span><a class="articleAuthor"
                             href="./userHomePage?owner=${comment.author.getUserName()}">${comment.author.getUserName()}</a></span>
                    <span class="articlePostTime right">${comment.postedTimeStamp}</span>
                </div>

                <div class="articleContent" id="content-${comment.articleId}">${comment.content}</div>

            </div>

            <div class="right">

                <span class="reactionBox">
                <input type="checkbox" class="reactionButton" id="like-${comment.articleId}">
                <label for="like-${comment.articleId}" class="reactionLabel"> <img src="./images/site/plus.png"
                                                                                   class="reactionIcon"></label>
                <span class="reactionCounter" id="nLike-${comment.articleId}"></span></span>


                <span class="reactionBox">
                <input type="checkbox" class="reactionButton" id="dislike-${comment.articleId}">
                <label for="dislike-${comment.articleId}"><img src="./images/site/minus.png"
                                                               class="reactionIcon"></label>
                    <span class="reactionCounter" id="nDislike-${comment.articleId}"></span></span>

                <c:if test="${user != null}">
                    <c:choose>
                        <c:when test="${user.userId == comment.author.userId}">
                            <button class="editButton" id="edit-${comment.articleId}">Edit</button>
                            <button class="deleteButton" id="delete-${comment.articleId}">Delete</button>
                        </c:when>

                        <c:when test="${user.userId == rootArticle.author.userId}">
                            <button class="deleteButton" id="delete-${comment.articleId}">Delete</button>
                        </c:when>
                    </c:choose>
                </c:if>
            </div>
            <c:set var="parent" value="${comment}" scope="request"/>
            <c:set var="rootArticle" value="${rootArticle}" scope="request"/>
            <c:set var="user" value="${user}" scope="request"/>
            <jsp:include page="comment.jsp"/>

        </li>
    </c:forEach>

</ul>
