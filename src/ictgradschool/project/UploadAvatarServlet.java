package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "uploadAvatar", urlPatterns = {"/uploadAvatar"})
public class UploadAvatarServlet extends HttpServlet {
    private File uploadsFolder;
    private File tempFolder;
    private final String imagesRelativePath = "/images";

//    @Override
//    public void init() throws ServletException {
//        super.init();
//
//        // Get the upload folder, ensure it exists.
//        this.uploadsFolder = new File(getServletContext().getRealPath(imagesRelativePath));
//        if (!uploadsFolder.exists()) {
//            uploadsFolder.mkdirs();
//        }
//
//        // Create the temporary folder that the file-upload mechanism needs.
//        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
//        if (!tempFolder.exists()) {
//            tempFolder.mkdirs();
//        }
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ImageUpload.uploadImage(req,resp,getServletContext());

//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setSizeThreshold(4 * 1024);
//        factory.setRepository(tempFolder);
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        try {
//            List<FileItem> fileItems = upload.parseRequest(req);
//            File fullsizeImageFile = null;
//
//            for (FileItem fi : fileItems) {
//                if (!fi.isFormField() && fi.getContentType().substring(0, 6).equals("image/")) {
//                    String fileName = fi.getName();
//                    fullsizeImageFile = new File(uploadsFolder, fileName);
//                    fi.write(fullsizeImageFile);
//                    try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
//                        String avatarURL = "./images/" + fullsizeImageFile.getName();
//                        HttpSession session = req.getSession();
//                        UserInfo ui = (UserInfo) session.getAttribute("user");
//                        UserInfoDAO.updateAvatarURL(ui, conn, avatarURL);
//
//                        String message = "Avatar updated successfully";
//                        req.setAttribute("changeAvatarMessage", message);
//
//                    } catch (SQLException e) {
//
//                        String message = "Avatar could not be changed";
//                        req.setAttribute("changeAvatarMessage", message);
//
//                    }
//                }
//            }
//            UserInfo ui = (UserInfo)(req.getSession().getAttribute("user"));
//            resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new ServletException(e);
//        }

        UserInfo ui = (UserInfo)(req.getSession().getAttribute("user"));
        resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());

//        resp.sendRedirect("./UserAccountPage.jsp");
    }
}
