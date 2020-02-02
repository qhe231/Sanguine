package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;

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

@WebServlet(name = "UserNameServlet", urlPatterns = { "/UserNameList" })
public class UsernameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

// Retrieve list of UserAuthentications and loop over each one. If the username is taken return false, else return true
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            String possibleUserName = req.getParameter("possibleUserName");

            List<UserAuthentication> userAuthentications = UserAuthenticationDAO.getAllUserAuthentications(conn);

            for (UserAuthentication ua : userAuthentications){

                if (possibleUserName.equals(ua.getUserName())) {
                    resp.getWriter().print("{\"isAvailable\":" + false + "}");
                    return;
                }

            }
            resp.getWriter().print("{\"isAvailable\":" + true + "}");


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
