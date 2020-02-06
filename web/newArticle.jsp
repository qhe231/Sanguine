<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sbro190
  Date: 29/01/2020
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Article</title>



    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

    <script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
    <title>New Article</title>
    <script src="https://cdn.tiny.cloud/1/djtof1icz6but0e9v9pg9rgpweztf9nghye4u2u08y9gub17/tinymce/5/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script>
        tinymce.init({
            selector: '#articleEditor',
            plugins: 'image autoresize',
            image_description: false,
            image_dimensions: false,
            image_uploadtab: true,
            images_upload_url: './articleImage'
        });
    </script>

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

</head>
<body>

<header class="page-header header container-fluid">
    <div class="container heightVh">
        <h3>Create New Article</h3>

        <form action="./postArticle" method="post">
            Title
            <input name="title" required><br>
            <textarea id="articleEditor" name="content"></textarea><br>
            <input type="submit" name="submit" id="submitButton" class="button">
            <button name="cancel" id="cancelButton" onclick="window.history.back();">Cancel</button>
        </form>
    </div>
</header>


</body>
</html>
