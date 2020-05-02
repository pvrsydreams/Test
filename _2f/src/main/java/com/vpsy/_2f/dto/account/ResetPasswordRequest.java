package com.vpsy._2f.dto.account;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class ResetPasswordRequest {

    private Long mobile;
    private String passwordKey;
    private String newPassword;

    public ResetPasswordRequest() {
        super();
    }

    public ResetPasswordRequest(Long mobile, String passwordKey, String newPassword) {
        this.mobile = mobile;
        this.passwordKey = passwordKey;
        this.newPassword = newPassword;
    }

    public Long getMobile() {
        return mobile;
    }

    public ResetPasswordRequest setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public ResetPasswordRequest setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ResetPasswordRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "mobile=" + mobile +
                ", passwordKey='" + passwordKey + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
