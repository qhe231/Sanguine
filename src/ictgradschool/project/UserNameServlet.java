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
import java.util.List;

@WebServlet(name = "UserNameServlet", urlPatterns = { "/UserNameList" })
public class UserNameServlet extends HttpServlet {

    public static boolean checkUserName(List<UserAuthentication> userAuthentications, String possibleUserName) throws SQLException {

        for (UserAuthentication ua : userAuthentications){
            if (possibleUserName.equals(ua.getUserName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            List<UserAuthentication> userAuthentications = UserAuthenticationDAO.getAllUserAuthentications(conn);
            String possibleUserName = req.getParameter("possibleUserName");

            resp.getWriter().print("{\"isAvailable\":" + UserNameServlet.checkUserName(userAuthentications, possibleUserName) + "}");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
