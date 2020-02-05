package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;

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

@WebServlet(name = "ChangeNameServlet", urlPatterns = { "/ChangeName" })

public class ChangeNameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");


        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateName(ui, conn, firstName, lastName);

            String message = "Name successfully updated to " + firstName + " " + lastName;
            req.setAttribute("changeNameMessage", message);


        } catch (SQLException e) {
            e.printStackTrace();
            String message = "Unable to update name";
            req.setAttribute("changeNameMessage", message);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }
}
