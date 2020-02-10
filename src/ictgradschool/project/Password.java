package ictgradschool.project;

import ictgradschool.project.util.PasswordUtil;

/**
 * This class just stores password info for checking and contains the hashing process so we don't have to
 * repeat ourselves in multiple places.
 */
public class Password {

    private String salt;
    private int hashNum;
    private String hashedPassword;

    public Password(String plainText) {
        byte[] saltByte = PasswordUtil.getNextSalt();
        salt = PasswordUtil.base64Encode(saltByte);

        hashNum = (int) (Math.random() * 100000) + 1000000;

        byte[] hash = PasswordUtil.hash(plainText.toCharArray(), saltByte, hashNum);

        hashedPassword = PasswordUtil.base64Encode(hash);
    }

    public Password(String salt, int hashNum, String hashedPassword) {
        this.salt = salt;
        this.hashNum = hashNum;
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public int getHashNum() {
        return hashNum;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getSaltByte() {
        return PasswordUtil.base64Decode(salt);
    }

    public byte[] getHashByte() {
        return PasswordUtil.base64Decode(hashedPassword);
    }
}
