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

    <script type="text/javascript" src="./articleReaction.js"></script>
    <script type="text/javascript" src="./sortIndex.js"></script>
</head>
<body>
<div id="sortingDiv">
    Rank articles in order of:
    <select id="sortingSelector">
        <option value="sort_date">Date Posted</option>
        <option value="sort_popular">Popularity</option>
    </select>
</div>
<header class="page-header header container-fluid">
    <div class="container">

        <div class="row">
            <div class="col heading"><h1>Welcome to JESP Blog</h1></div>

            <div class="col">
                <button onclick="window.location.href ='./article?articleId=random'" class="right">View Random Article
                </button>
            </div>

        </div>

        <div class="row">
            <div class="col"><h3>Newest Articles</h3></div>
            <div class="col">
                <form action="./search" method="get">
                    <input type="submit" value="Search"
                                                            class="button right">
                    <input name="search" type="text" class="right">
                </form>
            </div>
        </div>


        <div class="row">
            <div class="col-3"><h4>Article</h4></div>
            <div class="col-2"><h4>Author</h4></div>
            <div class="col-2"><h4>Comments</h4></div>
            <div class="col-2"><h4>Posted Time</h4></div>
            <div class="col-2"><h4>Edited Time</h4></div>
        </div>
        <hr>

        <br>

        <c:forEach var="article" items="${articles}">

            <div class="row articleRow" id="${article.articleId}">
                <div class="col-3">
                    <a href="./article?articleId=${article.articleId}">${article.title}</a> <br>
                    <img src="./images/plus.png" class="reactionIcon"><span class="reactionCounter" id="nLike-${article.articleId}"></span>
                    <img src="./images/minus.png" class="reactionIcon"><span class="reactionCounter" id="nDislike-${article.articleId}"></span><br>
                            <td colspan="4" class="artImage">${article.contentPreview}</td>
                </div>
                <div class="col-2">
                    <span><img src="${article.author.getAvatarURL()}" width="64px"></span>
                    <span><a
                            href="./userHomePage?owner=${article.author.getUserName()}">${article.author.getUserName()}</a></span>
                </div>
                <div class="col-2">${article.children.size()}</div>
                <div class="col-2">${article.postedTimeStamp}</div>
                <div class="col-2">${article.editedTimeStamp}</div>

            </div>


            <hr>
        </c:forEach>

        <br>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
                crossorigin="anonymous"></script>

    </div>

</header>
</body>
</html>