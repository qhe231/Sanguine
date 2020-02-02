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
    <title>New Article</title><jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include> <br>
    <script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
    <script>
        tinymce.init({
            selector: '#articleEditor'
        });
    </script>
</head>
<body>


<form action="./postArticle" method="post">
    <input name="title" required><br>
    <textarea id="articleEditor" name="content"></textarea><br>
    <input type="submit" name="submit" id="submitButton">
    <button name="cancel" id="cancelButton">Cancel</button>
</form>

</body>
</html>
