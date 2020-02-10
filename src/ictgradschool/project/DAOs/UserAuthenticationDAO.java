package ictgradschool.project.DAOs;

import ictgradschool.project.Password;
import ictgradschool.project.UserAuthentication;
import ictgradschool.project.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.jdo.Query.SQL;

//@author: Peter He

public class UserAuthenticationDAO {

    /**
     * This method gets the full table of USer Authentication data
     *
     * @param conn The DB connection to use.
     * @return a list of the UAs.
     * @throws SQLException
     */
    public static List<UserAuthentication> getAllUserAuthentications(Connection conn) throws SQLException {
        List<UserAuthentication> allUAs = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM user_authentication")) {
                while (rs.next()) {
                    allUAs.add(createANewUserAuthentication(rs));
                }
            }
        }
        return allUAs;
    }

    /**
     * These three methods retrieve the User Authentication data for the specified user, defined by either their
     * username or third party (Google) ID.
     *
     * @param conn The DB connection to use.
     * @return the user's UA.
     * @throws SQLException
     */
    public static UserAuthentication getUserAuthenticationByUserName(Connection conn, String userName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user_authentication WHERE userName = ?")) {
            return getUserAuthentication(s, userName);
        }
    }

    public static UserAuthentication getUserAuthenticationByThirdPartyId(Connection conn, String thirdPartyId) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user_authentication WHERE thirdPartyId = ?")) {
            return getUserAuthentication(s, thirdPartyId);
        }
    }

    private static UserAuthentication getUserAuthentication(PreparedStatement s, String param) throws SQLException {
        s.setString(1, param);
        try (ResultSet r = s.executeQuery()) {
            if (r.next()) {
                return createANewUserAuthentication(r);
            } else {
                return null;
            }
        }
    }

    /**
     * This method inserts a new User Authentication into the database.
     *
     * @param ua   The User Authentication details to insert.
     * @param conn The DB connection to use.
     * @return whether the insertion was successful.
     * @throws SQLException
     */
    public static boolean insertANewUserAuthentication(UserAuthentication ua, Connection conn) throws SQLException {
//        insert ua without an ID number.
        try (PreparedStatement s = conn.prepareStatement("INSERT INTO user_authentication (userName, hashedPassword, salt, hashNum, thirdPartyId) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, ua.getUserName());
            s.setString(2, ua.getHashedPassword());
            s.setString(3, ua.getSalt());
            s.setInt(4, ua.getHashNum());
            if (ua.getThirdPartyId() == null)
                s.setNull(5, java.sql.Types.INTEGER);
            else
                s.setString(5, ua.getThirdPartyId());

            if (s.executeUpdate() == 0)
                return false;

//            get the ID number for the ua
            try (ResultSet keys = s.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                ua.setUserId(id);

                return true;
            }
        }
    }

    /**
     * This method updates the username for the specified user.
     *
     * @param ua          The User Authentication details for the user.
     * @param conn        The DB connection to use.
     * @param newUserName The new user name to set.
     * @return Whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateUserName(UserAuthentication ua, Connection conn, String newUserName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("UPDATE user_authentication SET userName = ? WHERE userId = ?")) {
            s.setString(1, newUserName);
            s.setInt(2, ua.getUserId());

            ua.setUserName(newUserName);

            int rowsAffected = s.executeUpdate();

            return rowsAffected != 0;
        }
    }

    /**
     * This method deletes the User Authentication data from the database.
     *
     * @param user The UA to delete.
     * @param conn The DB connection to use.
     * @return whether the deletion was successful.
     * @throws SQLException
     */
    public static boolean deleteUser(UserAuthentication user, Connection conn) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("DELETE FROM user_authentication WHERE userId =?")) {
            s.setInt(1, user.getUserId());

            return s.executeUpdate() != 0;
        }
    }

    /**
     * This method updates the user's password.
     *
     * @param ua                   The UA to update.
     * @param conn                 The DB connection to use.
     * @param newPasswordPlainText The new password to set in plaintext.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updatePassword(UserAuthentication ua, Connection conn, String newPasswordPlainText) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("UPDATE user_authentication SET hashedPassword = ?, salt=?, hashNum= ? WHERE userId = ?")) {

            Password password = new Password(newPasswordPlainText);

            ua.setSalt(password.getSalt());
            ua.setHashNum(password.getHashNum());
            ua.setHashedPassword(password.getHashedPassword());

            s.setString(1, password.getHashedPassword());
            s.setString(2, password.getSalt());
            s.setInt(3, password.getHashNum());
            s.setInt(4, ua.getUserId());

            int rowsAffected = s.executeUpdate();

            return rowsAffected != 0;
        }


    }

    /**
     * This method uses the ResultSet from a SQL query to generate a UserAuthentication object.
     *
     * @param r The ResultSet.
     * @return the UA.
     * @throws SQLException
     */
    private static UserAuthentication createANewUserAuthentication(ResultSet r) throws SQLException {
        return new UserAuthentication(r.getInt(1),
                r.getString(2),
                r.getString(3),
                r.getString(4),
                r.getInt(5),
                r.getString(6));
    }

    /**
     * This method returns all Users whose usernames match the search string.
     *
     * @param conn   The DB connection to use.
     * @param search the search string.
     * @return the list of users.
     * @throws SQLException
     */
    public static List<UserAuthentication> getUserAuthenticationsBySearch(Connection conn, String search) throws SQLException {
        List<UserAuthentication> uas = new ArrayList<>();
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user_authentication WHERE userName LIKE ?")) {
            s.setString(1, "%" + search + "%");
            try (ResultSet r = s.executeQuery()) {
                while (r.next()) {
                    UserAuthentication ua = createANewUserAuthentication(r);
                    uas.add(ua);
                }
            }
        }

        return uas;
    }
}
