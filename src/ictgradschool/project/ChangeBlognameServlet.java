package ictgradschool.project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeBlognameServlet", urlPatterns = { "/ChangeBlogname" })
public class ChangeBlognameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        TODO get userID off session:
//        int userId = req.getSession().getAttribute("user").getId();

        String newBlogname = req.getParameter("newblogname");

//          TODO update blogname method call:
//        UserInfoDAO.updateBlogname(userId, newBlogname);

        String message = "Unable to update blogname";
        req.setAttribute("changeBlognameMessage", message);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserAccountPage.jsp");
        dispatcher.forward(req, resp);
    }

}
