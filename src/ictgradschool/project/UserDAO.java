package ictgradschool.project;

import ictgradschool.project.util.PasswordUtil;

import java.sql.*;
import java.util.Arrays;

//@author: Peter He

public class UserDAO {

    public static boolean insertANewUser(User user, Connection conn) throws SQLException {

//        insert the user without an ID number.
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_authentication (username, avatarURL, hashedPassword, salt, hashNum) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getAvatarUrl());
            stmt.setString(3, user.getHashedPassword());
            stmt.setString(4, user.getSalt());
            stmt.setInt(5, user.getHashNum());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

//            get the ID number for the user
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                user.setUserId(id);

                return true;
            }
        }
    }

    public static boolean updateUserName(User user, Connection conn, String newUserName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET username = ? WHERE id = ?")) {
            stmt.setString(1, newUserName);
            stmt.setInt(2, user.getUserId());

            user.setUserName(newUserName);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updateBlogName(User user, Connection conn, String newBlogName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET blogName = ? WHERE id = ?")) {
            stmt.setString(1, newBlogName);
            stmt.setInt(2, user.getUserId());

            user.setBlogName(newBlogName);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean deleteUser(User user, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_authentication WHERE userId =?")) {
            stmt.setInt(1, user.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updatePassword(User user, Connection conn, String newPassword) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET hashedPassword = ?, salt=?, hashNum= ? WHERE userId = ?")) {

//            create new salt
            byte[] newSaltByte = PasswordUtil.getNextSalt();
            String newSalt = Arrays.toString(newSaltByte);
            user.setSalt(newSalt);

//            generate new hashNum
            int newHashNum = (int) (Math.random() * 100000) + 1000000;
            user.setHashNum(newHashNum);

//            get mew hashedPassword
            byte[] newHash = PasswordUtil.hash(newPassword.toCharArray(), newSaltByte, newHashNum);
            String newHashedPassword = Arrays.toString(newHash);
            user.setHashedPassword(newHashedPassword);

            stmt.setString(1, newHashedPassword);
            stmt.setString(2, newSalt);
            stmt.setInt(3, newHashNum);
            stmt.setInt(4, user.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }
}
