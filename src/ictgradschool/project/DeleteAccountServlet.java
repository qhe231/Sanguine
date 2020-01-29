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

@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccount"})

public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserAuthentication ua = (UserAuthentication) session.getAttribute("user_auth");

        String password = req.getParameter("password");

        byte[] salt = PasswordUtil.base64Decode(ua.getSalt());
        byte[] expectedHash = PasswordUtil.base64Decode(ua.getHashedPassword());

        boolean isPasswordCorrect = PasswordUtil.isExpectedPassword(password.toCharArray(), salt, ua.getHashNum(), expectedHash);

//        If user entered correct password, delete the account
        if (isPasswordCorrect) {

            try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

                UserAuthenticationDAO.deleteUser(ua, conn);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(req, resp);

            } catch (SQLException e) {

                String message = "Account could not be deleted";
                req.setAttribute("deleteAccountMessage", message);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
                dispatcher.forward(req, resp);

            }
//          If user entered incorrect password, set error message
        } else {

            String message = "Entered password does not match current password";
            req.setAttribute("deleteAccountMessage", message);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
            dispatcher.forward(req, resp);

        }

    }

}
