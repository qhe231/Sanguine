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

        /*Style avatar thumbnail*/
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

        // Make form visible, hide button
        function showForm(formDivId, btnId) {
            const formDiv = document.getElementById(formDivId);
            formDiv.style.display = "initial";

            const btn = document.getElementById(btnId);
            btn.style.display = "none";
        }

    </script>
</head>
<body>

<h1>Account settings</h1>

<%--Button to change username--%>
<h2>Username</h2>
${userName}
<button onclick="showForm('changeUsername', 'usernameBtn')" id="usernameBtn">Change</button>

<%--Form to change username--%>
<div style="display: none" id="changeUsername">
    <form action="./ChangeUsername">
        New username: <input type="text" name="newName" placeholder="20 characters or less" maxlength="20">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Display success or failure message--%>
<c:if test="${changeUsernameMessage != null}">
    <div>${changeUsernameMessage}</div>
</c:if>

<%--Button to change name--%>
<h2>Name</h2>
${firstName} ${lastName}
<button onclick="showForm('changeName', 'nameBtn')" id="nameBtn">Change</button>

<%--Form to change name--%>
<div style="display: none" id="changeName">
    <form action="./ChangeName">
        New first name: <input type="text" name="firstName">
        <br> <br>
        New last name: <input type="text" name="lastName">
        <br><br>
        <input type="submit">
    </form>
</div>

<%--Display success or failure message--%>
<c:if test="${changeNameMessage != null}">
    <div>${changeNameMessage}</div>
</c:if>

<%--Button to change description--%>
<h2>Description</h2>
${description}
<button onclick="showForm('changeDesc', 'descBtn')" id="descBtn">Change</button>

<%--Form to change description--%>
<div style="display: none" id="changeDesc">
    <form action="./ChangeDesc">
        New description: <textarea rows="5"  name="desc"> </textarea>
        <br><br>
        <input type="submit">
    </form>
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
    <form action="./ChangePassword">
        Current password: <input type="password" name="currentPassword">
        <br> <br>
        New password: <input type="password" name="newPassword">
        <br> <br>
        <input type="submit">
    </form>
</div>

<%--If the entered password does not match the current passwrod, display an error message--%>
<c:if test="${changePasswordMessage!= null}">
    <div>${changePasswordMessage}</div>
</c:if>

<%--Button to upload avatar--%>
<h2>Avatar</h2>
<button onclick="showForm('uploadAvatar', 'uploadAvatarBtn')" id="uploadAvatarBtn">Upload avatar</button>

<%--Form to upload avatar--%>
<div style="display: none" id="uploadAvatar">
    <form action="./"> <%--TODO--%>
        Upload new avatar: <input type="file" name="newAvatar" accept="image/*">
        <br><br>
        <input type="submit">
        <br><br>
    </form>
</div>

<%--Button to select avatar from list--%>
<br><br>
<button onclick="showForm('chooseAvatar', 'chooseAvatarBtn')" id="chooseAvatarBtn">Select default avatar</button>

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
<h2>Blog name</h2>
<button onclick="showForm('changeBlogName', 'blogNameBtn')" id="blogNameBtn">Change</button>

<%--Form to change blogname--%>
<div style="display: none" id="changeBlogName">
    <form action="./ChangeBlogName">
        New blog name: <input type="text" name="newBlogName">
        <br><br>
        <input type="submit">
    </form>
</div>

<c:if test="${changeBlognameMessage != null}">
    <div>${changeBlognameMessage}</div>
</c:if>

<h2>Date of Birth</h2>
${DOB}

<div style="display: none" id="changeDOB">
<select name="dob-day" id="dob-day">
    <option value="">Day</option>
    <option value="">---</option>
    <option value="01">01</option>
    <option value="02">02</option>
    <option value="03">03</option>
    <option value="04">04</option>
    <option value="05">05</option>
    <option value="06">06</option>
    <option value="07">07</option>
    <option value="08">08</option>
    <option value="09">09</option>
    <option value="10">10</option>
    <option value="11">11</option>
    <option value="12">12</option>
    <option value="13">13</option>
    <option value="14">14</option>
    <option value="15">15</option>
    <option value="16">16</option>
    <option value="17">17</option>
    <option value="18">18</option>
    <option value="19">19</option>
    <option value="20">20</option>
    <option value="21">21</option>
    <option value="22">22</option>
    <option value="23">23</option>
    <option value="24">24</option>
    <option value="25">25</option>
    <option value="26">26</option>
    <option value="27">27</option>
    <option value="28">28</option>
    <option value="29">29</option>
    <option value="30">30</option>
    <option value="31">31</option>
