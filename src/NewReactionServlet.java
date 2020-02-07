import ictgradschool.project.ArticleReactionDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "newReaction", urlPatterns = {"/react"})
public class NewReactionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int articleId = Integer.parseInt(req.getParameter("articleId"));
        int reaction = Integer.parseInt(req.getParameter("reaction"));
        int userId = ((UserInfo)req.getSession().getAttribute("user")).getUserId();

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            resp.getWriter().println("{\"reactionSet\":" + ArticleReactionDAO.setReactionToArticle(conn, articleId, userId, reaction)+"}");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
