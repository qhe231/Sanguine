package ictgradschool.project.util;

import ictgradschool.project.util.DBConnectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

public class ImageUploadUtil {
    static File uploadsFolder;
    static File tempFolder;
    static final String imagesRelativePath = "/images";

    /**
     * This method initialises the directories server-side for image uploading (assuming they are needed).
     *
     * @param sc the ServletContext of the calling servlet.
     * @return
     */
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

        return new ServletFileUpload(factory);
    }

    /**
     * This method confirms that fi is in fact an image and, if so, uploads it to the server.
     *
     * @param fi       The file to upload.
     * @param isAvatar Avatars are resized to 100x100 when saved; other images are not.
     * @return a string containing the path to where this image is now stored on the server.
     * @throws Exception
     */
    public static String uploadImage(FileItem fi, boolean isAvatar) throws Exception {

        File fullsizeImageFile = null;

        if (fi.getContentType().substring(0, 6).equals("image/")) {
            String fileName = fi.getName().substring(0, fi.getName().indexOf(".")) + "_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) +
                    fi.getName().substring(fi.getName().indexOf("."));

            fullsizeImageFile = new File(uploadsFolder, fileName);
            if (isAvatar) {
                File tempImageFile = new File(tempFolder, fileName);
                fi.write(tempImageFile);
                fullsizeImageFile = createThumbnail(tempImageFile);
                tempImageFile.delete();
            } else
                fi.write(fullsizeImageFile);
            return "./images/" + fullsizeImageFile.getName();
        }

        return "";
    }

    /**
     * This method resizes a provided image to 100x100px.
     *
     * @param imageFile
     * @return the resized image.
     * @throws IOException
     */
    private static File createThumbnail(File imageFile) throws IOException {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        img.createGraphics().drawImage(ImageIO.read(imageFile).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);

        File thumbnailFile = new File(uploadsFolder, imageFile.getName());
        ImageIO.write(img, "png", thumbnailFile);

        return thumbnailFile;
    }
}
