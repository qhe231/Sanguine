package ictgradschool.project.Servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import ictgradschool.project.DAOs.UserAuthenticationDAO;
import ictgradschool.project.DAOs.UserInfoDAO;
import ictgradschool.project.Password;
import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserInfo;
import ictgradschool.project.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

@WebServlet(name = "LoginGoogle", urlPatterns = {"/LoginGoogle"})
public class LoginGoogleServlet extends HttpServlet {

    /**
     * GoogleLoginServlet is the back end for communicating with Google's verification server when someone logs
     * in using a Google account.
     * Name and username are generated from Google's name information; password is a randomly selected
     * 15 characters from their Google session token, and their avatar is linked from Google's servers rather
     * than being saved locally. All other personal details are initialised empty.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Scanner s = new Scanner(req.getInputStream());
        String tokenString = "";
        while (s.hasNextLine())
            tokenString += s.nextLine();

        HttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(transport, factory);

        GoogleIdToken token = GoogleIdToken.parse(factory, tokenString);
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            if (verifier.verify(token)) {
                GoogleIdToken.Payload payload = token.getPayload();
                String googleId = (String) payload.get("sub");
//check for existing account for this user
                UserAuthentication existingUser = UserAuthenticationDAO.getUserAuthenticationByThirdPartyId(conn, googleId);
                if (existingUser != null) {
                    UserInfo ui = UserInfoDAO.getUserInfoByUserName(conn, existingUser.getUserName());
                    req.getSession().setAttribute("user", ui);

                    resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());
                    return;
                } else {
//if user not found, create an account for them
                    String userName = createUserName(payload);
                    if (userName == null)
                        return;

                    int pwIndex = (int) (Math.random() * (tokenString.length() - 16));
                    Password password = new Password(tokenString.substring(pwIndex, pwIndex + 15));

                    UserAuthentication ua = new UserAuthentication(null, userName, password.getHashedPassword(),
                            password.getSalt(), password.getHashNum(), googleId);

                    if (UserAuthenticationDAO.insertANewUserAuthentication(ua, conn)) {

                        String fname = (String) payload.get("given_name");
                        String sname = (String) payload.get("family_name");
                        String avatarURL = (String) payload.get("picture");
                        Date dob = new Date(0);

                        UserInfo ui = new UserInfo(ua.getUserId(), userName + "'s Blog", fname, sname, dob, avatarURL, "", null, userName);

                        if (UserInfoDAO.insertANewUserInfo(ui, conn)) {
                            req.getSession().setAttribute("user", ui);

                            resp.sendRedirect("./userHomePage?owner=" + ui.getUserName());
                            return;
                        }

                    }
                }
            }
        } catch (GeneralSecurityException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a default username of the form 'xyyy001' for someone logging in from their Google
     * account for the first time, where x is the first letter of their first name, yyy is the first 3 letters
     * of their surname and 001 is a minimum-3-digit count of how many users have the 'xyyy' username
     * (essentially it's the system UoA uses).
     *
     * @param payload The user's Google account information.
     * @return
     */
    private String createUserName(GoogleIdToken.Payload payload) {
        String userNameLetters = "" + ((String) payload.get("given_name")).toLowerCase().charAt(0);
        String family_name = ((String) payload.get("family_name")).toLowerCase();
        if (family_name.length() < 4)
            userNameLetters += family_name;
        else
            userNameLetters += family_name.substring(0, 3);
        if (userNameLetters.length() == 0)
            userNameLetters = "unknown";
        String userName = userNameLetters + "001";
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            List<UserAuthentication> userAuthentications = UserAuthenticationDAO.getAllUserAuthentications(conn);
            for (int userNameNum = 2; !UserNameServlet.checkUserName(userAuthentications, userName); userNameNum++) {
                userName = userNameLetters;
                if (userNameNum < 10) userName += "0";
                if (userNameNum < 100) userName += "0";
                userName += userNameNum;
            }
            return userName;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
