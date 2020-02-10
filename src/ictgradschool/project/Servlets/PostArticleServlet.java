package ictgradschool.project.Servlets;

import ictgradschool.project.Article;
import ictgradschool.project.DAOs.ArticleDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "postArticle", urlPatterns = {"/postArticle"})
public class PostArticleServlet extends HttpServlet {

    /**
     * PostArticleServlet is the back end for posting a new article or comment.
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String parent = req.getParameter("parentId");
        int parentId = (parent == null) ? -1 : Integer.parseInt(parent);

        Article article = new Article(((UserInfo) req.getSession(false).getAttribute("user")),
                req.getParameter("title"), req.getParameter("content"), new Timestamp((new Date()).getTime()),
                new ArrayList<>(), parentId);

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            ArticleDAO.insertArticle(conn, article);

            String rootArticle = req.getParameter("rootArticle");
            if (rootArticle == null)
                rootArticle = "" + article.getArticleId();
            resp.sendRedirect("./article?articleId=" + rootArticle + "#" + article.getArticleId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
