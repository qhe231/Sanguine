package ictgradschool.project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    //After logging out, the user will return to the index page from them home page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        //hide user details
        session.invalidate();
        resp.sendRedirect("./index");
    }

}
