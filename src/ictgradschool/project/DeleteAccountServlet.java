package ictgradschool.project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteAccountServlet", urlPatterns = { "/DeleteAccount" })

public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        TODO get userID off session:
//        int userId = req.getSession().getAttribute("user").getId();

        String password = req.getParameter("password");

//        TODO check if entered password matches current password:
//        String hashedPassword = PasswordUtils.hashPassword(password);
//        boolean isCurrentPassword = UserDAO.checkPassword(userId, hashedPassword);

//        TODO if password does not match, set error message
//        if (!isCurrentPassword) {
        String message = "Entered password does not match current password";
        req.setAttribute("deleteAccountMessage", message);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);

//        TODO if password does match, delete the account
//        } else {

//        UserDAO.deleteAccount(userId);

//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
//        dispatcher.forward(req, resp);

//    }


    }

}
