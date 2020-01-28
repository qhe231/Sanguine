package ictgradschool.project.util;

import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserAuthenticationDAO;

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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isPassword = false;

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserAuthentication ua =  UserAuthenticationDAO.getUserAuthenticationByUserName(conn,username);

//            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_authentication WHERE userName = ?")) {
//                stmt.setString(1, username);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    if (rs.next()) {
//                        char[] passwordChar = password.toCharArray();
//                        byte[] salt = rs.getString(4).getBytes();
//                        int hashNum = rs.getInt(5);
//                        byte[] expectedHash = rs.getString(3).getBytes();
//
//                        isPassword = PasswordUtil.isExpectedPassword(passwordChar, salt, hashNum, expectedHash);
//
//                    } else {
//                        String userNotExist = "The user " + username + " is not exist.";
//                        req.setAttribute("error", userNotExist);
//
//                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
//                        requestDispatcher.forward(req, resp);
//
//                        return;
//                    }
//                }
//            }

            if (isPassword) {

                req.getSession().setAttribute("username", username);

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/userHomePage.jsp");
                requestDispatcher.forward(req, resp);

            } else {

                String passwordNotMatch = "Password and Username is not correct";
                req.setAttribute("error", passwordNotMatch);

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
