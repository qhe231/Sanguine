package ictgradschool.project.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String DOB = req.getParameter("DOB");
        String description = req.getParameter("description");

        String avatar = req.getParameter("avatar");

        if (avatar == "uploadPic") {
            String uploadPic = req.getParameter("uploadAvatar");
            avatar = uploadPic;
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/SignUp.jsp");
        requestDispatcher.forward(req, resp);
    }
}
