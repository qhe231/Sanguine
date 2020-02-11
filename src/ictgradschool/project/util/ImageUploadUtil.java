package ictgradschool.project.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ImageUploadUtil {
    static File imageFolder;
    static File avatarFolder;
    static File tempFolder;
    static final String imagesRelativePath = "/images";

    /**
     * This method initialises the directories server-side for image uploading (assuming they are needed).
     *
     * @param sc the ServletContext of the calling servlet.
     * @return
     */
    public static ServletFileUpload init(ServletContext sc) {
        // Get the upload folders, ensure they exist.
        imageFolder = new File(sc.getRealPath(imagesRelativePath + "/upload"));
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        avatarFolder = new File(sc.getRealPath(imagesRelativePath + "/avatars"));
        if (!avatarFolder.exists()) {
            avatarFolder.mkdirs();
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

        File finalImageFile;

        if (fi.getContentType().substring(0, 6).equals("image/")) {
            String fileName = fi.getName().substring(0, fi.getName().indexOf(".")) + "_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) +
                    fi.getName().substring(fi.getName().indexOf("."));


            if (isAvatar) {
                File tempImageFile = new File(tempFolder, fileName);
                fi.write(tempImageFile);
                finalImageFile = createThumbnail(tempImageFile);
                tempImageFile.delete();
            } else {
                finalImageFile = new File(imageFolder, fileName);
                fi.write(finalImageFile);
            }
            return "./images/" + (isAvatar ? "avatars/" : "") + finalImageFile.getName();
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

        File thumbnailFile = new File(avatarFolder, imageFile.getName());
        ImageIO.write(img, "png", thumbnailFile);

        return thumbnailFile;
    }
}
