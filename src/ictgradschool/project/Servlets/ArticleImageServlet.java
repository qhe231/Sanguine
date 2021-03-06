package ictgradschool.project.Servlets;

import ictgradschool.project.util.ImageUploadUtil;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "articleImage", urlPatterns = {"/articleImage"})
public class ArticleImageServlet extends HttpServlet {

    /**
     * ArticleImageServlet provides the backend for TinyMCE's image upload capability.
     * It provides a response containing the needed JSON object with the server location of the uploaded image file.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<FileItem> fileItems = ImageUploadUtil.init(req.getServletContext()).parseRequest(req);
            for (FileItem fi : fileItems) {
                if (!fi.isFormField()) {
                    resp.getWriter().print("{\"location\":\"" + ImageUploadUtil.uploadImage(fi, false) + "\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
