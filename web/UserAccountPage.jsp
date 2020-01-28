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


    <style>

        <%--Style avatar thumbnail--%>
        .avatar {
            width: 100px;
            height: 100px;
        }

        /*When hovered over, thumbnail enlarges*/
        .avatar:hover {
            width:200px;
            height: 200px;
        }

    </style>

    <script type="text/javascript">

        //Make the form to change username visible, hide change button
        function showChangeUsername() {
            const div = document.getElementById("changeUsername");
            div.style.display = "initial";

            const btn = document.getElementById("usernameBtn");
            btn.style.display = "none";
        }

        //Make the form to change first name visible, hide change button
        function showChangeFirstname() {
            const div = document.getElementById("changeFirstname");
            div.style.display = "initial";

            const btn = document.getElementById("firstnameBtn");
            btn.style.display = "none";
        }

        //Make the form to change last name visible, hide change button
        function showChangeLastname() {
            const div = document.getElementById("changeLastname");
            div.style.display = "initial";

            const btn = document.getElementById("lastnameBtn");
            btn.style.display = "none";
        }

        //Make the form to change description visible, hide change button
        function showChangeDesc() {
            const div = document.getElementById("changeDesc");
            div.style.display = "initial";

            const btn = document.getElementById("descBtn");
            btn.style.display = "none";
        }

            //Make the form to change password visible, hide change button
        function showChangePassword() {
            const div = document.getElementById("changePassword");
            div.style.display = "initial";

            const btn = document.getElementById("passwordBtn");
            btn.style.display = "none";
        }

        //Make the form to upload new avatar visible, hide change button
        function showUploadAvatar() {
            const div = document.getElementById("uploadAvatar");
            div.style.display = "initial";

            const btn = document.getElementById("uploadAvatarBtn");
            btn.style.display = "none";
        }

        //Make the form to choose avatar from list visible, hide change button
        function showChooseAvatar() {
            const div = document.getElementById("chooseAvatar");
            div.style.display = "initial";

            const btn = document.getElementById("chooseAvatarBtn");
            btn.style.display = "none";

            const uploadBtn = document.getElementById("uploadAvatarBtn");
            uploadBtn.style.display = "none";
        }

        //Make the form to change blogname visible, hide change button
        function showChangeBlogname() {
            const div = document.getElementById("changeBlogname");
            div.style.display = "initial";

            const btn = document.getElementById("blognameBtn");
            btn.style.display = "none";
        }

        //Make the form to delete account visible, hide delete button
         function showDeleteAccount() {
            const div = document.getElementById("deleteAccount");
            div.style.display = "initial";

            const btn = document.getElementById("deleteBtn");
            btn.style.display = "none";
        }

    </script>
</head>
<body>

<h1>Account settings</h1>

<%--Button to change username--%>
<h2>Username</h2>
${username} <%-- TODO display username--%>
<button onclick="showChangeUsername()" id="usernameBtn">Change</button>

<%--Form to change username--%>
<div style="display: none" id="changeUsername">
    <form action="./ChangeUsername">
        New username: <input type="text" name="newname" placeholder="20 characters or less" maxlength="20">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--If the username could not be changed, display an error message--%>
<c:if test="${changeUsernameError != null}">
    <div>Error changing username: ${changeUsernameError}</div>
</c:if>

<%--Button to change first name--%>
<h2>First Name</h2>
<button onclick="showChangeFirstname()" id="firstnameBtn">Change</button>

<%--Form to change first name--%>
<div style="display: none" id="changeFirstname">
    <form action="./ChangeFirstname">
        New first name: <input type="text" name="firstname">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Button to change last name--%>
<h2>Last Name</h2>
<button onclick="showChangeLastname()" id="lastnameBtn">Change</button>

<%--Form to change last name--%>
<div style="display: none" id="changeLastname">
    <form action="./ChangeLastname">
        New last name: <input type="text" name="lastname">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Button to change description--%>
<h2>Description</h2>
<button onclick="showChangeDesc()" id="descBtn">Change</button>

<%--Form to change description--%>
<div style="display: none" id="changeDesc">
    <form action="./ChangeDesc">
        New description: <textarea rows="5"  name="desc"> </textarea>
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Button to change password--%>
<h2>Password</h2>
<button onclick="showChangePassword()" id="passwordBtn">Change</button>

<%--Form to change password--%>
<div style="display: none" id="changePassword">
    <form action="./ChangePassword">
        <fieldset>
        Current password: <input type="password" name="currentpassword">
        <br> <br>
        New password: <input type="password" name="newpassword">
        <br> <br>
        <input type="submit">
        </fieldset>
    </form>
</div>

<%--If the entered password does not match the current passwrod, display an error message--%>
<c:if test="${changePasswordMessage!= null}">
    <div>${changePasswordMessage}</div>
</c:if>

<%--Button to upload avatar--%>
<h2>Avatar</h2>
<button onclick="showUploadAvatar()" id="uploadAvatarBtn">Upload avatar</button>

<%--Form to upload avatar--%>
<div style="display: none" id="uploadAvatar">
    <form action="./"> <%--TODO--%>
        Upload new avatar: <input type="file" name="newavatar" accept="image/*">
        <br><br>
        <input type="submit">
        <br><br>
    </form>
</div>

<%--Button to select avatar from list--%>
<br><br>
<button onclick="showChooseAvatar()" id="chooseAvatarBtn">Select default avatar</button>

<%--Button to choose avatar from list--%>
<div style="display: none" id="chooseAvatar">
    <form action="./"> <%--TODO--%>
        <br> Select default avatar: <br><br>
        <img src="./images/lion.jpg" alt="Lion" class="avatar">
        <input type="radio" name="avatar" value="lion"> Lion <br><br>
        <img src="./images/cat.jpg" alt="Cat" class="avatar">
        <input type="radio" name="avatar" value="cat"> Cat <br><br>
        <br>
        <input type="submit">
    </form>
</div>

<%--Button to change blogname--%>
<h2>Blogname</h2>
<button onclick="showChangeBlogname()" id="blognameBtn">Change</button>

<%--Form to change blogname--%>
<div style="display: none" id="changeBlogname">
    <form action="./ChangeBlogname">
        New blogname: <input type="text" name="newblogname">
        <br><br>
        <input type="submit">
    </form>
</div>

<c:if test="${changeBlognameMessage != null}">
    <div>${changeBlognameMessage}</div>
</c:if>

<h2>Account</h2>
<button onclick="showDeleteAccount()" id="deleteBtn">Delete</button>

<div style="display: none" id="deleteAccount">
    <form action="./DeleteAccount">
        This action will delete your account.
        <br><br>
        Enter password: <input type="password" name="password">
        <br> <br>
        <input type="submit">
    </form>
</div>

<c:if test="${deleteAccountMessage != null}">
    <div>${deleteAccountMessage}</div>
</c:if>

</body>
</html>
