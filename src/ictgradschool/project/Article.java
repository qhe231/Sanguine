package ictgradschool.project;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Article implements Serializable {

    private int articleId;
    private UserInfo author;
    private String title;
    private String content;
    private Timestamp postedTimeStamp;
    private Timestamp editedTimeStamp;

    private List<ArticleReaction> reactions;

    private List<Article> children;

    private int parentId;

    public Article(int articleId, UserInfo author, String title, String content, Timestamp postedTimeStamp, List<Article> children, int parentId, Timestamp editedTimeStamp,
                   List<ArticleReaction> reactions) {
        this.articleId = articleId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedTimeStamp = postedTimeStamp;
        this.children = children;
        this.parentId = parentId;
        this.editedTimeStamp = editedTimeStamp;
        this.reactions = reactions;
    }

    public Article(UserInfo author, String title, String content, Timestamp postedTimeStamp, List<Article> children, int parentId) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedTimeStamp = postedTimeStamp;
        this.children = children;
        this.parentId = parentId;
        this.editedTimeStamp = postedTimeStamp; //editedTimeStamp will be same as the postedTimeStamp at first post time
        this.reactions = new ArrayList<>();
    }

    /**
     * Returns a copy of the provided string with all angle brackets replaced by appropriate html tags.
     * This prevents unwanted <script> tags and the like from being executed.
     *
     * @param s The string to sanitise.
     * @return the sanitised string.
     */
    private String sanitise(String s) {
        return s.replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * Returns up to 100 characters of the content of an article, with all html tags removed, for use as a post preview.
     *
     * @return the preview plaintext.
     */
    public String getContentPreview() {
        String preview = content;
        while (preview.contains("<")) {
            String s = preview.substring(0, preview.indexOf("<")) + preview.substring(preview.indexOf(">") + 1);
            preview = s;
        }
        if (preview.length() <= 100)
            return preview;
        return preview.substring(0, 100);
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    public String getTitle() {
        return sanitise(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPostedTimeStamp() {
        return postedTimeStamp;
    }

    public void setPostedTimeStamp(Timestamp postedTimeStamp) {
        this.postedTimeStamp = postedTimeStamp;
    }

    public List<Article> getChildren() {
        return children;
    }

    public void setChildren(List<Article> children) {
        this.children = children;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Timestamp getEditedTimeStamp() {
        return editedTimeStamp;
    }

    public void setEditedTimeStamp(Timestamp editedTimeStamp) {
        this.editedTimeStamp = editedTimeStamp;
    }

    public List<ArticleReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<ArticleReaction> reactions) {
        this.reactions = reactions;
    }

    /**
     * This method sorts the provided list of Articles according to their popularity.
     * The base point values of Comments, Likes and Dislikes on a primary article are defined here.
     *
     * @param articles The list of Articles to sort.
     */
    public static void sortByPopularity(List<Article> articles) {
        int baseCommentVal = 5;
        int baseLikeVal = 9;
        int baseDislikeVal = -5;

        Comparator<Article> c = (a1, a2) ->
                getArticlePopularityScore(a2, baseCommentVal, baseLikeVal, baseDislikeVal) - getArticlePopularityScore(a1, baseCommentVal, baseLikeVal, baseDislikeVal);

        Collections.sort(articles, c);
    }

    /**
     * This method determines the popularity score of a given article.
     * Likes and Dislikes on comments, as well as comments to comments, have a reduced value on higher-level articles' popularity.
     *
     * @param a          The article being calculated.
     * @param commentVal The current point value for a comment.
     * @param likeVal    The current point value for a like.
     * @param dislikeVal The current point value for a dislike.
     * @return
     */
    private static int getArticlePopularityScore(Article a, int commentVal, int likeVal, int dislikeVal) {
        int score = a.children.size() * commentVal;
        for (ArticleReaction r : a.reactions)
            switch (r.getReaction()) {
                case 1:
                    score += likeVal;
                    break;
                case 2:
                    score += dislikeVal;
                    break;
            }
        for (Article comment : a.children) {
            score += getArticlePopularityScore(comment, Math.max(commentVal - 1, 1), Math.max(likeVal - 2, 0), Math.min(dislikeVal + 2, 0));
        }
        return score;
    }
}
