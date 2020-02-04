package ictgradschool.project;

import ictgradschool.project.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.jdo.Query.SQL;

//@author: Peter He

public class UserAuthenticationDAO {

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

    public static UserAuthentication getUserAuthenticationByUserName(Connection conn, String userName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_authentication WHERE userName = ?")) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createANewUserAuthentication(rs);
                } else {
                    return null;
                }
            }

        }
    }

    public static UserAuthentication getUseAuthenticationByThirdPartyId(Connection conn, String thirdPartyId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_authentication WHERE thirdPartyId = ?")) {
            stmt.setString(1, thirdPartyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createANewUserAuthentication(rs);
                } else {
                    return null;
                }
            }

        }
    }

    public static boolean insertANewUserAuthentication(UserAuthentication ua, Connection conn) throws SQLException {

//        insert ua without an ID number.
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_authentication (userName, hashedPassword, salt, hashNum, thirdPartyId) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ua.getUserName());
            stmt.setString(2, ua.getHashedPassword());
            stmt.setString(3, ua.getSalt());
            stmt.setInt(4, ua.getHashNum());
            if (ua.getThirdPartyId() == null)
                stmt.setNull(5, java.sql.Types.INTEGER);
            else
                stmt.setString(5, ua.getThirdPartyId());

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
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET userName = ? WHERE userId = ?")) {
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

    public static boolean updatePassword(UserAuthentication ua, Connection conn, String newPasswordPlainText) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user_authentication SET hashedPassword = ?, salt=?, hashNum= ? WHERE userId = ?")) {

            Password password = new Password(newPasswordPlainText);

            ua.setSalt(password.getSalt());
            ua.setHashNum(password.getHashNum());
            ua.setHashedPassword(password.getHashedPassword());

            stmt.setString(1, password.getHashedPassword());
            stmt.setString(2, password.getSalt());
            stmt.setInt(3, password.getHashNum());
            stmt.setInt(4, ua.getUserId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        }


    }

    //    create a new UserAuthentication by the resultSet
    private static UserAuthentication createANewUserAuthentication(ResultSet rs) throws SQLException {
        return new UserAuthentication(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getString(6));
    }

    public static List<UserAuthentication> getUserAuthenticationsBySearch(Connection conn, String search) throws SQLException {
        List<UserAuthentication> uas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_authentication WHERE userName LIKE ?")) {
            stmt.setString(1, "%" + search + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserAuthentication ua = createANewUserAuthentication(rs);
                    uas.add(ua);
                }
            }
        }

        return uas;
    }
}
