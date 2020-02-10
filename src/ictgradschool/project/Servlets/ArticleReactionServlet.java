package ictgradschool.project.Servlets;

import ictgradschool.project.ArticleReaction;
import ictgradschool.project.DAOs.ArticleReactionDAO;
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
import java.util.List;
import java.util.Scanner;

@WebServlet(name = "articleReaction", urlPatterns = {"/articleReaction"})
public class ArticleReactionServlet extends HttpServlet {

    /**
     * ArticleReactionServlet is called via ajax; it takes a list of Articles and returns a JSON array containing the
     * reactions to each Article, as well as what the current user's reaction is.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserInfo user = (UserInfo) req.getSession().getAttribute("user");

        Scanner s = new Scanner(req.getInputStream());
        String content = "";
        while (s.hasNextLine())
            content += s.nextLine();

        String[] ids = content.split(",");
        int[] likes = new int[ids.length];
        int[] dislikes = new int[ids.length];
        int[] userResponse = new int[ids.length];

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            for (int i = 0; i < ids.length; i++) {
                likes[i] = 0;
                dislikes[i] = 0;
                userResponse[i] = (user == null) ? -1 : 0;
                List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, ids[i]);
                for (ArticleReaction r : reactions) {
                    switch (r.getReaction()) {
                        case 1:
                            likes[i]++;
                            break;
                        case 2:
                            dislikes[i]++;
                            break;
                    }
                    if (user != null && r.getUserId() == user.getUserId())
                        userResponse[i] = r.getReaction();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String responseString = "[";
        for (int i = 0; i < ids.length; i++) {
            responseString += "{\"likes\":" + likes[i] + ",\"dislikes\":" + dislikes[i] + ",\"user\":" + userResponse[i] + "}";
            if (i < ids.length - 1)
                responseString += ",";
            else
                responseString += "]";
        }

        resp.getWriter().println(responseString);
    }
}
