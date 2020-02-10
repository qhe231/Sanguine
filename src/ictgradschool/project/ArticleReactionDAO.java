package ictgradschool.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleReactionDAO {

    /**
     * This method returns all the reactions for the provided Article.
     *
     * @param conn      The DB connection to use.
     * @param articleId The ID of the Article to retrieve reactions for.
     * @return a list of ArticleReactions.
     * @throws SQLException
     */
    public static List<ArticleReaction> getReactionsToArticle(Connection conn, int articleId) throws SQLException {
        List<ArticleReaction> reactions = new ArrayList<>();

        try (PreparedStatement s = conn.prepareStatement("select * from article_reactions where article_id=?;")) {
            s.setInt(1, articleId);

            s.execute();

            try (ResultSet r = s.getResultSet()) {
                while (r.next()) {
                    reactions.add(new ArticleReaction(articleId, r.getInt(2), r.getInt(3)));
                }
            }
        }

        return reactions;
    }

    /**
     * This version of getReactionsToArticle accepts articleId as a String rather than an int.
     */
    public static List<ArticleReaction> getReactionsToArticle(Connection conn, String articleId) throws SQLException {
        int id = Integer.parseInt(articleId);
        return getReactionsToArticle(conn, id);
    }

    /**
     * This method inserts/updates the database entry for the User in question's reaction to the provided Article.
     *
     * @param conn      The DB connection to use.
     * @param articleId The ID of the Article to retrieve reactions for.
     * @param userId    The ID of the User whose reaction is being updated.
     * @param reaction  The integer value of the appropriate reaction.
     * @return
     * @throws SQLException
     */
    public static boolean setReactionToArticle(Connection conn, int articleId, int userId, int reaction) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement("select * from article_reactions where article_id=? and user_id=?")) {
            s.setInt(1, articleId);
            s.setInt(2, userId);

            s.execute();

            try (ResultSet r = s.getResultSet()) {
                if (r.next()) {
                    if (reaction == 0)
                        try (PreparedStatement s2 = conn.prepareStatement("delete from article_reactions where article_id=? and user_id=?;")) {
                            s2.setInt(1, articleId);
                            s2.setInt(2, userId);

                            return (s2.executeUpdate() != 0);
                        }
                    else
                        try (PreparedStatement s2 = conn.prepareStatement("update article_reactions set reaction=? where article_id=? and user_id=?;")) {
                            s2.setInt(1, reaction);
                            s2.setInt(2, articleId);
                            s2.setInt(3, userId);

                            return (s2.executeUpdate() != 0);
                        }
                }
            }
        }

        try (PreparedStatement s = conn.prepareStatement("insert into article_reactions values (?, ?, ?)")) {
            s.setInt(1, articleId);
            s.setInt(2, userId);
            s.setInt(3, reaction);

            return (s.executeUpdate() != 0);
        }
    }
}
