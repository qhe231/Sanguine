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

<header class="page-header header container-fluid">
    <div class="container">

        <div class="row">
            <div class="col-md-6 col-12 heading"><h1>Welcome to JESP Blog</h1></div>

            <div class="col-md col-12centerSm">
                <button onclick="window.location.href ='./article?articleId=random'" class="right">Random
                    Article
                </button>
            </div>

            <div class="col-md-4 col-12">
                <form action="./search" method="get">
                    <input type="submit" value="Search" placeholder="Enter search term" class="button right">
                    <input name="search" type="text" class="right">
                </form>
            </div>
        </div>
        <br>


        <div class="row">
            <div class="col"><h3>Articles</h3></div>
            <div id="sortingDiv" class="right">
                Rank articles in order of:
                <select id="sortingSelector">
                    <option value="sort_date">Date Posted</option>
                    <option value="sort_popular">Popularity</option>
                </select>
            </div>
        </div>


        <div class="row hideSm">
            <div class="col-3"><h5>Article</h5></div>
            <div class="col-2"><h5>Author</h5></div>
            <div class="col-2"><h5>Comments</h5></div>
            <div class="col-2"><h5>Posted Time</h5></div>
            <div class="col-2"><h5>Edited Time</h5></div>
        </div>

        <hr>

        <c:forEach var="article" items="${articles}">

            <div class="row articleRow" id="${article.articleId}">
                <div class="col-md-3 col-12">
                    <a href="./article?articleId=${article.articleId}">${article.title}</a>
                    <br>${article.contentPreview}

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

            <div class="row">
                <div class="col-12">
                    <img src="./images/plus.png" class="reactionIcon"> &nbsp;
                    <span class="reactionCounter" id="nLike-${article.articleId}"> </span> &nbsp;
                    <img src="./images/minus.png" class="reactionIcon"> &nbsp;
                    <span class="reactionCounter" id="nDislike-${article.articleId}"></span>
                </div>
            </div>

            <hr>
        </c:forEach>

        <br>

    </div>

</header>
</body>
</html>