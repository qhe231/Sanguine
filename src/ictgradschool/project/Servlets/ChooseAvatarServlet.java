package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

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

@WebServlet(name = "ChooseAvatarServlet", urlPatterns = {"/ChooseAvatar"})
public class ChooseAvatarServlet extends HttpServlet {

    /**
     *  ChooseAvatarServlet is the back end for changing the user's avatar to a predefined avatar.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        String newAvatar = req.getParameter("avatar");

//      Update avatar, set message depending on success or failure
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateAvatarURL(ui, conn, newAvatar);

            String message = "Avatar updated successfully";
            req.setAttribute("changeAvatarMessage", message);

        } catch (SQLException e) {

            String message = "Avatar could not be changed";
            req.setAttribute("changeAvatarMessage", message);

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);

    }
}
