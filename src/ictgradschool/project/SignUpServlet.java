package ictgradschool.project;

import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.ImageUploadUtil;
import ictgradschool.project.util.PasswordUtil;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String> fields = new TreeMap<>();

        try {
            List<FileItem> fileItems = ImageUploadUtil.init(req.getServletContext()).parseRequest(req);
            for (FileItem fi : fileItems) {
                if (fi.isFormField()) {

                    // the upload part becomes isFormField
                    // and the image is uploaded without the execution of the other method
                    // the image is not in the out folder
                    System.out.println(fi.getFieldName() + "-" + fi.getString());

                    fields.put(fi.getFieldName(), fi.getString());
                } else {

                    // it is not doing this whole part????
                    String potentialAvatarUrl = ImageUploadUtil.uploadImage(fi, true);
                    System.out.println("1111111111111111111111" + potentialAvatarUrl);
                    if (!potentialAvatarUrl.equals(""))
                        fields.put("avatar", potentialAvatarUrl);
                    else {
                        String av = fields.get("avatar");
                        System.out.println("***************" + av);
                        if (!av.substring(av.length() - 4).equals(".png"))
                            fields.put("avatar", "./images/1.png");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


//        Create a hashed and salted password
        byte[] saltByte = PasswordUtil.getNextSalt();
        String salt = PasswordUtil.base64Encode(saltByte);
        int hashNum = (int) (Math.random() * 100000) + 1000000;
        byte[] hash = PasswordUtil.hash(fields.get("password").toCharArray(), saltByte, hashNum);
        String hashedPassword = PasswordUtil.base64Encode(hash);

        UserAuthentication ua = new UserAuthentication(null, fields.get("userName"), hashedPassword, salt, hashNum);

//        Insert user information to create new account
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            boolean insertUaSuccessfully = UserAuthenticationDAO.insertANewUserAuthentication(ua, conn);
            if (insertUaSuccessfully) {
                UserInfo ui = new UserInfo(ua.getUserId(), fields.get("blogName"), fields.get("firstName"), fields.get("lastName"), Date.valueOf(fields.get("dob")), fields.get("avatar"), fields.get("profile"), ua.getUserName());
                boolean insertUiSuccessfully = UserInfoDAO.insertANewUserInfo(ui, conn);
                if (insertUiSuccessfully) {
                    req.getSession().setAttribute("user", ui);

                    resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());

                } else {
                    signUpFailed(req, resp);
                }
            } else {
                signUpFailed(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //    If sign up failed, set error message
    private void signUpFailed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "Sign up failed.");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/SignUp.jsp");
        requestDispatcher.forward(req, resp);
    }


}
