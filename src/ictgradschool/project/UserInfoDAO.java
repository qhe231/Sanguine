package ictgradschool.project;

import java.sql.*;

//@author: Peter He

public class UserInfoDAO {

    public static UserInfo getUserInfoById(Connection conn, int userId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_info WHERE userId = ? ")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return new UserInfo(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getString(7));
            }
        }
    }


    public static boolean insertANewUserAuthentication(UserInfo ui, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_info VALUES (?,?,?,?,?,?,?)")) {
            stmt.setInt(1, ui.getUserId());
            stmt.setString(2, ui.getBlogName());
            stmt.setString(3, ui.getFirstName());
            stmt.setString(4, ui.getLastName());
            stmt.setDate(5, (Date) ui.getDateOfBirth());
            stmt.setString(6, ui.getAvatarURL());
            stmt.setString(7, ui.getProfile());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }


    public static boolean updateBlogName(UserInfo ui, Connection conn, String newBlogName) throws SQLException {
        ui.setBlogName(newBlogName);

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_info SET blogName = ? WHERE id = ?")) {
            stmt.setString(1, newBlogName);
            stmt.setInt(2, ui.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updateName(UserInfo ui, Connection conn, String newFirstName, String newLastName) throws SQLException {

        ui.setFirstName(newFirstName);
        ui.setLastName(newLastName);

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_info SET firstName = ?, lastName = ? WHERE id = ?")) {
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setInt(3, ui.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updateDateOfBirth(UserInfo ui, Connection conn, Date newDateOfBirth) throws SQLException {
        ui.setDateOfBirth(newDateOfBirth);

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_info SET dateOfBirth = ? WHERE userId = ?")) {
            stmt.setDate(1, newDateOfBirth);
            stmt.setInt(2, ui.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updateAvatarURL(UserInfo ui, Connection conn, String newAvatarURL) throws SQLException {
        ui.setAvatarURL(newAvatarURL);
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_info SET avatarURL = ? WHERE userId = ?")) {
            stmt.setString(1, newAvatarURL);
            stmt.setInt(2, ui.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }
    }

    public static boolean updateProfile(UserInfo ui, Connection conn, String newProfile) throws SQLException {
        ui.setProfile(newProfile);
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_info SET profile = ? WHERE userId = ?")) {
            stmt.setString(1, newProfile);
            stmt.setInt(2, ui.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }

    }
}

