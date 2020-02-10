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

@WebServlet(name = "search", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    /**
     * SearchServlet is the back end for the searchbar.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            List<UserAuthentication> users = UserAuthenticationDAO.getUserAuthenticationsBySearch(conn, search);
            List<Article> articles = ArticleDAO.getArticlesBySearch(conn, search);
            req.setAttribute("users", users);
            req.setAttribute("articles", articles);

            req.getRequestDispatcher("/searchResult.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
