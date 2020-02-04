package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "uploadImage", urlPatterns = {"/imageUpload"})
public class ImageUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");
        String avatarURL = "";

        try {
            List<FileItem> fileItems = ImageUpload.init(req.getServletContext()).parseRequest(req);
            for (FileItem fi : fileItems) {
                if (!fi.isFormField()) {
                    avatarURL += ImageUpload.uploadImage(fi, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            UserInfoDAO.updateAvatarURL(ui, conn, avatarURL);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("./UserAccountPage.jsp");
    }
}