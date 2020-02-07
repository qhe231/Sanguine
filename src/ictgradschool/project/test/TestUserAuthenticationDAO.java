package ictgradschool.project.test;

import ictgradschool.project.UserAuthentication;
import ictgradschool.project.UserAuthenticationDAO;
import ictgradschool.project.util.DBConnectionUtils;
import ictgradschool.project.util.PasswordUtil;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * If there are already some data in the table before this test, method testGetAllUserAuthentications()
 * and method  testGetUserAuthenticationsBySearch() will be inaccurate. The results of the two methods
 * getAllUserAuthentications() and getUserAuthenticationsBySearch() vary when the data are different.
 * As We do not know what the data in the table are like, we cannot set an expected result for the test.
 */


public class TestUserAuthenticationDAO {
    private Connection conn;


    @BeforeClass
    public static void login() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO user_authentication (userName, hashedPassword, salt, hashNum, thirdPartyId) VALUES ('a', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 1),('b', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 2),('ac', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 3),('e', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 4);");
        }
    }

    @Before
    public void setUp() throws IOException, SQLException {
        conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
    }

    @Test
    public void testGetAllUserAuthentications() throws SQLException {
        List<UserAuthentication> allUAs = UserAuthenticationDAO.getAllUserAuthentications(conn);
        String usernames = "";
        for (UserAuthentication ua : allUAs) {
            usernames += ua.getUserName();
        }
        assertEquals("abace", usernames);
    }

    @Test
    public void testGetUserAuthenticationByValidUserName() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "a");
        String username = ua.getUserName();
        assertEquals("a", username);
    }

    @Test
    public void testGetUserAuthenticationByInvalidUserName() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "z");
        assertNull(ua);
    }

    @Test
    public void testGetUseAuthenticationByValidThirdPartyId() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUseAuthenticationByThirdPartyId(conn, "1");
        String username = ua.getUserName();
        assertEquals("a", username);
    }

    @Test
    public void testGetUseAuthenticationByInvalidThirdPartyId() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUseAuthenticationByThirdPartyId(conn, "0");
        assertNull(ua);
    }

    @Test
    public void testInsertANewUserAuthentication() throws SQLException {
        UserAuthentication ua = new UserAuthentication(12, "d", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678", "qwertyuiop", 2);
        UserAuthenticationDAO.insertANewUserAuthentication(ua, conn);
        UserAuthentication ua2 = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "d");

        assertNotNull(ua2);
        assertEquals(ua.getUserId(), ua2.getUserId());
        assertEquals(ua.getUserName(), ua2.getUserName());
        assertEquals(ua.getHashedPassword(), ua2.getHashedPassword());
        assertEquals(ua.getSalt(), ua2.getSalt());
        assertSame(ua.getHashNum(), ua2.getHashNum());

    }

    @Test
    public void testUpdateUserName() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUseAuthenticationByThirdPartyId(conn, "2");
        UserAuthenticationDAO.updateUserName(ua, conn, "B");
        String username = ua.getUserName();

        assertEquals("B", username);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "e");
        UserAuthenticationDAO.deleteUser(ua, conn);
        UserAuthentication ua2 = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "e");
        assertNull(ua2);
    }

    @Test
    public void testUpdatePassword() throws SQLException {
        UserAuthentication ua = UserAuthenticationDAO.getUserAuthenticationByUserName(conn, "a");
        UserAuthenticationDAO.updatePassword(ua, conn, "abc");
        byte[] salt = PasswordUtil.base64Decode(ua.getSalt());
        int hashNum = ua.getHashNum();
        String hash = PasswordUtil.base64Encode(PasswordUtil.hash("abc".toCharArray(), salt, hashNum));

        assertEquals(hash, ua.getHashedPassword());
    }

    @Test
    public void testGetUserAuthenticationsBySearch() throws SQLException {
        List<UserAuthentication> uas = UserAuthenticationDAO.getUserAuthenticationsBySearch(conn, "a");
        String expectedUsernames = "";
        for (UserAuthentication ua : uas) {
            expectedUsernames += ua.getUserName();
        }

        assertEquals("aac", expectedUsernames);
    }


    @AfterClass
    public static void logout() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM user_authentication WHERE userName ='a' OR userName ='b' OR userName ='ac' OR userName = 'd' OR userName ='B' OR userName ='c' OR userName ='e' ;");
        }
    }


}