</select>
<select name="dob-month" id="dob-month">
    <option value="">Month</option>
    <option value="">-----</option>
    <option value="01">January</option>
    <option value="02">February</option>
    <option value="03">March</option>
    <option value="04">April</option>
    <option value="05">May</option>
    <option value="06">June</option>
    <option value="07">July</option>
    <option value="08">August</option>
    <option value="09">September</option>
    <option value="10">October</option>
    <option value="11">November</option>
    <option value="12">December</option>
</select>
<select name="dob-year" id="dob-year">
    <option value="">Year</option>
    <option value="">----</option>
    <option value="2012">2012</option>
    <option value="2011">2011</option>
    <option value="2010">2010</option>
    <option value="2009">2009</option>
    <option value="2008">2008</option>
    <option value="2007">2007</option>
    <option value="2006">2006</option>
    <option value="2005">2005</option>
    <option value="2004">2004</option>
    <option value="2003">2003</option>
    <option value="2002">2002</option>
    <option value="2001">2001</option>
    <option value="2000">2000</option>
    <option value="1999">1999</option>
    <option value="1998">1998</option>
    <option value="1997">1997</option>
    <option value="1996">1996</option>
    <option value="1995">1995</option>
    <option value="1994">1994</option>
    <option value="1993">1993</option>
    <option value="1992">1992</option>
    <option value="1991">1991</option>
    <option value="1990">1990</option>
    <option value="1989">1989</option>
    <option value="1988">1988</option>
    <option value="1987">1987</option>
    <option value="1986">1986</option>
    <option value="1985">1985</option>
    <option value="1984">1984</option>
    <option value="1983">1983</option>
    <option value="1982">1982</option>
    <option value="1981">1981</option>
    <option value="1980">1980</option>
    <option value="1979">1979</option>
    <option value="1978">1978</option>
    <option value="1977">1977</option>
    <option value="1976">1976</option>
    <option value="1975">1975</option>
    <option value="1974">1974</option>
    <option value="1973">1973</option>
    <option value="1972">1972</option>
    <option value="1971">1971</option>
    <option value="1970">1970</option>
    <option value="1969">1969</option>
    <option value="1968">1968</option>
    <option value="1967">1967</option>
    <option value="1966">1966</option>
    <option value="1965">1965</option>
    <option value="1964">1964</option>
    <option value="1963">1963</option>
    <option value="1962">1962</option>
    <option value="1961">1961</option>
    <option value="1960">1960</option>
    <option value="1959">1959</option>
    <option value="1958">1958</option>
    <option value="1957">1957</option>
    <option value="1956">1956</option>
    <option value="1955">1955</option>
    <option value="1954">1954</option>
    <option value="1953">1953</option>
    <option value="1952">1952</option>
    <option value="1951">1951</option>
    <option value="1950">1950</option>
    <option value="1949">1949</option>
    <option value="1948">1948</option>
    <option value="1947">1947</option>
    <option value="1946">1946</option>
    <option value="1945">1945</option>
    <option value="1944">1944</option>
    <option value="1943">1943</option>
    <option value="1942">1942</option>
    <option value="1941">1941</option>
    <option value="1940">1940</option>
    <option value="1939">1939</option>
    <option value="1938">1938</option>
    <option value="1937">1937</option>
    <option value="1936">1936</option>
    <option value="1935">1935</option>
    <option value="1934">1934</option>
    <option value="1933">1933</option>
    <option value="1932">1932</option>
    <option value="1931">1931</option>
    <option value="1930">1930</option>
    <option value="1929">1929</option>
    <option value="1928">1928</option>
    <option value="1927">1927</option>
    <option value="1926">1926</option>
    <option value="1925">1925</option>
    <option value="1924">1924</option>
    <option value="1923">1923</option>
    <option value="1922">1922</option>
    <option value="1921">1921</option>
    <option value="1920">1920</option>
    <option value="1919">1919</option>
    <option value="1918">1918</option>
    <option value="1917">1917</option>
    <option value="1916">1916</option>
    <option value="1915">1915</option>
    <option value="1914">1914</option>
    <option value="1913">1913</option>
    <option value="1912">1912</option>
    <option value="1911">1911</option>
    <option value="1910">1910</option>
    <option value="1909">1909</option>
    <option value="1908">1908</option>
    <option value="1907">1907</option>
    <option value="1906">1906</option>
    <option value="1905">1905</option>
    <option value="1904">1904</option>
    <option value="1903">1903</option>
    <option value="1901">1901</option>
    <option value="1900">1900</option>
</select>
</div>

<h2>Account</h2>
<button onclick="showForm('deleteAccount', 'deleteBtn')" id="deleteBtn">Delete</button>

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
