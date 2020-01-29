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


@WebServlet(name="article", urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "./res/connection.properties")) {
            Article article = ArticleDAO.getSpecificArticle(conn, (int)req.getAttribute("articleID"));
            req.setAttribute("article", article);

            RequestDispatcher rd = req.getRequestDispatcher("./WEB-INF/article.jsp");
            rd.forward(req, resp);
        }
        catch (SQLException | IOException | NumberFormatException | ServletException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
