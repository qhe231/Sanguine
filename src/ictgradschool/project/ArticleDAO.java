package ictgradschool.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleDAO {

    /**
     * @param conn     The DB connection to use
     * @param parentId If we only want to return children of a specified Article, this is its ID; -1 if we want root articles.
     * @param userId   If we only want to return Articles written by a specific user, this is their ID; -1 if we want posts from all users.
     * @return List of all articles
     * @throws SQLException
     */
    public static List<Article> getArticles(Connection conn, int parentId, int userId) throws SQLException {
        List<Article> articles = new ArrayList<>();

        String sqlString = "select * from articles_and_comments where parentId " + ((parentId == -1) ? "is" : "=") + " ?";
        if (userId != -1)
            sqlString += " and userBelongedId = ?";
        sqlString += " order by datePosted desc;";

        try (PreparedStatement s = conn.prepareStatement(sqlString)) {
            if (parentId == -1) {
                s.setNull(1, Types.INTEGER);
            } else
                s.setInt(1, parentId);

            if (userId != -1)
                s.setInt(2, userId);

            s.execute();
            try (ResultSet r = s.getResultSet()) {
                while (r.next()) {
                    int articleId = r.getInt(1);
                    UserInfo author = UserInfoDAO.getUserInfoById(conn, r.getInt(6));
                    Article a = new Article(articleId, author, r.getString(3), r.getString(4), r.getTimestamp(2), getArticles(conn, articleId, -1), parentId);
                    articles.add(a);
                }
            }
        }


        return articles;
    }

    /**
     * @param conn      The DB connection to use
     * @param articleId The ID of the Article to retrieve
     * @return The Article requested
     * @throws SQLException
     */
    public static Article getSpecificArticle(Connection conn, int articleId) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("select * from articles_and_comments where id = ?;")) {
            s.setInt(1, articleId);

            s.execute();
            try (ResultSet r = s.getResultSet()) {
                if (r.next()) {
                    UserInfo author = UserInfoDAO.getUserInfoById(conn, r.getInt(6));
                    int parentId = r.getInt(5);
                    if (r.wasNull())
                        parentId = -1;
                    return new Article(articleId, author, r.getString(3), r.getString(4), r.getTimestamp(2), getArticles(conn, articleId, -1), parentId);
                }
            }
        }

        return null;
    }

    /**
     * @param conn    The DB connection to use
     * @param article The Article to add to the database
     * @return Whether the insert was successful
     * @throws SQLException
     */
    public static boolean insertArticle(Connection conn, Article article) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("insert into articles_and_comments (datePosted, title, content, parentId, userBelongedId) values (?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            s.setTimestamp(1, article.getPostedTimeStamp());
            s.setString(2, article.getTitle());
            s.setString(3, article.getContent());
            int parentId = article.getParentId();
            if (parentId == -1)
                s.setNull(4, Types.INTEGER);
            else
                s.setInt(4, parentId);
            s.setInt(5, article.getAuthor().getUserId());

            int rowsAffected = s.executeUpdate();

            if (rowsAffected == 0)
                return false;

            try (ResultSet keys = s.getGeneratedKeys()) {
                keys.first();
                article.setArticleId(keys.getInt(1));
                return true;
            }
        }
    }

    /**
     * @param conn    The DB connection to use
     * @param article The Article to update in the database (if Article does not already exist, use insertArticle instead)
     * @return Whether the update was successful
     * @throws SQLException
     */
    public static boolean editArticle(Connection conn, Article article) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("update articles_and_comments set content = ? where id = ?;", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, article.getContent());
            s.setInt(2, article.getArticleId());

            return (s.executeUpdate() != 0);
        }
    }

    /**
     * @param conn    The DB connection to use
     * @param article The Article object to remove from the database
     * @return Whether deletion was successful
     * @throws SQLException
     */
    public static boolean deleteArticle(Connection conn, Article article) throws SQLException {
        return deleteArticle(conn, article.getArticleId());
    }

    /**
     * @param conn      The DB connection to use
     * @param articleID The ID of the Article to remove from the database
     * @return Whether deletion was successful
     * @throws SQLException
     */
    public static boolean deleteArticle(Connection conn, int articleID) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("delete from articles_and_comments where id = ?;")) {
            s.setInt(1, articleID);

            if (s.executeUpdate() == 0)
                return false;
        }
        return true;
    }

    public static List<Article> getArticlesBySearch(Connection conn, String search) throws SQLException {

        List<Article> articles = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM articles_and_comments WHERE title LIKE ? OR content LIKE ?")) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int articleId = rs.getInt(1);
                    Timestamp postedTime = rs.getTimestamp(2);
                    String title = rs.getString(3);
                    String content = rs.getString(4);
                    int parentId = rs.getInt(5);
                    UserInfo author = UserInfoDAO.getUserInfoById(conn, rs.getInt(6));
                    List<Article> chilren = getArticles(conn, articleId, -1);
                    Article article = new Article(articleId, author, title, content, postedTime, chilren, parentId);
                    articles.add(article);
                }
            }
        }

        return articles;
    }
}
