package ictgradschool.project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = { "/ChangePassword" })

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        TODO get userID off session:
//        int userId = req.getSession().getAttribute("user").getId();

        String currentPassword = req.getParameter("currentPassword");

//        TODO check if entered password matches current password:
//        String hashedPassword = PasswordUtils.hashPassword(currentPassword);
//        boolean isCurrentPassword = UserDAO.checkPassword(userId, hashedPassword);

//        TODO if password does not match, set error message
//        if (!isCurrentPassword) {
        String error = "Entered password does not match current password";
        req.setAttribute("changePasswordMessage", error);

//        TODO if password does match, change the password
//        } else {
        String newpassword = req.getParameter("newpassword");
//        UserDao.updatePassword(userId, newpassword);

        String message = "Password changed successfully";
        req.setAttribute("changePasswordMessage", message);
//    }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}
