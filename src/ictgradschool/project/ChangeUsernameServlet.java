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
import java.util.List;

@WebServlet(name = "ChangeUsernameServlet", urlPatterns = { "/ChangeUsername" })
public class ChangeUsernameServlet extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserAuthentication ua = (UserAuthentication) session.getAttribute("user_auth");

        String newName = req.getParameter("newName");

//        Update username, set message depending on success or failure
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserAuthenticationDAO.updateUserName(ua, conn, newName);

            String message = "Username successfully updated to: " + newName;
            req.setAttribute("changeUsernameMessage", message);

        } catch (SQLException e) {
            e.printStackTrace();
            String message = "Unable to update username";
            req.setAttribute("changeUsernameMessage", message);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);


    }

}