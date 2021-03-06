package ictgradschool.project.DAOs;

import ictgradschool.project.Article;
import ictgradschool.project.ArticleReaction;
import ictgradschool.project.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    /**
     * This method returns a list of Articles.
     *
     * @param conn     The DB connection to use.
     * @param parentId If we only want to return children of a specified Article, this is its ID; -1 if we want root articles.
     * @param userId   If we only want to return Articles written by a specific user, this is their ID; -1 if we want posts from all users.
     * @return list of all articles
     * @throws SQLException
     */
    public static List<Article> getArticles(Connection conn, int parentId, int userId, boolean descOrder) throws SQLException {
        List<Article> articles = new ArrayList<>();

        String sqlString = "select * from articles_and_comments where parentId " + ((parentId == -1) ? "is" : "=") + " ?";
        if (userId != -1)
            sqlString += " and userBelongedId = ?";
        sqlString += " order by datePosted " + (descOrder ? "desc" : "asc") + ";";

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
                    List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, articleId);
                    Article a = new Article(articleId, author, r.getString(3), r.getString(4), r.getTimestamp(2),
                            getArticles(conn, articleId, -1, false), parentId, r.getTimestamp(7), reactions);
                    articles.add(a);
                }
            }
        }

        return articles;
    }

    /**
     * This method returns the given Article.
     *
     * @param conn      The DB connection to use.
     * @param articleId The ID of the Article to retrieve.
     * @return the Article requested
     * @throws SQLException
     */
    public static Article getSpecificArticle(Connection conn, int articleId) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("select * from articles_and_comments where id = ?;")) {
            s.setInt(1, articleId);

            s.execute();
            try (ResultSet r = s.getResultSet()) {
                if (r.next()) {
                    UserInfo author = UserInfoDAO.getUserInfoById(conn, r.getInt(6));
                    List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, articleId);
                    int parentId = r.getInt(5);
                    if (r.wasNull())
                        parentId = -1;
                    return new Article(articleId, author, r.getString(3), r.getString(4), r.getTimestamp(2),
                            getArticles(conn, articleId, -1, false), parentId, r.getTimestamp(7), reactions);
                }
            }
        }

        return null;
    }

    /**
     * This method adds a new Article into the database.
     *
     * @param conn    The DB connection to use.
     * @param article The Article to add to the database.
     * @return whether the insert was successful.
     * @throws SQLException
     */
    public static boolean insertArticle(Connection conn, Article article) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("insert into articles_and_comments (datePosted, title, content, parentId, userBelongedId, timeEdited) values (?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            s.setTimestamp(1, article.getPostedTimeStamp());
            s.setString(2, article.getTitle());
            s.setTimestamp(6, article.getPostedTimeStamp());
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
     * This method updates the given article's database entry.
     *
     * @param conn    The DB connection to use.
     * @param article The Article to update in the database (if Article does not already exist, use insertArticle instead).
     * @return whether the update was successful.
     * @throws SQLException
     */
    public static boolean editArticle(Connection conn, Article article) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("update articles_and_comments set content = ? , timeEdited = ?, datePosted = ? where id = ?;", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, article.getContent());

//            set edited timestamp
            s.setTimestamp(2, article.getEditedTimeStamp());
            s.setTimestamp(3, article.getPostedTimeStamp());
            s.setInt(4, article.getArticleId());

            return (s.executeUpdate() != 0);
        }
    }

    /**
     * This method deletes an article from the database.
     *
     * @param conn      The DB connection to use.
     * @param articleID The ID of the Article to remove from the database.
     * @return Whether deletion was successful.
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

    /**
     * This method returns a list of articles based on a provided search string.
     *
     * @param conn   The DB connection to use.
     * @param search The search string.
     * @return all Articles where the search string is contained in either the title or content.
     * @throws SQLException
     */
    public static List<Article> getArticlesBySearch(Connection conn, String search) throws SQLException {
        List<Article> articles = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM articles_and_comments WHERE title LIKE ? OR content LIKE ? ORDER BY datePosted DESC;")) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");

            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    Article a = createAnArticle(conn, r);
                    articles.add(a);
                }
            }
        }

        return articles;
    }

    /**
     * This method returns a list of comments based on a provided userId.
     *
     * @param conn   The DB connection to use.
     * @param userId The userId of the specific user.
     * @return all the comments that a specific user wrote.
     * @throws SQLException
     */

    public static List<Article> getAllCommentsForSpecificUser(Connection conn, int userId) throws SQLException {
        List<Article> allComments = new ArrayList<>();
        try (PreparedStatement s = conn.prepareStatement("select * from articles_and_comments where userBelongedId = ? and parentId is not null;")) {
            s.setInt(1, userId);
            try (ResultSet r = s.executeQuery()) {
                while (r.next()) {
                    Article a = createAnArticle(conn, r);
                    allComments.add(a);
                }
            }
        }
        return allComments;
    }

    /**
     * This method returns an Article based on a provided ResultSet
     *
     * @param conn The DB connection to use.
     * @param r    The provided ResultSet.
     * @return an Article based on the data in the ResultSet.
     * @throws SQLException
     */

    public static Article createAnArticle(Connection conn, ResultSet r) throws SQLException {
        int articleId = r.getInt(1);
        UserInfo author = UserInfoDAO.getUserInfoById(conn, r.getInt(6));
        List<ArticleReaction> reactions = ArticleReactionDAO.getReactionsToArticle(conn, articleId);

        Article a = new Article(articleId, author, r.getString(3), r.getString(4), r.getTimestamp(2),
                getArticles(conn, articleId, -1, false), r.getInt(5), r.getTimestamp(7), reactions);

        return a;
    }

}



