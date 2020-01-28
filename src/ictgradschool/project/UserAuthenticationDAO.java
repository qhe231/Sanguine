package ictgradschool.project;

import ictgradschool.project.util.PasswordUtil;

import java.sql.*;
import java.util.Arrays;

//@author: Peter He

public class UserAuthenticationDAO {

    public static boolean insertANewUserAuthentication(UserAuthentication ua, Connection conn) throws SQLException {

//        insert ua without an ID number.
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_authentication (userName, hashedPassword, salt, hashNum) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ua.getUserName());
            stmt.setString(2, ua.getHashedPassword());
            stmt.setString(3, ua.getSalt());
            stmt.setInt(4, ua.getHashNum());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

//            get the ID number for the ua
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                ua.setUserId(id);

                return true;
            }
        }
    }

    public static boolean updateUserName(UserAuthentication ua, Connection conn, String newUserName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET userName = ? WHERE id = ?")) {
            stmt.setString(1, newUserName);
            stmt.setInt(2, ua.getUserId());

            ua.setUserName(newUserName);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean deleteUser(UserAuthentication user, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_authentication WHERE userId =?")) {
            stmt.setInt(1, user.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updatePassword(UserAuthentication ua, Connection conn, String newPassword) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET hashedPassword = ?, salt=?, hashNum= ? WHERE userId = ?")) {

//            create new salt
            byte[] newSaltByte = PasswordUtil.getNextSalt();
            String newSalt = Arrays.toString(newSaltByte);
            ua.setSalt(newSalt);

//            generate new hashNum
            int newHashNum = (int) (Math.random() * 100000) + 1000000;
            ua.setHashNum(newHashNum);

//            get mew hashedPassword
            byte[] newHash = PasswordUtil.hash(newPassword.toCharArray(), newSaltByte, newHashNum);
            String newHashedPassword = Arrays.toString(newHash);
            ua.setHashedPassword(newHashedPassword);

            stmt.setString(1, newHashedPassword);
            stmt.setString(2, newSalt);
            stmt.setInt(3, newHashNum);
            stmt.setInt(4, ua.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }
}
