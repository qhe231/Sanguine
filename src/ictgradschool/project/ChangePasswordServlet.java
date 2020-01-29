package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.PasswordUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = { "/ChangePassword" })

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserAuthentication ua = (UserAuthentication) session.getAttribute("user");

        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");

        boolean isPasswordCorrect = PasswordUtil.isExpectedPassword(currentPassword.toCharArray(), ua.getSalt().getBytes(), ua.getHashedPassword().getBytes());

//          If user entered correct password, update password and set success message
        if (isPasswordCorrect) {

            try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

                UserAuthenticationDAO.updatePassword(ua, conn, newPassword);

            } catch (SQLException e) {

                String message = "Password could not be changed";
                req.setAttribute("changePasswordMessage", message);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
                dispatcher.forward(req, resp);

            }
//          If user entered incorrect password, set error message
        } else {

            String message = "Entered password does not match current password";
            req.setAttribute("changePasswordMessage", message);

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}
