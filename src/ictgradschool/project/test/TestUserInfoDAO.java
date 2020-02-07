package ictgradschool.project.test;

import ictgradschool.project.UserInfo;
import ictgradschool.project.UserInfoDAO;
import ictgradschool.project.util.DBConnectionUtils;
import org.junit.*;

import javax.jws.soap.SOAPBinding;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUserInfoDAO {
    private Connection conn;
    private UserInfo testUI;

    @BeforeClass
    public static void login() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO user_authentication VALUES (1000,'a', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 1),(1001,'b', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 2),(1002,'ac', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 3),(1003,'e', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 4);");
            stmt.executeUpdate("INSERT INTO user_info VALUES " +
                    "(1000,'BlogA','fnameA','lnameA','1980-02-05','urlA','profileA','themeA')," +
                    "(1001,'BlogB','fnameB','lnameB','1996-02-05','urlB','profileB','themeB')," +
                    "(1002,'BlogC','fnameC','lnameC','2003-12-05','urlC','profileC','themeC');");
        }
    }

    @Before
    public void setUp() throws IOException, SQLException {
        conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        testUI = UserInfoDAO.getUserInfoById(conn, 1001);
    }

    @Test
    public void testGetUserInfoByValidId() throws SQLException {
        UserInfo ui = UserInfoDAO.getUserInfoById(conn, 1000);
        assertEquals("fnameA", ui.getFirstName());
    }

    @Test
    public void testGetUserInfoByInvalidId() throws SQLException {
        UserInfo ui = UserInfoDAO.getUserInfoById(conn, 2000);
        assertNull(ui);
    }

    @Test
    public void testGetUserInfoByValidUserName() throws SQLException {
        UserInfo ui = UserInfoDAO.getUserInfoByUserName(conn, "a");
        assertEquals("fnameA", ui.getFirstName());
    }

    @Test
    public void testGetUserInfoByInvalidUserName() throws SQLException {
        UserInfo ui = UserInfoDAO.getUserInfoByUserName(conn, "z");
        assertNull(ui);
    }

    @Test
    public void testInsertANewUserInfo() throws SQLException {
        UserInfo ui = new UserInfo(1003, "BlogD", "fnameD", "lnameD", Date.valueOf("2006-05-24"), "urlD", "profileD", "themeD", "e");
        UserInfoDAO.insertANewUserInfo(ui, conn);
        UserInfo ui2 = UserInfoDAO.getUserInfoById(conn, 1003);

        assertNotNull(ui2);
        assertEquals(ui.getUserId(), ui2.getUserId());
        assertEquals(ui.getBlogName(), ui2.getBlogName());
        assertEquals(ui.getFirstName(), ui2.getFirstName());
        assertEquals(ui.getLastName(), ui2.getLastName());
        assertEquals(ui.getDateOfBirth(), ui2.getDateOfBirth());
        assertEquals(ui.getAvatarURL(), ui2.getAvatarURL());
        assertEquals(ui.getProfile(), ui2.getProfile());
        assertEquals(ui.getTheme(), ui2.getTheme());
    }

    @Test
    public void testUpdateBlogName() throws SQLException {
        UserInfoDAO.updateBlogName(testUI, conn, "NewBlog");
        assertEquals("NewBlog", testUI.getBlogName());
    }

    @Test
    public void testUpdateName() throws SQLException {
        UserInfoDAO.updateName(testUI, conn, "NewFname", "NewLname");
        assertEquals("NewFname", testUI.getFirstName());
        assertEquals("NewLname", testUI.getLastName());
    }

    @Test
    public void testUpdateAvatarURL() throws SQLException {
        UserInfoDAO.updateAvatarURL(testUI, conn, "NewURL");
        assertEquals("NewURL", testUI.getAvatarURL());
    }

    @Test
    public void testUpdateProfile() throws SQLException {
        UserInfoDAO.updateProfile(testUI, conn, "NewProfile");
        assertEquals("NewProfile", testUI.getProfile());
    }

    @Test
    public void testUpdateTheme() throws SQLException {
        UserInfoDAO.updateTheme(testUI, conn, "NewTheme");
        assertEquals("NewTheme", testUI.getTheme());
    }

    @AfterClass
    public static void logout() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM user_authentication WHERE userId =1000 OR userId =1001 OR userId =1002 OR userId =1003;");
        }
    }
}
