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

@WebServlet(name = "ChangeDescriptionServlet", urlPatterns = {"/ChangeDesc"})

public class ChangeDescriptionServlet extends HttpServlet {

    /**
     * ChangeDescriptionServlet is the back end for changing the user's public description.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        String desc = req.getParameter("desc");

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateProfile(ui, conn, desc);

            String message = "Description successfully updated to " + desc;
            req.setAttribute("changeDescMessage", message);

        } catch (SQLException e) {

            String message = "Unable to update description";
            req.setAttribute("changeDescMessage", message);
        }

        req.getRequestDispatcher("/UserAccountPage.jsp").forward(req, resp);
    }
}
