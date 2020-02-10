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

    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>
<body>

<header class="page-header header container-fluid">
    <div class="container">

        <c:choose>
            <c:when test="${user == null}">

                Please <a href="./login.jsp"> log in </a>to view account settings

            </c:when>
            <c:otherwise>

                <h1>Account settings</h1>

                <%--Button to change username--%>
                <h3>Username</h3>
                ${user.userName} <br><br>
                <button onclick="showForm('changeUsername', 'usernameBtn')" id="usernameBtn">Change</button>

                <%--Form to change username--%>
                <div style="display: none" id="changeUsername">
                    <form action="./ChangeUsername">
                        New username: <input type="text" name="newUsername" id="username" required>
                        <br><br>

                        <div class="row">
                            <div class="col">
                                <input type="submit" id="submitUser" class="button">
                                <button type="button" onclick="hideForm('changeUsername', 'usernameBtn')"
                                        class="button">
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <%--Display success or failure message--%>
                <c:if test="${changeUserNameMessage != null}">
                    <div>${changeUserNameMessage}</div>
                </c:if>
                <hr>


                <%--Button to change name--%>
                <h3>Name</h3>
                ${user.firstName} ${user.lastName} <br><br>
                <button onclick="showForm('changeName', 'nameBtn')" id="nameBtn">Change</button>

                <%--Form to change name--%>
                <div style="display: none" id="changeName">
                    <form action="./ChangeName">

                        <div class="row">
                            <div class="col">
                                New first name:
                            </div>
                            <input type="text" name="firstName" required>
                            <div class="col"></div>
                        </div>

                        <div class="row">
                            <div class="col">
                                New last name:
                            </div>
                            <input type="text" name="lastName" required>
                            <div class="col"></div>
                        </div>

                        <div class="row">
                            <div class="col">

                                <input type="submit" class="button">
                                <button type="button" onclick="hideForm('changeName', 'nameBtn')" class="button">
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </form>

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
                        <textarea rows="5" cols="50" name="desc" maxlength="1000" placeholder="New description"
                                  required> </textarea>
                        <br><br>

                        <div class="row">
                            <div class="col">

                                <input type="submit" class="button">

                                <button type="button" onclick="hideForm('changeDesc', 'descBtn')" class="button">
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <%--Display success or failure message--%>
                <c:if test="${changeDescMessage != null}">
                    <div>${changeDescMessage}</div>
                </c:if>

                <hr>


                <%--Button to change blogname--%>
                <h3>Blog name</h3>
                ${user.blogName}<br><br>
                <button onclick="showForm('changeBlogName', 'blogNameBtn')" id="blogNameBtn" class="button">Change
                </button>

                <%--Form to change blogname--%>
                <div style="display: none" id="changeBlogName">
                    <form action="./ChangeBlogName">
                        New blog name: <input type="text" name="newBlogName" required>
                        <br><br>

                        <div class="row">
                            <div class="col">
                                <input type="submit" class="button">

                                <button onclick="hideForm('changeBlogName', 'blogNameBtn')" class="button">Cancel
                                </button>
                            </div>
                        </div>
                    </form>
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

                        <div class="row">
                            <div class="col-2">
                                New DOB:
                            </div>
                            <div class="col">
                                <input type="date" name="dob" required>
                            </div>
                        </div>

                        <br>
                        <div class="row">
                            <div class="col">
                                <input type="submit" class="button">
                                <button onclick="hideForm('changeDateOfBirth', 'dobBtn')">Cancel</button>
                            </div>
                        </div>
                    </form>


                </div>

                <%--Display success or error message--%>
                <c:if test="${changeDOBMessage != null}">
                    <div>${changeDOBMessage}</div>
                </c:if>
                <hr>

                <h3>Theme</h3>
                ${user.theme} <br><br>

                <button onclick="showForm('changeTheme', 'themeBtn')" id="themeBtn">Change</button>

                <%--Form to change theme--%>
                <div style="display: none" id="changeTheme">
                    <form action="./ChangeTheme">

                        <div class="row">
                            <div class="col-md col-12">
                                <img src="images/themes/cropped/plant_crop.jpg" class="crop">
                                <input type="radio" name="theme" value="Botanical" class="theme"> Botanical<br> <br>
                                <a style="background-color:darkgrey;color:white;text-decoration:none;padding:4px 6px;font-family: Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px"
                                   href="https://unsplash.com/@anniespratt?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge"
                                   target="_blank" rel="noopener noreferrer"
                                   title="Download free do whatever you want high-resolution photos from Annie Spratt"><span
                                        style="display:inline-block;padding:2px 3px"><svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white"
                                        viewBox="0 0 32 32"><title>unsplash-logo</title><path
                                        d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span
                                        style="display:inline-block;padding:2px 3px">Annie Spratt</span></a> <br><br>

                            </div>

                            <div class="col-md col-12">
                                <img src="images/themes/cropped/beach_crop.jpg" class="crop">
                                <input type="radio" name="theme" value="Beach" class="theme"> Beach <br><br>
                                <a style="background-color:darkgrey;color:white;text-decoration:none;padding:4px 6px;font-family:Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px"
                                   href="https://unsplash.com/@fezbot2000?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge"
                                   target="_blank" rel="noopener noreferrer"
                                   title="Download free do whatever you want high-resolution photos from Fezbot2000"><span
                                        style="display:inline-block;padding:2px 3px"><svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white"
                                        viewBox="0 0 32 32"><title>unsplash-logo</title><path
                                        d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span
                                        style="display:inline-block;padding:2px 3px">Fezbot2000</span></a> <br>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md col-12">
                                <img src="images/themes/cropped/balloons_crop.jpg" class="crop">
                                <input type="radio" name="theme" value="Hot Air Balloons" class="theme"> Hot Air
                                Balloons <br><br>
                                <a style="background-color:darkgrey;color:white;text-decoration:none;padding:4px 6px;font-family: Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px"
                                   href="https://unsplash.com/@kajhinkson?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge"
                                   target="_blank" rel="noopener noreferrer"
                                   title="Download free do whatever you want high-resolution photos from Kyle Hinkson"><span
                                        style="display:inline-block;padding:2px 3px"><svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white"
                                        viewBox="0 0 32 32"><title>unsplash-logo</title><path
                                        d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span
                                        style="display:inline-block;padding:2px 3px">Kyle Hinkson</span></a><br>
                                <br></div>


                            <div class="col-md col-12">
                                <img src="images/themes/cropped/stationery_crop.jpg" class="crop">
                                <input type="radio" name="theme" value="Stationery" class="theme"> Stationery <br><br>
                                <a style="background-color:darkgrey;color:white;text-decoration:none;padding:4px 6px;font-family: Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px"
                                   href="https://unsplash.com/@joannakosinska?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge"
                                   target="_blank" rel="noopener noreferrer"
                                   title="Download free do whatever you want high-resolution photos from Joanna Kosinska"><span
                                        style="display:inline-block;padding:2px 3px"><svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white"
                                        viewBox="0 0 32 32"><title>unsplash-logo</title><path
                                        d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span
                                        style="display:inline-block;padding:2px 3px">Joanna Kosinska</span></a><br>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md col-12">
                                <img src="images/themes/cropped/snow_crop.jpg" class="crop">
                                <input type="radio" name="theme" value="Snow" class="theme"> Snow <br><br>
                                <a style="background-color:darkgrey;color:white;text-decoration:none;padding:4px 6px;font-family:Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px"
                                   href="https://unsplash.com/@paulfiedler?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge"
                                   target="_blank" rel="noopener noreferrer"
                                   title="Download free do whatever you want high-resolution photos from Paul Fiedler"><span
                                        style="display:inline-block;padding:2px 3px"><svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white"
                                        viewBox="0 0 32 32"><title>unsplash-logo</title><path
                                        d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span
                                        style="display:inline-block;padding:2px 3px">Paul Fiedler</span></a><br>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <input type="submit" id="submitTheme" class="button">
                                <button type="button" onclick="hideForm('changeTheme', 'themeBtn')" class="button">
                                    Cancel
                                </button>
                            </div>
                        </div>

                    </form>

                </div>

                <%--Display success or failure message--%>
                <c:if test="${changeThemeMessage != null}">
                    <div>${changeThemeMessage}</div>
                </c:if>
                <hr>


                <%--Button to upload avatar--%>
                <h3>Avatar</h3>
                <img src="${user.avatarURL}"><br> <br>
                <button onclick="showForm('uploadAvatar', 'uploadAvatarBtn')" id="uploadAvatarBtn">Upload avatar
                </button>

                <%--Form to upload avatar--%>
                <div style="display: none" id="uploadAvatar">
                    <form action="./avatarUpload" method="POST" enctype="multipart/form-data">
                        Upload new avatar: <input type="file" id="ownAvatarFile" name="newAvatar" accept="image/*">
                        <span id="ownAvatarPic"></span>
                        <br><br>

                        <div class="row">
                            <div class="col">
                                <input type="submit" class="button">
                                <button class="button" type="button"
                                        onclick="hideForm('uploadAvatar', 'uploadAvatarBtn')">Cancel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <%--Button to choose avatar from list--%>
                <br><br>
                <button onclick="showForm('chooseAvatar', 'chooseAvatarBtn')" id="chooseAvatarBtn">Select default avatar
                </button>

                <%--Button to choose avatar from list--%>
                <div style="display: none" id="chooseAvatar">
                    <form action="./ChooseAvatar">
                        <br> Select default avatar: <br><br>

                        <div class="row">

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/1.png" class="avatar"><img
                                    src="./images/avatars/1.png"></div>

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/2.png" class="avatar"><img
                                    src="./images/avatars/2.png"></div>

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/3.png" class="avatar"><img
                                    src="./images/avatars/3.png"></div>
                        </div>
                        <div class="row">
                            <div class="col-2">

                                <input type="radio" name="avatar" value="./images/avatars/4.png" class="avatar"><img
                                    src="./images/avatars/4.png"></div>


                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/5.png" class="avatar"><img
                                    src="./images/avatars/5.png">
                            </div>

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/6.png" class="avatar"><img
                                    src="./images/avatars/6.png"></div>
                        </div>

                        <div class="row">
                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/7.png" class="avatar"><img
                                    src="./images/avatars/7.png"></div>

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/8.png" class="avatar"><img
                                    src="./images/avatars/8.png"></div>

                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/9.png" class="avatar"><img
                                    src="./images/avatars/9.png"></div>
                        </div>

                        <div class="row">
                            <div class="col-2">
                                <input type="radio" name="avatar" value="./images/avatars/10.png" class="avatar"><img
                                    src="./images/avatars/10.png"></div>
                        </div>
                    </form>

                    <br>
                    <p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a
                            href="https://www.iconfinder.com/iconsets/business-avatar-1"
                            target="_blank"><em>slack</em></a>
                    </p>

                    <div class="row">
                        <div class="col">
                            <input type="submit" class="button">

                            <button type="button" onclick="hideForm('chooseAvatar', 'chooseAvatarBtn')"
                                    class="button">Cancel
                            </button>
                        </div>
                    </div>
                    </form>
                </div>

                <%--Display success or error message--%>
                <c:if test="${changeAvatarMessage!= null}">
                    <div>${changeAvatarMessage}</div>
                </c:if>
                <hr>


                <%--Button to change password--%>
                <h3>Password</h3>
                <button onclick="showForm('changePassword', 'passwordBtn')" id="passwordBtn">Change</button>

                <%--Form to change password--%>
                <div style="display: none" id="changePassword">
                    <form action="./ChangePassword" method="post">

                        <div class="row">
                            <div class="col-3">
                                Current password:
                            </div>
                            <div class="col"><input type="password" name="currentPassword" required></div>
                        </div>

                        <br>

                        <div class="row">
                            <div class="col-3">
                                New password:
                            </div>
                            <div class="col">
                                <input type="password" name="newPassword" id="password" onkeyup='checkPassword()'
                                       required>
                            </div>
                        </div>

                        <br>

                        <div class="row">
                            <div class="col-3">
                                Confirm new password:
                            </div>
                            <div class="col">
                                <input type="password" name="confirmNewPassword" id="confirmPassword"
                                       onkeyup='checkPassword()' required>
                                <span id="message"></span></div>
                        </div>

                        <br>

                        <div class="row">
                            <div class="col">
                                <input type="submit" id="submitPassword" class="button">

                                <button type="button" onclick="hideForm('changePassword', 'passwordBtn')"
                                        class="button">Cancel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <%--Display success or error message--%>
                <c:if test="${changePasswordMessage!= null}">
                    <div>${changePasswordMessage}</div>
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

                        <div class="row">
                            <div class="col">
                                <input type="submit" class="button">

                                <button type="button" onclick="hideForm('deleteAccount', 'deleteBtn')" class="button">
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <%--If the account could not be deleted, display error message--%>
                <c:if test="${deleteAccountMessage != null}">
                    <div>${deleteAccountMessage}</div>
                </c:if>
            </c:otherwise>
        </c:choose>

    </div>
</header>


</body>
</html>
