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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            List<Article> articles = ArticleDAO.getArticles(conn, -1, -1);

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


            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
