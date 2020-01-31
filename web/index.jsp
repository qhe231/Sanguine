<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JESP Blog</title>
    <style type="text/css">
        body {
            width: 600px;
            margin: 6em auto;
            color: #444;
            text-align: justify;
            font-family: sans-serif;
            font-size: 14pt;
            line-height: 150%;
        }

        h1, h2 {
            text-decoration: lightgrey underline;
            color: #222;
        }

        h2 {
            margin-top: 2em;
        }

        code {
            background-color: rgba(255, 167, 182, 0.41);
            color: darkred;
            font-family: monospace;
            border: 1px solid darkred;
            padding: 1px 5px;
        }

        a:visited, a:active, a {
            color: dodgerblue;
            text-decogitration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>

<h1>Welcome to JESP Blog</h1>

<table>
    <caption>Newest Articles</caption>
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Comments</th>
        <th>Posted Time</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
        <tr>
            <td><a href="./article?articleId=${article.articleId}">${article.title}</a></td>
            <td>
                <span><a href="./userHomePage?owner=${article.author.getUserName()}">${article.author.getUserName()}</a></span>
                <span><img src="${article.author.getAvatarURL()}"></span>
            </td>
            <td>${article.children.size()}</td>
            <td>${article.postedTimeStamp}</td>
        </tr>
        <tr>
            <c:choose>
                <c:when test="${article.content.length() <= 100}">
                    <td colspan="4">${article.content}</td>
                </c:when>
                <c:otherwise>
                    <td colspan="4">${article.content.substring(0,99)}...</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>