package ictgradschool.project.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isPassword = false;//PasswordUtil.isExpectedPassword();
        if (isPassword){

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/HomePage.jsp");
            requestDispatcher.forward(req, resp);

        }else {

            String error = "Password and Username is not correct";
            req.setAttribute("Error", error);

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
