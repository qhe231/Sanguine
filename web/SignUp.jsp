<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jluo669
  Date: 28/01/2020
  Time: 3:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <script type="text/javascript" src="SignUpJS.js"></script>
    <script type="text/javascript" src="uploadAvatar.js"></script>
</head>
<body>


<%--Sign up page--%>
<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>
<br>

<div id="contain">
    <form action="./SignUp" method="post" enctype="multipart/form-data">
        <fieldset>
            <label>Blog Name: </label>
            <input name="blogName" value="" required><br>
            <label>First Name: </label>
            <input name="firstName" value="" required><br>
            <label>Last Name: </label>
            <input name="lastName" value="" required><br>

            <label>User Name: </label>
            <input name="userName" onblur="checkUserName()" id="userName" required><br>
            <span id="userNameMessage"></span>

            <br>

            <label>Password: </label>
            <input type="password" name="password" onkeyup='checkPassword()' id="password" required> <br>
            <label>Confirm Your Password: </label>
            <input type="password" name="password" onkeyup='checkPassword()' id="confirmPassword" required> <br>
            <span id="message"></span>

            <br>
            <label>DOB: </label>
            <input type="date" name="dob" required>
            <br>
            <label>Profile: </label>
            <textarea name="profile" value="" rows="4" cols="40"></textarea> <br>


            <label>Choose An Avatar: </label>
            <input type="radio" name="avatar" value="./images/1.png" class="avatar"><img src="./images/1.png">
            <input type="radio" name="avatar" value="./images/2.png" class="avatar"><img src="./images/2.png">
            <input type="radio" name="avatar" value="./images/3.png" class="avatar"><img src="./images/3.png">
            <input type="radio" name="avatar" value="./images/4.png" class="avatar"><img src="./images/4.png">
            <input type="radio" name="avatar" value="./images/5.png" class="avatar"><img src="./images/5.png">
            <input type="radio" name="avatar" value="./images/6.png" class="avatar"><img src="./images/6.png">
            <input type="radio" name="avatar" value="./images/7.png" class="avatar"><img src="./images/7.png">
            <input type="radio" name="avatar" value="./images/8.png" class="avatar"><img src="./images/8.png">
            <input type="radio" name="avatar" value="./images/9.png" class="avatar"><img src="./images/9.png">
            <input type="radio" name="avatar" value="./images/10.png" class="avatar"><img src="./images/10.png">
            <input type="radio" name="avatar" class="avatar" id="ownAvatar"><span
                id="ownAvatarPic">Your Own Avatar</span>
            <input type="file" id="ownAvatarFile" name="newAvatar" accept="image/*" onblur="uploadAvatar()">
            <br>
            <span id="changeAvatarMessage"></span>

            <br><br>
            <p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a
                    href="https://www.iconfinder.com/iconsets/business-avatar-1" target="_blank"><em>slack</em></a></p>
            <input type="submit" id="submit">
        </fieldset>
    </form>
</div>

<c:if test="${error != null}">
    <div>${error}</div>
</c:if>

</body>
</html>
