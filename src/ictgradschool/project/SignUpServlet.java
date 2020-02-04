package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogName = req.getParameter("blogName");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String plainTextPassword = req.getParameter("password");
        String DOBString = req.getParameter("dob-year") + "-" + req.getParameter("dob-month") + "-" + req.getParameter("dob-day");
        Date DOB = Date.valueOf(DOBString);
        String profile = req.getParameter("profile");
        String avatarURL = req.getParameter("avatar");

        if (avatarURL.equals("own")) {

        }

        Password password = new Password(plainTextPassword);

        UserAuthentication ua = new UserAuthentication(null, userName, password.getHashedPassword(), password.getSalt(), password.getHashNum());

//        Insert user information to create new account
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            boolean insertUaSuccessfully = UserAuthenticationDAO.insertANewUserAuthentication(ua, conn);
            if (insertUaSuccessfully) {
                UserInfo ui = new UserInfo(ua.getUserId(), blogName, firstName, lastName, DOB, avatarURL, profile, ua.getUserName());
                boolean insertUiSuccessfully = UserInfoDAO.insertANewUserInfo(ui, conn);
                if (insertUiSuccessfully) {
                    req.getSession().setAttribute("user", ui);

                    //req.getRequestDispatcher("./userHomePage?ownerId=" + ui.getUserId()).forward(req, resp);
                    resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());
                } else {
                    signUpFailed(req, resp);
                }
            } else {
                signUpFailed(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    If sign up failed, set error message
    private void signUpFailed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "Sign up failed.");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/SignUp.jsp");
        requestDispatcher.forward(req, resp);
    }


}
