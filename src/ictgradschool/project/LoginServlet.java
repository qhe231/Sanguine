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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isPassword = false;

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, username);
            if (ua == null) {
                resp.getWriter().println("<script>alert('The user is not exist!')</script>");

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);

                return;
            } else {
                char[] passwordChar = password.toCharArray();
                byte [] salt = ua.getSalt().getBytes();
                int hashNum = ua.getHashNum();
                byte [] expectedHash = ua.getHashedPassword().getBytes();
                isPassword = PasswordUtil.isExpectedPassword(passwordChar, salt, hashNum, expectedHash);
            }


            if (isPassword) {

                req.getSession().setAttribute("user", ua);

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/userHomePage.jsp");
                requestDispatcher.forward(req, resp);

            } else {

                resp.getWriter().println("<script>alert('Wrong Password')</script>");

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
