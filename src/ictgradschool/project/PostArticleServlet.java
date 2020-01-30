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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name="postArticle", urlPatterns = {"/postArticle"})
public class PostArticleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int parentArticle;
        try {
            parentArticle = Integer.parseInt(req.getParameter("parentArticle"));
        }
        catch (NumberFormatException e) {
            parentArticle = -1;
        }

        Article article = new Article(((UserInfo)req.getSession(false).getAttribute("user")),
                req.getParameter("title"), req.getParameter("content"), new Timestamp((new Date()).getTime()),
                new ArrayList<>(), parentArticle);


        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "./res/connection.properties")) {
            ArticleDAO.insertArticle(conn, article);

            resp.sendRedirect("./article?articleId="+article.getArticleId());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
