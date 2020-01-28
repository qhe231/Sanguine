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

@WebServlet(name = "ChangeBlogNameServlet", urlPatterns = { "/ChangeBlogName" })
public class ChangeBlogNameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        String newBlogName = req.getParameter("newBlogName");

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

           UserInfoDAO.updateBlogName(ui, conn, newBlogName);

            String message = "Blog name successfully updated to " + newBlogName;
            req.setAttribute("changeBlogNameMessage", message);

        } catch (SQLException e) {

            String message = "Unable to update blog name";
            req.setAttribute("changeBlogNameMessage", message);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}
