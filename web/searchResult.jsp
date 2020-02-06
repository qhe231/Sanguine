<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qhe231
  Date: 3/02/2020
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Search Result</title>


    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <c:choose>
        <c:when test="${user.getTheme() == 'Snow'}">
            <link rel="stylesheet" type="text/css" href="./css/Snow.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Hot Air Balloons'}">
            <link rel="stylesheet" type="text/css" href="./css/HotAirBalloons.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Beach'}">
            <link rel="stylesheet" type="text/css" href="./css/Beach.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Stationery'}">
            <link rel="stylesheet" type="text/css" href="./css/Stationery.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="./css/Botanical.css">
        </c:otherwise>
    </c:choose>

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>
<body>

<header class="page-header header container-fluid">
    <div class="container">

        <h1>Your Search Result</h1>

        <%--display the users that match--%>
        <h2>Users</h2>
        <c:choose>
            <c:when test="${users.size()==0}">
                <p>Sorry, no user matches your search.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="user" items="${users}">
                    <p><a href="./userHomePage?owner=${user.userName}">${user.userName}</a></p>
                </c:forEach>
            </c:otherwise>
        </c:choose>


        <%--display the articles that match--%>
        <h2>Articles and Comments</h2>
        <c:choose>
            <c:when test="${articles.size()==0}">
                <p>Sorry, no article matches your search.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="article" items="${articles}">
                    <p><a href="./article?articleId=${article.articleId}">${article.title}</a></p>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</header>

</body>
</html>
