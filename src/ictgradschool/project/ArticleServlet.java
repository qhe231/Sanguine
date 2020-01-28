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
import java.util.List;


@WebServlet(name="article", urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "./res/connection.properties")) {
            Article article = ArticleDAO.getSpecificArticle(conn, Integer.parseInt(req.getParameter("articleID")));
            req.setAttribute("article", article);

            req.getRequestDispatcher("./WEB-INF/article.jsp").forward(req, resp);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
    }
/*
    private Article getTestData() {
        List<Article> thread = new ArrayList<>();
        List<Article> subThread = new ArrayList<>();
        thread.add(new Article(3, 2, "Filler", "Trolly troll", new Timestamp(2020, 1, 28, 4, 4, 1, 0), new ArrayList<Article>(), 0));
        subThread.add(new Article(2, 0, "Comment on Comment", "something more", new Timestamp(2020, 1, 28, 7, 46, 0, 0), new ArrayList<Article>(), 1));
        thread.add(new Article(1, 1, "Comment", "visible?", new Timestamp(2020, 1, 27, 11, 45, 0, 0), subThread, 0));
        Article a = new Article(0, 0, "Head of Page", "This is garbage", new Timestamp(2020, 1, 27, 11, 10, 57, 0), thread, -1);
        return a;
    }*/
}
