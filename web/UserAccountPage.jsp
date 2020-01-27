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
    <script type="text/javascript">

        //Make the form to change username visible, hide change button
        async function showChangeUsername() {
            const div = document.getElementById("changeUsername");
            div.style.display = "initial";

            const btn = document.getElementById("usernameBtn");
            btn.style.display = "none";
        }

        //Make the form to change password visible, hide change button
        async function showChangePassword() {
            const div = document.getElementById("changePassword");
            div.style.display = "initial";

            const btn = document.getElementById("passwordBtn");
            btn.style.display = "none";

        }

        //Make the form to change avatar visible, hide change button
        async function showChangeAvatar() {
            const div = document.getElementById("changeAvatar");
            div.style.display = "initial";

            const btn = document.getElementById("avatarBtn");
            btn.style.display = "none";
        }

        //Make the form to change blogname visible, hide change button
        async function showChangeBlogname() {
            const div = document.getElementById("changeBlogname");
            div.style.display = "initial";

            const btn = document.getElementById("blognameBtn");
            btn.style.display = "none";
        }

        //Make the form to delete account visible, hide delete button
        async function showDeleteAccount() {
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
Kitty123 <%-- dummy username--%>
<br><br>
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

<%--Button to change avatar--%>
<h2>Avatar</h2>
<button onclick="showChangeAvatar()" id="avatarBtn">Change</button>

<div style="display: none" id="changeAvatar">
    <form action="./"> <%--TODO--%>

        Upload new avatar: <input type="file" name="newavatar" accept="image/*">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Button to change blogname--%>
<h2>Blogname</h2>
<button onclick="showChangeBlogname()" id="blognameBtn">Change</button>

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
