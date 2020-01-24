package ictgradschool.project;

import ictgradschool.project.User;

import java.sql.*;

public class UserDAO {
    public static boolean insertANewUser(User user, Connection conn) throws SQLException {
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
}
