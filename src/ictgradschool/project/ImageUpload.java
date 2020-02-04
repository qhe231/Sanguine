package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ImageUpload {
    static File uploadsFolder;
    static File tempFolder;
    static final String imagesRelativePath = "/images";

    public static ServletFileUpload init(ServletContext sc) {
        // Get the upload folder, ensure it exists.
        uploadsFolder = new File(sc.getRealPath(imagesRelativePath));
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        // Create the temporary folder that the file-upload mechanism needs.
        tempFolder = new File(sc.getRealPath("/WEB-INF/temp"));
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);
        ServletFileUpload upload = new ServletFileUpload(factory);

        return upload;
    }

    public static String uploadImage(FileItem fi, boolean isAvatar) throws Exception{

        File fullsizeImageFile = null;

        if (fi.getContentType().substring(0, 6).equals("image/")) {
            String fileName = fi.getName();
            fullsizeImageFile = new File(uploadsFolder, fileName);
            fi.write(fullsizeImageFile);
            return "./images/" + fullsizeImageFile.getName();
        }

        return null;
    }
}
