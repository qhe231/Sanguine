package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserAuthenticationDAO;
import ictgradschool.project.UserAuthentication;
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

@WebServlet(name = "UserNameServlet", urlPatterns = {"/UserNameList"})
public class UserNameServlet extends HttpServlet {

    /**
     * UserNameServlet is the back end for checking whether a username is available. This servlet is called
     * by ajax when the user is entering a new username and returns a JSON object advising whether the
     * username in question is available at that time.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            String possibleUserName = req.getParameter("possibleUserName");

            List<UserAuthentication> userAuthentications = UserAuthenticationDAO.getAllUserAuthentications(conn);

            resp.getWriter().print("{\"isAvailable\":" + UserNameServlet.checkUserName(userAuthentications, possibleUserName) + "}");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks the provided username against the current UA data to determine whether it is available.
     * Note that usernames cannot contain < or > as that is the easiest way to prevent JS injection through username.
     *
     * @param userAuthentications The list of current UA data retrieved from the database.
     * @param possibleUserName    The username to check against the list.
     * @return whether possibleUserName is available.
     * @throws SQLException
     */
    public static boolean checkUserName(List<UserAuthentication> userAuthentications, String possibleUserName) throws SQLException {

        if (possibleUserName.contains("<") || possibleUserName.contains(">"))
            return false;

        for (UserAuthentication ua : userAuthentications) {
            if (possibleUserName.equals(ua.getUserName())) {
                return false;
            }
        }
        return true;
    }
}
