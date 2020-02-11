package ictgradschool.project.Servlets;

import ictgradschool.project.Article;
import ictgradschool.project.DAOs.ArticleDAO;
import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "userHomePage", urlPatterns = {"/userHomePage"})
public class UserHomePageServlet extends HttpServlet {
    /**
     * UserHomePageServlet is the back end for loading a user's homepage.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userObj = req.getSession().getAttribute("user");
        UserInfo user = (userObj == null) ? null : (UserInfo) userObj;

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            UserInfo owner = UserInfoDAO.getUserInfoByUserName(conn, req.getParameter("owner"));
            List<Article> articles = ArticleDAO.getArticles(conn, -1, owner.getUserId(), true);

            req.setAttribute("articles", articles);
            req.setAttribute("owner", owner);
            req.setAttribute("user", user);
            if (user != null) {
                List<Article> comments = ArticleDAO.getAllCommentsForSpecificUser(conn, user.getUserId());
                req.setAttribute("comments", comments);
            }

            req.getRequestDispatcher("/userHomePage.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
