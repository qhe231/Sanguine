package ictgradschool.project;

//@author: Peter He

import java.io.Serializable;

public class UserAuthentication implements Serializable {
   private Integer userId;
   private String userName;
   private String hashedPassword;
   private String salt;
   private Integer hashNum;
   private String thirdPartyId;

    public UserAuthentication(Integer userId, String userName, String hashedPassword, String salt, Integer hashNum) {
        this.userId = userId;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.hashNum = hashNum;
        this.thirdPartyId = null;
    }

    public UserAuthentication(Integer userId, String userName, String hashedPassword, String salt, Integer hashNum, String thirdPartyId) {
        this.userId = userId;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.hashNum = hashNum;
        this.thirdPartyId = thirdPartyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

}
