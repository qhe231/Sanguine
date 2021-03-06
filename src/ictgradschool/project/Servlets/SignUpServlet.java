package ictgradschool.project.Servlets;

import ictgradschool.project.DAOs.UserAuthenticationDAO;
import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.ImageUploadUtil;
import ictgradschool.project.util.PasswordUtil;
import org.apache.commons.fileupload.FileItem;

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

    /**
     * SignUpServlet is the back end for the signup page.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String> fields = new TreeMap<>();

        try {
            List<FileItem> fileItems = ImageUploadUtil.init(req.getServletContext()).parseRequest(req);
            for (FileItem fi : fileItems) {
                if (fi.isFormField()) {

                    fields.put(fi.getFieldName(), fi.getString());
                } else {
                    String potentialAvatarUrl = ImageUploadUtil.uploadImage(fi, true);
                    if (!potentialAvatarUrl.equals(""))
                        fields.put("avatar", potentialAvatarUrl);
                    else {
                        String av = fields.get("avatar");
                        if (!av.substring(av.length() - 4).equals(".png"))
                            fields.put("avatar", "./images/avatars/1.png");
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
            if (UserAuthenticationDAO.insertANewUserAuthentication(ua, conn)) {
                UserInfo ui = new UserInfo(ua.getUserId(), fields.get("blogName"), fields.get("firstName"), fields.get("lastName"), Date.valueOf(fields.get("dob")), fields.get("avatar"), fields.get("profile"), null, ua.getUserName());

                if (UserInfoDAO.insertANewUserInfo(ui, conn)) {
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
        req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
    }
}
