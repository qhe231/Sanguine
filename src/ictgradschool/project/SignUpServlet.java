package ictgradschool.project;

import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.PasswordUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.util.PropertyFilePropertySource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {

    private String streamText;

    private String getValue(String val) {
        String t = streamText.substring(streamText.indexOf("name=\"" + val + "\""));
        t = t.substring(t.indexOf("\n") + 1);
        t = t.substring(t.indexOf("\n") + 1);
        return t.substring(0, t.indexOf("\n")).trim();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ImageUpload.uploadImage(req, resp, getServletContext());

        streamText = "";
        Scanner s = new Scanner(req.getInputStream());
        while (s.hasNextLine())
            streamText += s.nextLine() + "\n";
        System.out.println(streamText);


        String blogName = getValue("blogName");
        String firstName = getValue("firstName");
        String lastName = getValue("lastName");
        String userName = getValue("userName");
        String password = getValue("password");
        Date DOB = Date.valueOf(getValue("dob"));
        String profile = getValue("profile");
        String avatarURL = getValue("avatar");

        String temp = "";
        Scanner t = new Scanner(req.getInputStream());
        while (t.hasNextLine())
            temp += s.nextLine() + "\n";
        System.out.println(">>" + temp + "<<");


//        Create a hashed and salted password
        byte[] saltByte = PasswordUtil.getNextSalt();
        String salt = PasswordUtil.base64Encode(saltByte);
        int hashNum = (int) (Math.random() * 100000) + 1000000;
        byte[] hash = PasswordUtil.hash(password.toCharArray(), saltByte, hashNum);
        String hashedPassword = PasswordUtil.base64Encode(hash);

        UserAuthentication ua = new UserAuthentication(null, userName, hashedPassword, salt, hashNum);

//        Insert user information to create new account
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            boolean insertUaSuccessfully = UserAuthenticationDAO.insertANewUserAuthentication(ua, conn);
            if (insertUaSuccessfully) {
                UserInfo ui = new UserInfo(ua.getUserId(), blogName, firstName, lastName, DOB, avatarURL, profile, ua.getUserName());
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
