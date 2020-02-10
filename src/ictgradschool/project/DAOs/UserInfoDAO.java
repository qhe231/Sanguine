package ictgradschool.project.DAOs;

import ictgradschool.project.UserInfo;

import java.sql.*;

//@author: Peter He

public class UserInfoDAO {

    /**
     * This method returns the UserInfo matching the provided User ID.
     *
     * @param conn   The DB connection to use.
     * @param userId The ID of the User to retrieve.
     * @return The UserInfo of the provided user.
     * @throws SQLException
     */
    public static UserInfo getUserInfoById(Connection conn, int userId) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user_info INNER JOIN user_authentication USING (userId) WHERE userId = ?")) {
            s.setInt(1, userId);
            try (ResultSet r = s.executeQuery()) {
                if (r.next())
                    return createANewUserInfo(r);
                else
                    return null;
            }
        }
    }

    /**
     * This method returns the UserInfo matching the provided Username.
     *
     * @param conn     The DB connection to use.
     * @param userName The username of the User to retrieve.
     * @return The UserInfo of the provided user.
     * @throws SQLException
     */
    public static UserInfo getUserInfoByUserName(Connection conn, String userName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM user_info INNER JOIN user_authentication USING (userId) WHERE userName = ?")) {
            s.setString(1, userName);
            try (ResultSet r = s.executeQuery()) {
                if (r.next())
                    return createANewUserInfo(r);
                else
                    return null;
            }
        }
    }

    /**
     * This method inserts a new UserInfo into the database.
     *
     * @param ui   The UserInfo to insert.
     * @param conn The DB connection to use.
     * @return whether the insert was successful.
     * @throws SQLException
     */
    public static boolean insertANewUserInfo(UserInfo ui, Connection conn) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("INSERT INTO user_info VALUES (?,?,?,?,?,?,?,?)")) {
            s.setInt(1, ui.getUserId());
            s.setString(2, ui.getBlogName());
            s.setString(3, ui.getFirstName());
            s.setString(4, ui.getLastName());
            s.setDate(5, (Date) ui.getDateOfBirth());
            s.setString(6, ui.getAvatarURL());
            s.setString(7, ui.getProfile());
            s.setString(8, ui.getTheme());

            int rowsAffected = s.executeUpdate();

            return rowsAffected != 0;
        }
    }

    /**
     * This method updates the user's blog name.
     *
     * @param ui          The UserInfo to update.
     * @param conn        The DB connection to use.
     * @param newBlogName The new blog name to use.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateBlogName(UserInfo ui, Connection conn, String newBlogName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET blogName = ? WHERE userId = ?")) {
            s.setString(1, newBlogName);
            s.setInt(2, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setBlogName(newBlogName);

            return rowsAffected != 0;
        }
    }

    /**
     * This method updates the user's first and last name.
     *
     * @param ui           The UserInfo to update.
     * @param conn         The DB connection to use.
     * @param newFirstName The new first name to use.
     * @param newLastName  The new surname to use.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateName(UserInfo ui, Connection conn, String newFirstName, String newLastName) throws SQLException {


        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET firstName = ?, lastName = ? WHERE userId = ?")) {
            s.setString(1, newFirstName);
            s.setString(2, newLastName);
            s.setInt(3, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setFirstName(newFirstName);
            ui.setLastName(newLastName);

            return rowsAffected != 0;
        }

    }

    /**
     * This method updates the user's date of birth.
     *
     * @param ui             The UserInfo to update.
     * @param conn           The DB connection to use.
     * @param newDateOfBirth The new date of birth to use.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateDateOfBirth(UserInfo ui, Connection conn, Date newDateOfBirth) throws SQLException {

        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET dateOfBirth = ? WHERE userId = ?")) {
            s.setDate(1, newDateOfBirth);
            s.setInt(2, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setDateOfBirth(newDateOfBirth);

            return rowsAffected != 0;

        }
    }

    /**
     * This method updates the user's avatar.
     *
     * @param ui           The UserInfo to update.
     * @param conn         The DB connection to use.
     * @param newAvatarURL The new avatar URL to use.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateAvatarURL(UserInfo ui, Connection conn, String newAvatarURL) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET avatarURL = ? WHERE userId = ?")) {
            s.setString(1, newAvatarURL);
            s.setInt(2, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setAvatarURL(newAvatarURL);

            return rowsAffected != 0;

        }
    }

    /**
     * This method updates the user's profile.
     *
     * @param ui         The UserInfo to update.
     * @param conn       The DB connection to use.
     * @param newProfile The new profile string to use.
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateProfile(UserInfo ui, Connection conn, String newProfile) throws SQLException {

        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET profile = ? WHERE userId = ?")) {
            s.setString(1, newProfile);
            s.setInt(2, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setProfile(newProfile);

            return rowsAffected != 0;
        }
    }

    /**
     * This method uses the ResultSet from a SQL query to generate a UserAuthentication object.
     *
     * @param r The ResultSet.
     * @return the UserInfo.
     * @throws SQLException
     */
    public static UserInfo createANewUserInfo(ResultSet r) throws SQLException {
        return new UserInfo(r.getInt(1),
                r.getString(2),
                r.getString(3),
                r.getString(4),
                r.getDate(5),
                r.getString(6),
                r.getString(7),
                r.getString(8),
                r.getString(9));
    }

    /**
     * This method updates the user's theme.
     *
     * @param ui    The UserInfo to update.
     * @param conn  The DB connection to use.
     * @param theme
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean updateTheme(UserInfo ui, Connection conn, String theme) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("UPDATE user_info SET theme = ? WHERE userId = ?")) {
            s.setString(1, theme);
            s.setInt(2, ui.getUserId());

            int rowsAffected = s.executeUpdate();

            ui.setTheme(theme);

            return rowsAffected != 0;
        }
    }
}

