package ictgradschool.project;

import java.io.Serializable;

/**
 * Reaction values:
 * 1    Like
 * 2    Dislike
 */
public class ArticleReaction implements Serializable {
    int articleId;
    int userId;
    int reaction;

    public ArticleReaction(int articleId, int userId, int reaction) {
        this.articleId = articleId;
        this.userId = userId;
        this.reaction = reaction;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }
}
