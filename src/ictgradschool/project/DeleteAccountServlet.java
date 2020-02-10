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

    /**
     * DeleteAccountServlet is the back end for deleting the user's account.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserInfo user = (UserInfo) req.getSession().getAttribute("user");
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, user.getUserName());

            String password = req.getParameter("password");

            byte[] salt = PasswordUtil.base64Decode(ua.getSalt());
            byte[] expectedHash = PasswordUtil.base64Decode(ua.getHashedPassword());

            boolean isPasswordCorrect = PasswordUtil.isExpectedPassword(password.toCharArray(), salt, ua.getHashNum(), expectedHash);

//        If user entered correct password, delete the account
            if (isPasswordCorrect) {

                UserAuthenticationDAO.deleteUser(ua, conn);
                req.getSession().invalidate();

                resp.sendRedirect("./index");
                return;
            }
//          If user entered incorrect password, set error message
            else {
                req.setAttribute("deleteAccountMessage", "Entered password does not match current password");
            }
        } catch (SQLException e) {
            req.setAttribute("deleteAccountMessage", "Account could not be deleted");
        }

        req.getRequestDispatcher("/UserAccountPage.jsp").forward(req, resp);
    }

}
