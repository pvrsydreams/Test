package com.vpsy._2f.utility;

/**
 * @author punith
 * @date 2020-04-24
 * @description
 */
public class HashResult {

    /** The variable is used to hold hashed string */
    private String hashString;

    /** The variable is used to hold salt used to hash string */
    private String salt;

    public HashResult(String hashString, String salt) {
        this.hashString = hashString;
        this.salt = salt;
    }

    public String getHashString() {
        return hashString;
    }

    public HashResult setHashString(String hashString) {
        this.hashString = hashString;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public HashResult setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    @Override
    public String toString() {
        return "HashResult{" +
                "hashString='" + hashString + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
