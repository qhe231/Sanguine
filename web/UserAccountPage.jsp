<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ekoe938
  Date: 24/01/2020
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Account Settings</title>
    <script type="text/javascript" src="UserAccountJS.js"></script>
    <script type="text/javascript" src="uploadAvatar.js"></script>
    <style>

        /*Style avatar thumbnail*/
        .avatar {
            width: 100px;
            height: 100px;
        }
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="main.css">

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>
<body>
<header class="page-header header container-fluid">
    <div class="container">

        <h1>Account settings</h1>

        <%--Button to change username--%>
        <h3>Username</h3>
        ${user.userName} <br><br>
        <button onclick="showForm('changeUsername', 'usernameBtn')" id="usernameBtn">Change</button>

        <%--Form to change username--%>
        <div style="display: none" id="changeUsername">
            <form action="./ChangeUsername">
                New username: <input type="text" name="newName" placeholder="20 characters or less" maxlength="20"
                                     onkeyup="checkUserName()" id="userName" required>
                <span id="userNameMessage"></span><br><br>
                <input type="submit" id="submitUser" class="button">
                <%--        <button type="submit" id="submitUser">subtnmitmme</button>--%>
            </form>
            <button onclick="hideForm('changeUsername', 'usernameBtn')" class="button">Cancel</button>
        </div>


        <%--Display success or failure message--%>
        <c:if test="${changeUsernameMessage != null}">
            <div>${changeUsernameMessage}</div>
        </c:if>
        <hr>


        <%--Button to change name--%>
        <h3>Name</h3>
        ${user.firstName} ${user.lastName} <br><br>
        <button onclick="showForm('changeName', 'nameBtn')" id="nameBtn">Change</button>

        <%--Form to change name--%>
        <div style="display: none" id="changeName">
            <form action="./ChangeName">
                New first name: <input type="text" name="firstName" required>
                <br> <br>
                New last name: <input type="text" name="lastName" required>
                <br><br>
                <input type="submit" class="button">
            </form>
            <button onclick="hideForm('changeName', 'nameBtn')" class="button">Cancel</button>
        </div>

        <%--Display success or failure message--%>
        <c:if test="${changeNameMessage != null}">
            <div>${changeNameMessage}</div>
        </c:if>
        <hr>
        <%--Button to change description--%>
        <h3>Description</h3>
        ${user.profile}<br><br>
        <button onclick="showForm('changeDesc', 'descBtn')" id="descBtn">Change</button>

        <%--Form to change description--%>
        <div style="display: none" id="changeDesc">
            <form action="./ChangeDesc">
                New description: <textarea rows="20" cols="10" name="desc" maxlength="1000" required> </textarea>
                <br><br>
                <input type="submit" class="button">
            </form>
            <button onclick="hideForm('changeDesc', 'descBtn')" class="button">Cancel</button>
        </div>

        <%--Display success or failure message--%>
        <c:if test="${changeDescMessage != null}">
            <div>${changeDescMessage}</div>
        </c:if>

        <hr>

        <%--Button to change password--%>
        <h3>Password</h3>
        <button onclick="showForm('changePassword', 'passwordBtn')" id="passwordBtn">Change</button>

        <%--Form to change password--%>
        <div style="display: none" id="changePassword">
            <form action="./ChangePassword" method="post">
                Current password: <input type="password" name="currentPassword" required>
                <br> <br>
                New password: <input type="password" name="newPassword" id="password" onkeyup='checkPassword()'
                                     required>
                <br> <br>
                Confirm new password: <input type="password" name="confirmNewPassword" id="confirmPassword"
                                             onkeyup='checkPassword()' required>
                <span id="message"></span><br> <br>
                <input type="submit" id="submitPassword" class="button">
            </form>
            <button onclick="hideForm('changePassword', 'passwordBtn')" class="button">Cancel</button>
        </div>

        <%--Display success or error message--%>
        <c:if test="${changePasswordMessage!= null}">
            <div>${changePasswordMessage}</div>
        </c:if>
