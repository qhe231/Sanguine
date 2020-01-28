package ictgradschool.project;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Article implements Serializable {

    private int articleId;
    private UserInfo Author;
    private String title;
    private String content;
    private Timestamp postedTimeStamp;

    private List<Article> children;

    private int parentId;

    public Article(int articleId, int authorId, String title, String content, Timestamp postedTimeStamp, List<Article> children, int parentId) {
        this.articleId = articleId;
        this.author = UserInfoDAO.getUserInfo(authorId);
        this.title = title;
        this.content = content;
        this.postedTimeStamp = postedTimeStamp;
        this.children = children;
        this.parentId = parentId;
    }

    public Article(int authorId, String title, String content, Timestamp postedTimeStamp, List<Article> children, int parentId) {
        this.author = UserInfoDAO.getUserInfo(authorId);
        this.title = title;
        this.content = content;
        this.postedTimeStamp = postedTimeStamp;
        this.children = children;
        this.parentId = parentId;
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
        return title;
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
}
