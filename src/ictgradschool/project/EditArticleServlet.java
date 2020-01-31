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

@WebServlet(name = "EditArticle", urlPatterns = {"/EditArticle"})
public class EditArticleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            String content = req.getParameter("content");
            int articleId = Integer.parseInt(req.getParameter("articleId"));
            Article article = ArticleDAO.getSpecificArticle(conn, articleId);
            article.setContent(content);
            ArticleDAO.editArticle(conn, article);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