<hr>
        <%--Button to upload avatar--%>
        <h3>Avatar</h3>
        <img src="${user.avatarURL}"><br>
        <button onclick="showForm('uploadAvatar', 'uploadAvatarBtn')" id="uploadAvatarBtn" >Upload avatar</button>

<%--Form to upload avatar--%>
<div style="display: none" id="uploadAvatar">
    <form action="./avatarUpload" method="POST" enctype="multipart/form-data">
        Upload new avatar: <input type="file" id="ownAvatarFile" name="newAvatar" accept="image/*">
        <span id="ownAvatarPic"></span>
        <br><br>
        <input type="submit">
        <br><br>
    </form>
    <button onclick="hideForm('uploadAvatar', 'uploadAvatarBtn')">Cancel</button>
</div>

        <%--Button to choose avatar from list--%>
        <br><br>
        <button onclick="showForm('chooseAvatar', 'chooseAvatarBtn')" id="chooseAvatarBtn">Select default avatar
        </button>

        <%--Button to choose avatar from list--%>
        <div style="display: none" id="chooseAvatar">
            <form action="./ChooseAvatar">
                <br> Select default avatar: <br><br>
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
                <br>
                <p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a
                        href="https://www.iconfinder.com/iconsets/business-avatar-1" target="_blank"><em>slack</em></a>
                </p>
                <input type="submit" class="button">
            </form>
            <button onclick="hideForm('chooseAvatar', 'chooseAvatarBtn')" class="button">Cancel</button>
        </div>

        <%--Display success or error message--%>
        <c:if test="${changeAvatarMessage!= null}">
            <div>${changeAvatarMessage}</div>
        </c:if>
        <hr>
        <%--Button to change blogname--%>
        <h3>Blog name</h3>
        ${user.blogName}<br><br>
        <button onclick="showForm('changeBlogName', 'blogNameBtn')" id="blogNameBtn" class="button">Change</button>

        <%--Form to change blogname--%>
        <div style="display: none" id="changeBlogName">
            <form action="./ChangeBlogName">
                New blog name: <input type="text" name="newBlogName" required>
                <br><br>
                <input type="submit">
            </form>
            <button onclick="hideForm('changeBlogName', 'blogNameBtn')" class="button">Cancel</button>

        </div>

        <%--Display success or error message--%>
        <c:if test="${changeBlognameMessage != null}">
            <div>${changeBlognameMessage}</div>
        </c:if>
        <hr>
        <%--Button to change date of birth--%>
        <h3>Date of Birth</h3>
        ${user.dateOfBirth}<br><br>
        <button onclick="showForm('changeDateOfBirth', 'dobBtn')" id="dobBtn" class="button">Change</button>

<%--Form to change date of birth--%>
<div style="display: none" id="changeDateOfBirth">
    <form action="./ChangeDateOfBirth">
        <input type="date" name="dob" required>
        <input type="submit">
    </form>
    <button onclick="hideForm('changeDateOfBirth', 'dobBtn')">Cancel</button>

        </div>

        <%--Display success or error message--%>
        <c:if test="${changeDOBMessage != null}">
            <div>${changeDOBMessage}</div>
        </c:if>
        <hr>
        <%--Button to delete account--%>
        <h3>Account</h3>
        <button onclick="showForm('deleteAccount', 'deleteBtn')" id="deleteBtn">Delete</button>

        <%--Form to delete account--%>
        <div style="display: none" id="deleteAccount">
            <form action="./DeleteAccount">
                This action will delete your account.
                <br><br>
                Enter password: <input type="password" name="password" required>
                <br> <br>
                <input type="submit" class="button">
            </form>
            <button onclick="hideForm('deleteAccount', 'deleteBtn')" class="button">Cancel</button>

        </div>

        <%--If the account could not be deleted, display error message--%>
        <c:if test="${deleteAccountMessage != null}">
            <div>${deleteAccountMessage}</div>
        </c:if>


    </div>
</header>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

</body>
</html>
