<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JESP Blog</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>
<body>
<header class="page-header header container-fluid">
    <div class="container">

        <div class="row">
            <div class="col-md col-12 heading"><h1>Welcome to JESP Blog</h1></div>

            <div class="col-md col-12">
                <button onclick="window.location.href ='./article?articleId=random'" class="right centerSm">View Random Article
                </button>
            </div>
<br>
        </div>
        <br>

                <form action="./search" method="get">
                    <input type="submit" value="Search" placeholder="Enter search term" class="button right centerSm">
                    <input name="search" type="text" class="right centerSm">
                </form>

      <h3>Newest Articles</h3>

        <div class="row hideSm">
            <div class="col-3"><h4>Article</h4></div>
            <div class="col-2"><h4>Author</h4></div>
            <div class="col-2"><h4>Comments</h4></div>
            <div class="col-2"><h4>Posted Time</h4></div>
            <div class="col-2"><h4>Edited Time</h4></div>
        </div>

        <hr>



        <c:forEach var="article" items="${articles}">

            <div class="row">
                <div class="col-md-3 col-12">
                    <a href="./article?articleId=${article.articleId}">${article.title}</a> <br>
                    <c:choose>
                        <c:when test="${article.content.length() <= 100}">
                            <td colspan="4" class="artImage">${article.content}</td>
                        </c:when>
                        <c:otherwise>
                            <td colspan="4" class="artImage">${article.content.substring(0,99)}...</td>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-2 col-12">
                    <span><img src="${article.author.getAvatarURL()}" width="64px"></span>
                    <span><a
                            href="./userHomePage?owner=${article.author.getUserName()}">${article.author.getUserName()}</a></span>
                </div>

                <div class="col-md-2 col-12"><span class="displaySm grey">Comments: </span>${article.children.size()}
                </div>

                <div class="col-md-2 col-12"><span class="displaySm grey">Posted time: </span>${article.postedTimeStamp}
                </div>

                <div class="col-md-2 col-12"><c:if test="${article.editedTimeStamp != null}"><span
                        class="displaySm grey">Edited time: </span>${article.editedTimeStamp}</c:if>
                </div>

            </div>


            <hr>
        </c:forEach>

        <br>

    </div>

</header>
</body>
</html>