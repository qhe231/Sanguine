package ictgradschool.project.util;

import ictgradschool.project.UserInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogName = req.getParameter("blogName");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String DOB = req.getParameter("dob-year") +"-" + req.getParameter("dob-month") +"-" + req.getParameter("dob-day");
        String profile = req.getParameter("profile");
        String avatarURL = req.getParameter("avatar");

        if (avatarURL == "uploadPic") {
            String uploadPic = req.getParameter("uploadAvatar");
            avatarURL = uploadPic;
        }

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/SignUp.jsp");
        requestDispatcher.forward(req, resp);
    }


}
