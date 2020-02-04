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
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@WebServlet(name = "ChangeDateOfBirthServlet", urlPatterns = { "/ChangeDateOfBirth" })
public class ChangeDateOfBirthServlet extends HttpServlet {

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

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}
