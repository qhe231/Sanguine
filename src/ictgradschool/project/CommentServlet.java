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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "comments", urlPatterns = {"/comments"})
public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("userId"));
        Object userObj = req.getSession().getAttribute("user");
        UserInfo user = (userObj == null) ? null : (UserInfo) userObj;

        System.out.println(userId);
        System.out.println(userId);
        System.out.println(userId);
        System.out.println(userId);
        System.out.println(userId);
        System.out.println(userId);
        System.out.println(userId);

        List<Article> comments = new ArrayList<>();

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            //get the user's all articles
            List<Article> articles = ArticleDAO.getArticles(conn, -1, userId);
            UserInfo owner = UserInfoDAO.getUserInfoByUserName(conn, req.getParameter("owner"));

//            for (Article a : articles) {
//
//                //add each article's comment in the comments list
//                for (Article c : a.getChildren()){
//                    comments.add(c);
//                }
//            }
//
//            req.setAttribute("articles", articles);
//            req.setAttribute("user", user);
//            req.setAttribute("owner", owner);
//            req.setAttribute("comments",comments);

            RequestDispatcher dispatcher  =getServletContext().getRequestDispatcher("/userHomePage.jsp");
            dispatcher.forward(req,resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
