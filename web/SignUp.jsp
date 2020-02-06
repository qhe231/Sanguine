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

    <c:choose>
        <c:when test="${user.getTheme() == 'Snow'}">
            <link rel="stylesheet" type="text/css" href="./css/Snow.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Hot Air Balloons'}">
            <link rel="stylesheet" type="text/css" href="./css/HotAirBalloons.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Beach'}">
            <link rel="stylesheet" type="text/css" href="./css/Beach.css">
        </c:when>
        <c:when test="${user.getTheme() == 'Stationery'}">
            <link rel="stylesheet" type="text/css" href="./css/Stationery.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="./css/Botanical.css">
        </c:otherwise>
    </c:choose>


<%--Sign up page--%>
    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>



</head>
<body>

<header class="page-header header container-fluid">

    <div id="contain" class="container heightVh">
        <div id="left">

            <h1 class="center">Sign Up</h1>
            <form action="./SignUp" method="post" enctype="multipart/form-data">
                <fieldset>
                    <br>
                    <div class="row">
                        <div class="col-2 rightText"><label>Blog Name: </label></div>
                        <div class="col-3"><input name="blogName" value="" required></div>
                    </div><br>



                    <div class="row">
                        <div class="col-2 rightText"><label>First Name: </label></div>
                        <div class="col-3"><input name="firstName" value="" required></div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>Last Name: </label></div>
                        <div class="col-3"><input name="lastName" value="" required></div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>User Name: </label></div>
                        <div class="col-3"><input name="userName" onblur="checkUserName()" id="userName" required></div>
                        <div class="col-3"> <span id="userNameMessage" class="left"></span></div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>Password: </label></div>
                        <div class="col-3"><input type="password" name="password" onkeyup='checkPassword()' id="password" required></div>
                    </div>

                    <div class="row">
                        <div class="col-2 rightText"><label>Confirm Password: </label></div>
                        <div class="col-3"><input type="password" name="password" onkeyup='checkPassword()'
                                                  id="confirmPassword" required></div>
                        <div class="col-3"><span id="message"></span></div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>Date Of Birth: </label></div>
                        <div class="col-4">
                            <input type="date" name="dob" required>
                        </div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>Profile: </label></div>
                        <div class="col-1"><textarea name="profile" value="" rows="4" cols="40"></textarea></div>
                    </div><br>

                    <div class="row">
                        <div class="col-2 rightText"><label>Avatar: </label></div>
                        <div class="col">
                            <input type="radio" name="avatar" value="./images/1.png" class="avatar"><img
                                src="./images/1.png">
                            <input type="radio" name="avatar" value="./images/2.png" class="avatar"><img
                                src="./images/2.png">
                            <input type="radio" name="avatar" value="./images/3.png" class="avatar"><img
                                src="./images/3.png">
                            <input type="radio" name="avatar" value="./images/4.png" class="avatar"><img
                                src="./images/4.png">
                            <input type="radio" name="avatar" value="./images/5.png" class="avatar"><img
                                src="./images/5.png">
                            <input type="radio" name="avatar" value="./images/6.png" class="avatar"><img
                                src="./images/6.png">
                            <input type="radio" name="avatar" value="./images/7.png" class="avatar"><img
                                src="./images/7.png">
                            <input type="radio" name="avatar" value="./images/8.png" class="avatar"><img
                                src="./images/8.png">
                            <input type="radio" name="avatar" value="./images/9.png" class="avatar"><img
                                src="./images/9.png">
                            <input type="radio" name="avatar" value="./images/10.png" class="avatar"><img
                                src="./images/10.png">
                        </div>
                    </div><br>

                     <input type="radio" name="avatar" value="own" class="avatar" id="ownAvatar"><span
                id="ownAvatarPic">Your Own Avatar</span>
            <input type="file" id="ownAvatarFile" accept="image/*">

                    <span id="changeAvatarMessage"></span>

                    <div class="row">
                        <div class="col-2"></div>
                        <div class="col"><p>These predefined avatars are from <strong>Hopnguyen Mr</strong> at <a
                                href="https://www.iconfinder.com/iconsets/business-avatar-1"
                                target="_blank"><em>slack</em></a></p> </div>
                    </div><br>

                    <div class="row">
                        <div class="col-2"></div>
                        <div class="col"> <input type="submit" id="submit" class="button"> </div>
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
