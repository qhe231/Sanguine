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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "userHomePage", urlPatterns = {"/userHomePage"})
public class UserHomePageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int ownerId = Integer.parseInt(req.getParameter("ownerId"));

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            List<Article> articles = ArticleDAO.getArticles(conn, -1, ownerId);
            UserInfo user = UserInfoDAO.getUserInfoById(conn, userId);
            UserInfo owner = UserInfoDAO.getUserInfoById(conn, ownerId);

            req.setAttribute("articles", articles);
            req.setAttribute("user", user);
            req.setAttribute("owner", owner);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userHomePage.jsp");
            dispatcher.forward(req, resp);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
