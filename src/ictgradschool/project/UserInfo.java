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
    private String theme;

    public UserInfo(Integer userId, String blogName, String firstName, String lastName, Date dateOfBirth, String avatarURL, String profile, String theme, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.blogName = blogName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.avatarURL = avatarURL;
        this.theme = theme;
        this.profile = profile;
    }

    /**
     * Returns a copy of the provided string with all angle brackets replaced by appropriate html tags.
     * This prevents unwanted <script> tags and the like from being executed.
     *
     * @param s The string to sanitise.
     * @return the sanitised string.
     */
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

    public String getTheme() {return theme; }

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

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
