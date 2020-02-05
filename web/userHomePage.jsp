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
    <script type="text/javascript" src="commentsJS.js"></script>
</head>
<body>

<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>

<c:choose>
    <c:when test="${owner.blogName != null}">
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
        <th>Last Edited Time</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
        <tr>
            <td><a href="./article?articleId=${article.articleId}">${article.title}</a></td>
            <td>${article.postedTimeStamp}</td>
            <td>${article.editedTimeStamp}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${user.userId == owner.userId}">
    <form action="./newArticle.jsp" method="post">
        <input type="submit" name="postANewArticle" value="Post a New Article">
    </form>

    <button type="button" id="commentsButton">Show All Comments</button>
</c:if>


<div id="comments">
    <c:if test="${user.userId == owner.userId}">
        <c:choose>
            <c:when test=" ${comments.size() == 0}">
                <p>You do not have any comments.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <caption>Comments</caption>
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author Name</th>
                        <th>Posted Time</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td><a href="./article?articleId=${comment.articleId}">${comment.title}</a></td>
                            <td>${comment.author.userName}</td>
                            <td>${comment.postedTimeStamp}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>

</body>
</html>
