package ictgradschool.project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeUsernameServlet", urlPatterns = { "/ChangeUsername" })
public class ChangeUsernameServlet extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        TODO get userID off session:
//        int userId = req.getSession().getAttribute("user").getId();

        String newname = req.getParameter("newname");

//        TODO Error message?
//        String someError = "Unable to update username";
//        req.setAttribute("changeUsernameError", someError);

//          TODO update username method call:
//        UserDAO.updateUsername(userId, newname);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}