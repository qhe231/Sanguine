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
</jsp:include> <br>
    <title>New Article</title>
    <script src="https://cdn.tiny.cloud/1/djtof1icz6but0e9v9pg9rgpweztf9nghye4u2u08y9gub17/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
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
</head>
<body>


<form action="./postArticle" method="post">
    <input name="title" required><br>
    <textarea id="articleEditor" name="content"></textarea><br>
    <input type="submit" name="submit" id="submitButton">
    <button name="cancel" id="cancelButton" onclick="window.history.back();">Cancel</button>
</form>

</body>
</html>
