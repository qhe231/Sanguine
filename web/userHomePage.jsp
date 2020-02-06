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
        <c:when test="${owner.blogName != null}">
            <title>${owner.blogName}</title>
        </c:when>
        <c:otherwise>
            <title>${owner.userName}'s Blog</title>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${user.getTheme() == 'Snow'}">
            <link rel="stylesheet" type="text/css" href="./CSS/Snow.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Hot Air Balloons'}">
            <link rel="stylesheet" type="text/css" href="./CSS/HotAirBalloons.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Beach'}">
            <link rel="stylesheet" type="text/css" href="./CSS/Beach.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Stationery'}">
            <link rel="stylesheet" type="text/css" href="./CSS/Stationery.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="CSS/Botanical.css">
        </c:otherwise>
    </c:choose>

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

    <script type="text/javascript" src="commentsJS.js"></script>
</head>


</head>
<body>

<header class="page-header header container-fluid">
    <div class="container minHeight">

        <%--Display Blog Name--%>
        <c:choose>
        <c:when test="${owner.blogName != null}">
        <h1>${owner.blogName}</h1>
        </c:when>
        <c:otherwise>
        <h1>${owner.userName}'s Blog</h1>
        </c:otherwise>
        </c:choose>

        <%--Button to post new article--%>
        <c:if test="${user.userId == owner.userId}">
        <form action="./newArticle.jsp" method="post">
            <input type="submit" name="postANewArticle" value="Post a New Article" class="button">
        </form>

        <button type="button" id="commentsButton">Show All Comments</button>
        </c:if>

        <c:choose>

        <c:when test="${articles[0].title != null}">
            <%--Display all articles associated with the blog--%>
        <h2>Articles</h2>
        <div class="row">
            <div class="col-4"><h4>Article</h4></div>
            <div class="col-2"><h4>Comments</h4></div>
            <div class="col-2"><h4>Posted Time</h4></div>
            <div class="col-2"><h4>Edited time</h4></div>
        </div>
        <hr>

        <c:forEach var="article" items="${articles}">
        <div class="row">

            <div class="col-4"><a href="./article?articleId=${article.articleId}">${article.title}</a>
                <c:choose>
                    <c:when test="${article.content.length() <= 100}">
                        <td colspan="4">${article.content}</td>
                    </c:when>
                    <c:otherwise>
                        <td colspan="4">${article.content.substring(0,99)}...</td>
                    </c:otherwise>
                </c:choose></div>

            <div class="col-2">${article.children.size()}</div>

            <div class="col-2">${article.postedTimeStamp}</div>
        </div>
        <hr>
        </c:forEach>
        </c:when>

        <c:otherwise>
        You have not posted any articles.
        </c:otherwise>

        </c:choose>

        <div id="comments">
            <h2>Comments</h2>

            <c:if test="${user.userId == owner.userId}">
                <c:choose>
                    <c:when test="${comments.size() <= 0}">
                        <p>You do not have any comments.</p>
                    </c:when>
                    <c:otherwise>

                        <div class="row">
                            <div class="col">Title</div>
                            <div class="col">Posted Time</div>
                        </div>
                        <hr>

                        <c:forEach var="comment" items="${comments}">
                            <div class="row">
                                <div class="col"><a href="./article?articleId=${comment.articleId}">${comment.title}</a>
                                </div>
                                <div class="col"> ${comment.postedTimeStamp}</div>
                            </div>
                            <hr>
                        </c:forEach>

                    </c:otherwise>
                </c:choose>
            </c:if>
            <script>console.log(${comments.size()})</script>
        </div>

</body>
</html>
