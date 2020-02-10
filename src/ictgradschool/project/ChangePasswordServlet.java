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

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePassword"})

public class ChangePasswordServlet extends HttpServlet {

    /**
     * ChangePasswordServlet is the back end for changing the user's password.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");
        UserAuthentication ua = null;


        String currentPasswordPlainText = req.getParameter("currentPassword");
        String newPasswordPlainText = req.getParameter("newPassword");

        Password existingPassword = new Password(ua.getSalt(), ua.getHashNum(), ua.getHashedPassword());

        boolean isPasswordCorrect = PasswordUtil.isExpectedPassword(currentPasswordPlainText.toCharArray(), existingPassword.getSaltByte(), existingPassword.getHashNum(),
                existingPassword.getHashByte());

//          If user entered correct password, update password and set success message
        if (isPasswordCorrect) {

            try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

                ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, ui.getUserName());
                UserAuthenticationDAO.updatePassword(ua, conn, newPasswordPlainText);

                String message = "Password was successfully changed";
                req.setAttribute("changePasswordMessage", message);

            } catch (SQLException e) {
                e.printStackTrace();
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

        req.getRequestDispatcher("/UserAccountPage.jsp").forward(req, resp);
    }

}
