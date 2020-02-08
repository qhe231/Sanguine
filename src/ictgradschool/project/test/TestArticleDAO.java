package ictgradschool.project.test;

import ictgradschool.project.Article;
import ictgradschool.project.ArticleDAO;
import ictgradschool.project.UserInfo;
import ictgradschool.project.UserInfoDAO;
import ictgradschool.project.util.DBConnectionUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArticleDAO {
    private Connection conn;
    private Article testArticle;

    @BeforeClass
    public static void login() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO user_authentication VALUES (1000,'a', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 1),(1001,'b', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 2),(1002,'ac', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 3),(1003,'e', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1, 4);");

            stmt.executeUpdate("INSERT INTO user_info VALUES " +
                    "(1000,'BlogA','fnameA','lnameA','1980-02-05','urlA','profileA','themeA')," +
                    "(1001,'BlogB','fnameB','lnameB','1996-02-05','urlB','profileB','themeB')," +
                    "(1002,'BlogC','fnameC','lnameC','2003-12-05','urlC','profileC','themeC')," +
                    "(1003,'BlogD','fnameD','lnameD','2003-12-05','urlD','profileD','themeD');");

            stmt.executeUpdate("INSERT INTO articles_and_comments VALUES " +
                    "(2000,'1999-01-23 09:01:15','TitleA','contentA',NULL ,1000,'1999-01-23 11:19:38')," +
                    "(2001,'1999-01-23 09:25:15','TitleB','Special',2000 ,1001,'1999-02-23 23:16:38')," +
                    "(2002,'1999-01-25 09:01:15','Special','contentC',2001 ,1002,'1999-01-25 11:19:38')," +
                    "(2003,'1999-02-18 11:19:38','TitleD','contentD',NULL ,1000,'1999-01-25 11:19:38')," +
                    "(2004,'1999-01-28 09:01:15','TitleE','contentE',2000 ,1003,'1999-01-28 11:58:32')," +
                    "(2005,'1999-01-30 09:01:15','TitleF','contentF',2000 ,1001,'1999-01-31 11:19:38')," +
                    "(2006,'1999-01-30 09:01:15','TitleG','contentG',2005 ,1003,'1999-01-31 11:19:38');");
        }
    }

    @Before
    public void setUp() throws IOException, SQLException {
        conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        testArticle = ArticleDAO.getSpecificArticle(conn, 2000);
    }

    @Test
    public void testAGetAllRootArticles() throws SQLException {
        List<Article> articles = ArticleDAO.getArticles(conn, -1, -1);
        String titles = getTitles(articles);
        String lastTitles = titles.substring(titles.length() - 12);
        assertEquals("TitleDTitleA", lastTitles);
    }

    @Test
    public void testBGetAllChildrenOfAnArticle() throws SQLException {
        List<Article> articles = ArticleDAO.getArticles(conn, 2000, -1);
        String titles = getTitles(articles);
        assertEquals("TitleFTitleETitleB", titles);
    }

    @Test
    public void testCGetAllRootArticlesBelongedToAUser() throws SQLException {
        List<Article> articles = ArticleDAO.getArticles(conn, -1, 1000);
        String titles = getTitles(articles);
        assertEquals("TitleDTitleA", titles);
    }

    @Test
    public void testDGetAllChildrenOfAnArticleBelongedToAUser() throws SQLException {
        List<Article> articles = ArticleDAO.getArticles(conn, 2000, 1001);
        String titles = getTitles(articles);
        assertEquals("TitleFTitleB", titles);
    }

    @Test
    public void testEGetSpecificArticle() throws SQLException {
        Article article = ArticleDAO.getSpecificArticle(conn, 2000);
        assertNotNull(article);
        assertEquals("TitleA", article.getTitle());
    }

    @Test
    public void testFInsertArticle() throws SQLException {
        UserInfo author = UserInfoDAO.getUserInfoById(conn, 1002);
        Article article1 = new Article(author, "TitleH", "ContentH", Timestamp.valueOf("2020-02-28 06:01:23"), null, -1);
        ArticleDAO.insertArticle(conn, article1);
        Article article2 = ArticleDAO.getSpecificArticle(conn, article1.getArticleId());

        assertNotNull(article2);
        assertEquals(article1.getArticleId(), article2.getArticleId());
        assertEquals(article1.getPostedTimeStamp(), article2.getPostedTimeStamp());
        assertEquals(article1.getTitle(), article2.getTitle());
        assertEquals(article1.getContent(), article2.getContent());
        assertEquals(article1.getParentId(), article2.getParentId());
        assertEquals(article1.getAuthor().getUserId(), article2.getAuthor().getUserId());
        assertEquals(article1.getEditedTimeStamp(), article2.getEditedTimeStamp());

    }

    @Test
    public void testGEditArticle() throws SQLException {
        Article article1 = ArticleDAO.getSpecificArticle(conn, 2003);
        article1.setContent("NewContent");
        article1.setEditedTimeStamp(Timestamp.valueOf("2020-02-26 16:56:59"));
        ArticleDAO.editArticle(conn, article1);
        Article article2 = ArticleDAO.getSpecificArticle(conn, 2003);

        assertEquals("NewContent", article2.getContent());
        assertEquals("2020-02-26 16:56:59.0", article2.getEditedTimeStamp().toString());
    }

    @Test
    public void testHDeleteArticles() throws SQLException {
        ArticleDAO.deleteArticle(conn, 2006);
        Article article = ArticleDAO.getSpecificArticle(conn, 2006);
        assertNull(article);
    }

    @Test
    public void testIGetArticlesBySearch() throws SQLException {
        List<Article> articles = ArticleDAO.getArticlesBySearch(conn, "special");
        String titles = getTitles(articles);
        String lastTitles = titles.substring(titles.length() - 13);
        assertEquals("SpecialTitleB", lastTitles);
    }

    @AfterClass
    public static void logout() throws IOException, SQLException {
        Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM user_authentication WHERE userId =1000 OR userId =1001 OR userId =1002 OR userId =1003;");
        }
        conn.close();
    }

    private String getTitles(List<Article> articles) {
        String titles = "";
        for (Article article : articles) {
            titles += article.getTitle();
        }
        return titles;
    }
}
