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
import java.util.Scanner;

@WebServlet(name="postArticle", urlPatterns = {"/postArticle"})
public class PostCommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int parentArticle = Integer.parseInt(req.getParameter("parentArticle"));

        String content = req.getParameter("content");
        if (content == null) {
            content = "";
            Scanner s = new Scanner(req.getInputStream());
            while (s.hasNextLine())
                content += s.nextLine();
        }

        Article article = new Article(((UserInfo)req.getSession(false).getAttribute("user")),
                req.getParameter("title"), content, new Timestamp((new Date()).getTime()),
                new ArrayList<>(), parentArticle);

        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "./res/connection.properties")) {
            ArticleDAO.insertArticle(conn, article);
            resp.getWriter().print("{ \"articleId\":\"" + article.getArticleId() + "\", \"author\":\"" + article.getAuthor().getUserName() + "\", \"avatar\":\"" +
                                    article.getAuthor().getAvatarURL() + "\", \"time\":\"" + article.getPostedTimeStamp() + "\"}");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
