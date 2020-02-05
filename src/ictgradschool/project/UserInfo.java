package ictgradschool.project;

import java.io.Serializable;
import java.util.Date;

//@author: Peter He

public class UserInfo implements Serializable {
    private Integer userId;
    private String blogName;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String avatarURL;
    private String profile;
    private String userName;

    public UserInfo() {

    }

    public UserInfo(Integer userId, String blogName, String firstName, String lastName, Date dateOfBirth, String avatarURL, String profile, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.blogName = blogName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.avatarURL = avatarURL;
        this.profile = profile;
    }

    private String sanitise(String s) {
        return s.replace("<","&lt;").replace(">","&gt;");
    }

    public Integer getUserId() {
        return userId;
    }

    public String getBlogName() {
        return sanitise(blogName);
    }

    public String getFirstName() {
        return sanitise(firstName);
    }

    public String getLastName() {
        return sanitise(lastName);
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getProfile() {
        return sanitise(profile);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
