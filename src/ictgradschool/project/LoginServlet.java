package ictgradschool.project;

import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserAuthenticationDAO;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.PasswordUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TEST");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isPassword = false;

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, username);
            if (ua == null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);

                return;
            } else {
                char[] passwordChar = password.toCharArray();
                byte[] salt = PasswordUtil.base64Decode(ua.getSalt());
                int hashNum = ua.getHashNum();
                byte[] expectedHash = PasswordUtil.base64Decode(ua.getHashedPassword());
                isPassword = PasswordUtil.isExpectedPassword(passwordChar, salt, hashNum, expectedHash);
            }


            if (isPassword) {
                UserInfo ui = UserInfoDAO.getUserInfoById(conn, ua.getUserId());
                req.getSession().setAttribute("user", ui);
                req.setAttribute("user", ui);
                //req.setAttribute("ownerId", ui.getUserId());

                resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());/*
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/userHomePage?ownerId=" + ui.getUserId());
                requestDispatcher.forward(req, resp);*/

            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
