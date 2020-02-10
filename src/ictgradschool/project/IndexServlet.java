package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "index", urlPatterns = {"", "/index"})
public class IndexServlet extends HttpServlet {

    /**
     * IndexServlet loads the index page. It retrieves a list of all root articles sorted by date descending,
     * determines whether the user has opted to sort by popularity instead, then forwards those articles to the JSP
     * once sorting is complete.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            List<Article> articles = ArticleDAO.getArticles(conn, -1, -1, true);

            String sorting = "sort_date";
            Cookie[] cookies = req.getCookies();
            if (cookies != null)
                for (Cookie c : cookies)
                    if (c.getName().equals("sortingMethod")) {
                        sorting = c.getValue();
                        break;
                    }
            if (sorting.equals("sort_popular"))
                Article.sortByPopularity(articles);

            req.setAttribute("articles", articles);

            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
