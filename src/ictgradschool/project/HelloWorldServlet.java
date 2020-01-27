package ictgradschool.project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO This is a test servlet, and should be deleted from the project.
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = { "/HelloWorld" })
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("message", "Hello, world!");
        request.setAttribute("image", "Dragonite.png");

        request.getRequestDispatcher("WEB-INF/hello-world.jsp").forward(request, response);
    }
}
