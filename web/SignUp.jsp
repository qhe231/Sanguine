<%@ page import="java.util.ArrayList" %>
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
    <script type="text/javascript" src="checkDescriptionLength.js"></script>


    <%--Sign up page--%>
    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>


</head>
<body>

<header class="page-header header container-fluid">

    <div id="contain" class="container">
        <div id="left">

            <h1 class="center">Sign Up</h1>
            <form action="./SignUp" method="post" enctype="multipart/form-data">
                <fieldset>
                    <br>
                    <div class="row">
                        <div class="col-md-2 col-12 rightText centerSm"><label>Blog Name: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input name="blogName" value="" required></div>
                    </div>
                    <br>


                    <div class="row">
                        <div class="col-md-2 col-12 centerSm rightText"><label>First Name: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input name="firstName" value="" required></div>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 col-12 centerSm rightText"><label>Last Name: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input name="lastName" value="" required></div>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 col-12 rightText centerSm"><label>User Name: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input name="userName" oninput="checkUserName()" id="userName" required></div>
                    </div>
<br>
                    <div class="row">
                        <div class="col-md-3 col-12 centerSm"><span id="userNameMessage" class="left centerSm"></span></div>
                    </div>

                    <br>

                    <div class="row">
                        <div class="col-md-2 rightText centerSm"><label>Password: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input type="password" name="password" oninput='checkPassword()'
                                                  id="password" required></div>
                    </div>

                    <div class="row">
                        <div class="col-md-2 col-12 centerSm rightText"><label>Confirm Password: </label></div>
                        <div class="col-md-3 col-12 centerSm"><input type="password" name="password" oninput='checkPassword()'
                                                  id="confirmPassword" required></div>

                    </div>
                    <div class="row"><div class="col-md- col-12 centerSm"><span id="message"></span></div></div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 col-12 rightText centerSm"><label>Date Of Birth: </label></div>
                        <div class="col-md-4 col-12 centerSm">
                            <input type="date" name="dob" required>
                        </div>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 rightText centerSm"><label>Profile: </label></div>
                        <div class="col-md-1 col-12 centerSm"><textarea name="profile" rows="4" cols="25" id="profileText" onkeyup='checkLength()'></textarea></div>
                    </div>
                    <div id="profilePrompt"></div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 col-12 centerSm rightText"><label>Avatar: </label></div>
                        <div class="col">
                            <div class="row">

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/1.png" class="avatar"><img
                                        src="images/avatars/1.png"></div>

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/2.png" class="avatar"><img
                                        src="images/avatars/2.png"></div>

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/3.png" class="avatar"><img
                                        src="images/avatars/3.png"></div>

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/4.png" class="avatar"><img
                                        src="images/avatars/4.png"></div>
                            </div>
                            <div class="row">
                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/5.png" class="avatar"><img
                                        src="images/avatars/5.png">
                                </div>

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/6.png" class="avatar"><img
                                        src="images/avatars/6.png"></div>

                                <div class="col-md-2 col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/7.png" class="avatar"><img
                                        src="images/avatars/7.png"></div>

                                <div class="col-md-2  col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/8.png" class="avatar"><img
                                        src="images/avatars/8.png"></div>
                            </div>

                            <div class="row">

                                <div class="col-md-2  col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/9.png" class="avatar"><img
                                        src="images/avatars/9.png"></div>

                                <div class="col-md-2  col-6 centerSm">
                                    <input type="radio" name="avatar" value="./images/avatars/10.png" class="avatar"><img
                                        src="images/avatars/10.png"></div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-2 col-12 centerSm"></div>
                        <div class="col-md-3 col-12 centerSm">
                        <input type="radio" name="avatar" value="own" class="avatar" id="ownAvatar"><span
                            id="ownAvatarPic"> Your Own Avatar</span></div>
                        <div class="col-md-4  col-12">
                        <input type="file" name="avatarUpload" id="ownAvatarFile" accept="image/*"></div>
                        <span id="changeAvatarMessage"></span>
                    </div>

                    <div class="row">
                        <div class="col-md-2 col-12 centerSm"></div>
                        <div class="col-12"><p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a
                                href="https://www.iconfinder.com/iconsets/business-avatar-1"
                                target="_blank"><em>slack</em></a></p></div>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 centerSm col-12"></div>
                        <div class="col"><input type="submit" id="submit" class="button"></div>
                    </div>

                </fieldset>
            </form>

            <c:if test="${error != null}">
                <div>${error}</div>
            </c:if>

        </div>
    </div>


</header>
</body>
</html>
