<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script>
    tinymce.init({
        selector: '#articleEditor'
    });
</script>

<form action="./postArticle" method="post">
    <input name="title"><br>
    <textarea id="articleEditor" name="content"></textarea><br>
    <input type="submit" name="submit">
    <button name="cancel">Cancel</button>
</form>