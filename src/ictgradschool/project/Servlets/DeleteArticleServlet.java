package ictgradschool.project.Servlets;

import ictgradschool.project.Article;
import ictgradschool.project.DAOs.ArticleDAO;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DeleteArticle", urlPatterns = {"/DeleteArticle"})
public class DeleteArticleServlet extends HttpServlet {

    /**
     * DeleteArticleServlet is the back end for deleting an article or comment.
     * It deletes the article from the database, then attempts to reload the root article's page (or index if the
     * article deleted was root).
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int articleId = -1;
        try {
            articleId = Integer.parseInt(req.getParameter("articleId"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("./index");
        }
        if (articleId == -1)
            resp.sendRedirect("./index");

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            Article article = ArticleDAO.getSpecificArticle(conn, articleId);
            if (article == null) {
                return;
            }
            ArticleDAO.deleteArticle(conn, articleId);

            if (article.getParentId() == -1)
                resp.sendRedirect("./index");
            else {
                Article parent = ArticleDAO.getSpecificArticle(conn, article.getParentId());
                while (parent.getParentId() != -1) {
                    parent = ArticleDAO.getSpecificArticle(conn, parent.getParentId());
                }
                req.getRequestDispatcher("./article?articleId=" + parent.getArticleId()).forward(req, resp);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
