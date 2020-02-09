package ictgradschool.project.test;

import ictgradschool.project.ArticleReaction;
import ictgradschool.project.ArticleReactionDAO;
import ictgradschool.project.util.DBConnectionUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArticleReactionDAO {
    Connection conn;

    @BeforeClass
    public static void login() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO user_authentication VALUES (1000,'a', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 1),(1001,'b', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 2),(1002,'ac', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 3);");

            stmt.executeUpdate("INSERT INTO user_info VALUES " +
                    "(1000,'BlogA','fnameA','lnameA','1980-02-05','urlA','profileA','themeA')," +
                    "(1001,'BlogB','fnameB','lnameB','1996-02-05','urlB','profileB','themeB')," +
                    "(1002,'BlogC','fnameC','lnameC','2003-12-05','urlC','profileC','themeC');");

            stmt.executeUpdate("INSERT INTO articles_and_comments VALUES " +
                    "(2000,'1999-01-23 09:01:15','TitleA','contentA',NULL ,1000,'1999-01-23 11:19:38')," +
                    "(2001,'1999-01-23 09:25:15','TitleB','Special',2000 ,1001,'1999-02-23 23:16:38')," +
                    "(2002,'1999-01-25 09:01:15','Special','contentC',2001 ,1002,'1999-01-25 11:19:38');");

            stmt.executeUpdate("INSERT INTO article_reactions VALUES " +
                    "(2000,1000,1),(2001,1001,2)," +
                    "(2000,1001,2),(2001,1000,1);");
        }

    }

    @Before
    public void setUp() throws IOException, SQLException {
        conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
    }

    @Test
    public void testAGetReactionsToArticleWithIntArgument() throws SQLException {
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, 2000);
        assertSame(2, reactions.size());
    }

    @Test
    public void testAGetReactionsToArticleWithStringArgument() throws SQLException {
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, "2000");
        assertSame(2, reactions.size());
    }

    @Test
    public void testCDeleteReactionToArticle() throws SQLException {
        ArticleReactionDAO.setReactionToArticle(conn, 2000, 1000, 0);
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, 2000);

        assertSame(1, reactions.size());
        assertSame(2, reactions.get(0).getReaction());
    }

    @Test
    public void testDUpdateReactionToArticle() throws SQLException {
        ArticleReactionDAO.setReactionToArticle(conn, 2000, 1001, 1);
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, 2000);
        assertSame(1, reactions.get(0).getReaction());
    }

    @Test
    public void testEInsertReactionToArticle() throws SQLException {
        ArticleReactionDAO.setReactionToArticle(conn, 2002, 1000, 1);
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, 2002);
        assertSame(1, reactions.size());
        assertSame(1, reactions.get(0).getReaction());
    }

    @AfterClass
    public static void logout() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM user_authentication WHERE userId =1000 OR userId =1001 OR userId =1002 OR userId =1003;");
        }
        conn.close();
    }
}
