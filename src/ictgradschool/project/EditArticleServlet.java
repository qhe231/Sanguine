package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

@WebServlet(name = "EditArticle", urlPatterns = {"/EditArticle"})
public class EditArticleServlet extends HttpServlet {

    /**
     * EditArticleServlet is the back end for editing an article or comment. Once the user has made their changes
     * in the TinyMCE editor, this servlet is called via ajax to save those changes to the database.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            Scanner s = new Scanner(req.getInputStream());
            String content = "";
            while (s.hasNextLine())
                content += s.nextLine();

            int articleId = Integer.parseInt(req.getParameter("articleId"));
            Article article = ArticleDAO.getSpecificArticle(conn, articleId);
            article.setContent(content);
            article.setEditedTimeStamp(new Timestamp((new Date()).getTime()));
            ArticleDAO.editArticle(conn, article);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
