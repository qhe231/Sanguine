package ictgradschool.project;

//@author: Peter He

public class User {
   private Integer userId;
   private String userName;
   private String blogName;
   private String avatarUrl;
   private String hashedPassword;
   private String salt;
   private Integer hashNum;

    public User(){

    }

    public User(Integer userId, String userName, String avatarUrl, String hashedPassword, String salt, Integer hashNum) {
        this.userId = userId;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.hashNum = hashNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public Integer getHashNum() {
        return hashNum;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setHashNum(Integer hashNum) {
        this.hashNum = hashNum;
    }
}
