<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ekoe938
  Date: 24/01/2020
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

</head>
<body>

<jsp:include page="./WEB-INF/NavigationBar.jsp">
    <jsp:param name="user" value="${user}"/>
</jsp:include>
<br>

<h1>Account settings</h1>

<%--Button to change username--%>
<h2>Username</h2>
${user.userName} <br><br>
<button onclick="showForm('changeUsername', 'usernameBtn')" id="usernameBtn">Change</button>

<%--Form to change username--%>
<div style="display: none" id="changeUsername">
    <form action="./ChangeUsername">
        New username: <input type="text" name="newName" placeholder="20 characters or less" maxlength="20" onkeyup="checkUserName()" id="userName" required>
        <span id="userNameMessage"></span><br><br>
        <input type="submit" id="submitUser">
<%--        <button type="submit" id="submitUser">subtnmitmme</button>--%>
    </form>
    <button onclick="hideForm('changeUsername', 'usernameBtn')">Cancel</button>
</div>

<%--Display success or failure message--%>
<c:if test="${changeUsernameMessage != null}">
    <div>${changeUsernameMessage}</div>
</c:if>



<%--Button to change name--%>
<h2>Name</h2>
${user.firstName} ${user.lastName} <br><br>
<button onclick="showForm('changeName', 'nameBtn')" id="nameBtn">Change</button>

<%--Form to change name--%>
<div style="display: none" id="changeName">
    <form action="./ChangeName">
        New first name: <input type="text" name="firstName" required>
        <br> <br>
        New last name: <input type="text" name="lastName" required>
        <br><br>
        <input type="submit">
    </form>
    <button onclick="hideForm('changeName', 'nameBtn')">Cancel</button>
</div>

<%--Display success or failure message--%>
<c:if test="${changeNameMessage != null}">
    <div>${changeNameMessage}</div>
</c:if>

<%--Button to change description--%>
<h2>Description</h2>
${user.profile}<br><br>
<button onclick="showForm('changeDesc', 'descBtn')" id="descBtn">Change</button>

<%--Form to change description--%>
<div style="display: none" id="changeDesc">
    <form action="./ChangeDesc">
        New description: <textarea rows="5" name="desc" maxlength="1000" required> </textarea>
        <br><br>
        <input type="submit">
    </form>
    <button onclick="hideForm('changeDesc', 'descBtn')">Cancel</button>
</div>

<%--Display success or failure message--%>
<c:if test="${changeDescMessage != null}">
    <div>${changeDescMessage}</div>
</c:if>

<%--Button to change password--%>
<h2>Password</h2>
<button onclick="showForm('changePassword', 'passwordBtn')" id="passwordBtn">Change</button>

<%--Form to change password--%>
<div style="display: none" id="changePassword">
    <form action="./ChangePassword" method="post">
        Current password: <input type="password" name="currentPassword" required>
        <br> <br>
        New password: <input type="password" name="newPassword" id="password" onkeyup='checkPassword()' required>
        <br> <br>
        Confirm new password: <input type="password" name="confirmNewPassword" id="confirmPassword" onkeyup='checkPassword()' required>
        <span id="message"></span><br> <br>
        <input type="submit" id="submitPassword">
    </form>
    <button onclick="hideForm('changePassword', 'passwordBtn')">Cancel</button>
</div>

<%--Display success or error message--%>
<c:if test="${changePasswordMessage!= null}">
    <div>${changePasswordMessage}</div>
</c:if>

<%--Button to upload avatar--%>
<h2>Avatar</h2>
<img src="${user.avatarURL}"><br>
<button onclick="showForm('uploadAvatar', 'uploadAvatarBtn')" id="uploadAvatarBtn">Upload avatar</button>

<%--Form to upload avatar--%>
<div style="display: none" id="uploadAvatar">
    <form action="./imageUpload" method="POST" enctype="multipart/form-data">
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
<button onclick="showForm('chooseAvatar', 'chooseAvatarBtn')" id="chooseAvatarBtn">Select default avatar</button>

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
        <p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a href="https://www.iconfinder.com/iconsets/business-avatar-1" target="_blank"><em>slack</em></a></p>
        <input type="submit">
    </form>
    <button onclick="hideForm('chooseAvatar', 'chooseAvatarBtn')">Cancel</button>
</div>

<%--Display success or error message--%>
<c:if test="${changeAvatarMessage!= null}">
    <div>${changeAvatarMessage}</div>
</c:if>

<%--Button to change blogname--%>
<h2>Blog name</h2>
${user.blogName}<br><br>
<button onclick="showForm('changeBlogName', 'blogNameBtn')" id="blogNameBtn">Change</button>

<%--Form to change blogname--%>
<div style="display: none" id="changeBlogName">
    <form action="./ChangeBlogName">
        New blog name: <input type="text" name="newBlogName" required>
        <br><br>
        <input type="submit">
    </form>
    <button onclick="hideForm('changeBlogName', 'blogNameBtn')">Cancel</button>

</div>

<%--Display success or error message--%>
<c:if test="${changeBlognameMessage != null}">
    <div>${changeBlognameMessage}</div>
</c:if>

<%--Button to change date of birth--%>
<h2>Date of Birth</h2>
${user.dateOfBirth}<br><br>
<button onclick="showForm('changeDateOfBirth', 'dobBtn')" id="dobBtn">Change</button>

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

<%--Button to delete account--%>
<h2>Account</h2>
<button onclick="showForm('deleteAccount', 'deleteBtn')" id="deleteBtn">Delete</button>

<%--Form to delete account--%>
<div style="display: none" id="deleteAccount">
    <form action="./DeleteAccount">
        This action will delete your account.
        <br><br>
        Enter password: <input type="password" name="password" required>
        <br> <br>
        <input type="submit">
    </form>
    <button onclick="hideForm('deleteAccount', 'deleteBtn')">Cancel</button>

</div>

<%--If the account could not be deleted, display error message--%>
<c:if test="${deleteAccountMessage != null}">
    <div>${deleteAccountMessage}</div>
</c:if>

</body>
</html>
