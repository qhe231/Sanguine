package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.ImageUploadUtil;
import org.apache.commons.fileupload.FileItem;

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

@WebServlet(name = "uploadImage", urlPatterns = {"/avatarUpload"})
public class AvatarUploadServlet extends HttpServlet {
    /**
     * AvatarUploadServlet handles the uploading of user avatars.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");
        String avatarURL = "";

        try {
            List<FileItem> fileItems = ImageUploadUtil.init(req.getServletContext()).parseRequest(req);
            for (FileItem fi : fileItems) {
                if (!fi.isFormField()) {
                    avatarURL += ImageUploadUtil.uploadImage(fi, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!avatarURL.equals("")) {
            try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {

                UserInfoDAO.updateAvatarURL(ui, conn, avatarURL);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect("./UserAccountPage.jsp");
    }
}