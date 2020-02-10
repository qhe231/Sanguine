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


@WebServlet(name = "article", urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {

    /**
     * ArticleServlet takes a parameter for the articleId. If that parameter is the word "random" then it randomly
     * selects a root article to display.
     *
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "./res/connection.properties")) {
            String articleIdStr = req.getParameter("articleId");
            Article article;

            if (articleIdStr.equalsIgnoreCase("random")) {
                List<Article> articles = ArticleDAO.getArticles(conn, -1, -1, true);
                int randomIndex = (int) (Math.random() * articles.size());
                article = articles.get(randomIndex);
            } else {
                article = ArticleDAO.getSpecificArticle(conn, Integer.parseInt(articleIdStr));
            }

            req.setAttribute("article", article);
            req.setAttribute("user", req.getSession().getAttribute("user"));

            req.getRequestDispatcher("./WEB-INF/article.jsp").forward(req, resp);
        } catch (SQLException | IOException | NumberFormatException | ServletException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
