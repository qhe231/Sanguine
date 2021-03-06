package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ChangeDateOfBirthServlet", urlPatterns = {"/ChangeDateOfBirth"})
public class ChangeDateOfBirthServlet extends HttpServlet {

    /**
     * ChangeDateOfBirthServlet is the back end for changing the user's date of birth.
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

        Date date = Date.valueOf(req.getParameter("dob"));

//        Update date of birth, set message depending on success or failure
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            UserInfoDAO.updateDateOfBirth(ui, conn, date);

            String message = "Date of birth successfully updated to " + date;
            req.setAttribute("changeDOBNameMessage", message);

        } catch (SQLException e) {

            String message = "Unable to update date of birth";
            req.setAttribute("changeDOBNameMessage", message);
        }

        req.getRequestDispatcher("/UserAccountPage.jsp").forward(req, resp);
    }

}
