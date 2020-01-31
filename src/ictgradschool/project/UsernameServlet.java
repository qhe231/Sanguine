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


        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

            String possibleUserName = req.getParameter("possibleUserName");


            System.out.println(possibleUserName);
            List<UserAuthentication> userAuthentications = UserAuthenticationDAO.getAllUserAuthentications(conn);
//            ArrayList<String> userNames = new ArrayList<>();

            for (UserAuthentication ua : userAuthentications){
//                userNames.add(ua.getUserName());

                if (possibleUserName.equals(ua.getUserName())) {
                    resp.getWriter().print("{\"isAvailable\":" + false + "}");
                    return;
                }


            }
            resp.getWriter().print("{\"isAvailable\":" + true + "}");

//            req.setAttribute("userNames", userNames);

//            req.getRequestDispatcher("/SignUp.jsp").forward(req,resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
