<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sbro190
  Date: 29/01/2020
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sanguine: ${article.title}</title>
    <script src="https://cdn.tiny.cloud/1/djtof1icz6but0e9v9pg9rgpweztf9nghye4u2u08y9gub17/tinymce/5/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script type="text/javascript" src="./articleCommentEditDelete.js"></script>
    <script type="text/javascript" src="./articleReaction.js"></script>
    <script type="text/javascript" src="./UserAccountJS.js"></script>


    <jsp:include page="./NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>
<body>
<header class="page-header header container-fluid">
    <div class="container">

        <div class="mainArticle" id="${article.articleId}">

            <div class="row">
                <div class="articleTitle col" id="title-${article.articleId}"><h1>${article.title}</h1></div>
            </div>

            <div class="row">
                <div class="col">
                    <span><img src="${article.author.getAvatarURL()}" width="64px"></span>
                    <span><a class="articleAuthor"
                             href="./userHomePage?owner=${article.author.getUserName()}">${article.author.getUserName()}</a></span>
                    <span class="articlePostTime right">${article.postedTimeStamp}</span>
                </div>
            </div>

            <div class="row">
                <div class="articleContent col" id="content-${article.articleId}">${article.content}</div>
            </div>
        </div>

        <div class="right">
            <c:if test="${article.postedTimeStamp < article.editedTimeStamp}">
                <i>Last edited: ${article.editedTimeStamp}</i>
            </c:if>
<span class="reactionBox">
            <input type="checkbox" class="reactionButton" id="like-${article.articleId}">
            <label for="like-${article.articleId}" class="reactionLabel"> <img src="./images/site/plus.png"
                                                                               class="reactionIcon"></label>
    <span class="reactionCounter" id="nLike-${article.articleId}"></span> </span>


            <span class="reactionBox">
            <input type="checkbox" class="reactionButton" id="dislike-${article.articleId}">
            <label for="dislike-${article.articleId}" class="reactionLabel"><img src="./images/site/minus.png"
                                                                                 class="reactionIcon"></label>
                <span class="reactionCounter" id="nDislike-${article.articleId}"></span></span>

            <c:if test="${user != null and user.userId == article.author.userId}">
                <button name="editArticle" class="editButton" id="edit-${article.articleId}">Edit</button>

                <button class="deleteButton" id="delete-${article.articleId}">Delete</button>

            </c:if>
        </div>


        <c:set var="parent" value="${article}" scope="request"/>
        <c:set var="rootArticle" value="${article}" scope="request"/>
        <c:set var="user" value="${user}" scope="request"/>

        <jsp:include page="comment.jsp"/>

    </div>
</header>

</body>
</html>
