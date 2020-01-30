package ictgradschool.project;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("A");
        int articleId = -1;
        try {
            articleId = Integer.parseInt(req.getParameter("articleId"));
        }
        catch (NumberFormatException e) {
            resp.sendRedirect("./index");
        }
        if (articleId == -1)
            resp.sendRedirect("./index");
        System.out.println(articleId);

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            Article article = ArticleDAO.getSpecificArticle(conn, articleId);
            ArticleDAO.deleteArticle(conn, articleId);

            System.out.println("B");
            if (article.getParentId() == -1)
                resp.sendRedirect("./index");
            else {
                Article parent = ArticleDAO.getSpecificArticle(conn, article.getParentId());
                while (parent.getParentId() != -1)
                    parent = ArticleDAO.getSpecificArticle(conn, article.getParentId());
                req.getRequestDispatcher("./article?articleId=" + parent.getArticleId()).forward(req, resp);
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }
}
