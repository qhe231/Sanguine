<%--
  Created by IntelliJ IDEA.
  User: jluo669
  Date: 28/01/2020
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>

 <%--Log in page--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>Login</title>
    <script src="login.js"></script>

    <jsp:include page="./WEB-INF/NavigationBar.jsp">
        <jsp:param name="user" value="${user}"/>
    </jsp:include>

</head>

<body>

<header class="page-header header container-fluid">
        <div class="content center heightFit">
            <h1 class="center">Welcome back!</h1> <br>

                <form action="./login" method="post">

                    <div class="row">
                        <div class="col-4 right"><label>Username: </label></div>
                        <div class="col-4 left"><input name="username" required></div>
                    </div>

                    <div class="row">
                        <div class="col-4 right"><label>Password: </label></div>
                        <div class="col-4 left"><input type="password" name="password" required></div>
                    </div>

                    <br>

                    <input type="submit" value="Login" class="Login center button">

                </form>

            <c:if test="${ErrorMessage != null}">
                <div class="center"> Error: ${ErrorMessage} <br>
                    Do you need to sign up? <a href="SignUp.jsp">Click here</a>
                </div>
            </c:if>
<br>
<div class="g-signin2" data-onsuccess="googleSignIn"></div>

</div>
</header>
</body>
</html>
