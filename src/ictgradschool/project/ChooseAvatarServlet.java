package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ChooseAvatarServlet", urlPatterns = { "/ChooseAvatar" })
public class ChooseAvatarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");
        String newAvatar = req.getParameter("avatar");


        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateAvatarURL(ui, conn, newAvatar );

        } catch (SQLException e) {


        }


    }
}