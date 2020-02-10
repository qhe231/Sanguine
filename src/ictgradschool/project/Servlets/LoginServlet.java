package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserAuthenticationDAO;
import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    /**
     * LoginServlet is the back end for a standard login. Username and password are verified and, if correct,
     * user's session is initiated.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isPassword;

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, username);
            if (ua == null) {
//If the username does not exist, set error message:
                String message = "Username not found. ";
                req.setAttribute("ErrorMessage", message);

                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
// If the username exists, check the password
            } else {
                char[] passwordChar = password.toCharArray();
                byte[] salt = PasswordUtil.base64Decode(ua.getSalt());
                int hashNum = ua.getHashNum();
                byte[] expectedHash = PasswordUtil.base64Decode(ua.getHashedPassword());
                isPassword = PasswordUtil.isExpectedPassword(passwordChar, salt, hashNum, expectedHash);
            }

// If password is correct, log in user and direct to homepage
            if (isPassword) {
                UserInfo ui = UserInfoDAO.getUserInfoById(conn, ua.getUserId());
                req.getSession().setAttribute("user", ui);
                req.setAttribute("user", ui);

                resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());

// If password is incorrect, set the error message
            } else {
                String message = "Incorrect password. ";
                req.setAttribute("ErrorMessage", message);

                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
