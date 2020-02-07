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
    private Timestamp editedTimeStamp;  //Add edit time for the article

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
        this.editedTimeStamp = editedTimeStamp; //Add edit time for the article
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

    private String sanitise(String s) {
        return s.replace("<","&lt;").replace(">","&gt;");
    }

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

    public static void sortByPopularity(List<Article> articles) {
        int baseCommentVal = 4;
        int baseLikeVal = 2;
        int baseDislikeVal = -1;

        Comparator<Article> c = (a1, a2) ->
                getArticlePopularityScore(a2, baseCommentVal, baseLikeVal, baseDislikeVal) - getArticlePopularityScore(a1, baseCommentVal, baseLikeVal, baseDislikeVal);

        Collections.sort(articles, c);
    }

    private static int getArticlePopularityScore(Article a, int commentVal, int likeVal, int dislikeVal) {
        int score = a.children.size();
        for (ArticleReaction r: a.reactions)
            switch (r.getReaction()) {
                case 1:
                    score += likeVal;
                    break;
                case 2:
                    score += dislikeVal;
                    break;
            }
        for (Article comment: a.children) {
            score += getArticlePopularityScore(comment, Math.max(commentVal-1, 1), Math.max(likeVal-1, 0), Math.min(dislikeVal + 1, 0) );
        }
        return score;
    }
}
