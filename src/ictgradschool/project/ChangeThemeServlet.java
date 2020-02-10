package ictgradschool.project;

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

@WebServlet(name = "ChangeThemeServlet", urlPatterns = {"/ChangeTheme"})


public class ChangeThemeServlet extends HttpServlet {

    /**
     * ChangeThemeServlet is the back end for changing the user's theme.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        String theme = req.getParameter("theme");

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateTheme(ui, conn, theme);

            String message = "Theme successfully updated to " + theme;
            req.setAttribute("changeThemeMessage", message);


        } catch (SQLException e) {
            e.printStackTrace();
            String message = "Unable to update theme";
            req.setAttribute("changeThemeMessage", message);
        }

        req.getRequestDispatcher("/UserAccountPage.jsp").forward(req, resp);
    }
}
