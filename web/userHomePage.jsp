<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  UserAuthentication: qhe231
  Date: 28/01/2020
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">

    <c:choose>
        <c:when test="${owner.blogName != null}">
            <title>${owner.blogName}</title>
        </c:when>
        <c:otherwise>
            <title>${owner.userName}'s Blog</title>
        </c:otherwise>
    </c:choose>

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>
    <script type="text/javascript" src="commentsJS.js"></script>
    <link href="./CSS/userInfo.css" rel="stylesheet" type="text/css">
</head>


<body>

<header class="page-header header container-fluid">
    <div class="container minHeight">

        <%--Display Blog Name--%>
        <c:choose>
            <c:when test="${owner.blogName != null}">
                <h1 class="heading">${owner.blogName}</h1>
            </c:when>
            <c:otherwise>
                <h1 class="heading">${owner.userName}'s Blog</h1>
            </c:otherwise>
        </c:choose>

        <%--Buttons to post new article and view all comments--%>
        <c:if test="${user.userId == owner.userId}">
            <form action="./newArticle.jsp" method="post">
                <input type="submit" name="postANewArticle" value="Create a New Article" class="button right">
            </form>

        </c:if>


        <div id="userInfo">
            <h2>About Me</h2>


            <img src="${owner.avatarURL}"><br><br>

            <p><strong>Username:</strong> ${owner.userName}</p>


            <p><strong>Name:</strong> ${owner.firstName} ${owner.lastName}</p>
            <p><strong>Date of Birth:</strong> ${owner.dateOfBirth}</p>
            <p><strong>Description:</strong> ${owner.profile}</p>
        </div>

        <c:choose>

            <c:when test="${articles[0].title != null}">
                <%--Display all articles associated with the blog--%>
                <h2>Articles</h2>
                <div class="row hideSm">
                    <div class="col-4"><h4>Article</h4></div>
                    <div class="col-2"><h4>Comments</h4></div>
                    <div class="col-2"><h4>Posted Time</h4></div>
                    <div class="col-2"><h4>Last Edited</h4></div>
                </div>
                <hr>

                <c:forEach var="article" items="${articles}">
                    <div class="row">

                        <div class="col-md-4 col-12"><a
                                href="./article?articleId=${article.articleId}">${article.title}</a>
                            <c:choose>
                                <c:when test="${article.content.length() <= 100}">
                                    <td colspan="4">${article.content}</td>
                                </c:when>
                                <c:otherwise>
                                    <td colspan="4">${article.content.substring(0,99)}...</td>
                                </c:otherwise>
                            </c:choose></div>

                        <div class="col-md-2 col-12"><span
                                class="displaySm grey">Comments: </span>${article.children.size()}</div>

                        <div class="col-md-2 col-12"><span
                                class="displaySm grey">Posted time: </span>${article.postedTimeStamp}
                        </div>

                        <div class="col-md-2 col-12">
                            <c:if test="${article.editedTimeStamp != null}">
                                <span class="displaySm grey">Edited time: </span>
                                ${article.editedTimeStamp}
                            </c:if>
                        </div>

                    </div>
                    <hr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <br>
                ${owner.userName} has not posted any articles.
                <br>
            </c:otherwise>

        </c:choose>

        <c:if test="${user.userId == owner.userId}">
            <br>
            <button type="button" id="commentsButton" class="right">Show All Comments</button>
            <br>
        </c:if>

        <div id="comments">
            <h2>Comments</h2>
            <div class="comments">

                <c:if test="${user.userId == owner.userId}">
                    <c:choose>
                        <c:when test="${comments.size() <= 0}">
                            <p>You do not have any comments.</p>
                        </c:when>
                        <c:otherwise>

                            <div class="row hideSm">
                                <div class="col-6"><h4>Title</h4></div>
                                <div class="col-2"><h4>Posted Time</h4></div>
                                <div class="col-2"><h4>Edited Time</h4></div>
                            </div>
                            <hr>

                            <c:forEach var="comment" items="${comments}">
                                <div class="row">

                                    <div class="col-md-6 col-12"><a
                                            href="./article?articleId=${comment.articleId}"> ${comment.title} </a><br>
                                            ${comment.content}
                                    </div>

                                    <div class="col-md-2 col-12">
                                        <span class="displaySm grey">Posted time: </span>
                                            ${comment.postedTimeStamp}</div>

                                    <div class="col-md-2 col-12">
                                        <c:if test="${comment.editedTimeStamp != null}">
                                            <span class="displaySm grey">Edited time: </span>
                                            ${comment.editedTimeStamp}
                                        </c:if>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>

                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </div>
</header>

</body>
</html>
