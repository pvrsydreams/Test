package com.vpsy._2f.dto.account;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class AccountVerificationRequest {
    private Long mobile;
    private String passwordKey;
    private String otp;

    public AccountVerificationRequest() {
        super();
    }

    public AccountVerificationRequest(Long mobile, String passwordKey, String otp) {
        this.mobile = mobile;
        this.passwordKey = passwordKey;
        this.otp = otp;
    }

    public Long getMobile() {
        return mobile;
    }

    public AccountVerificationRequest setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public AccountVerificationRequest setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public AccountVerificationRequest setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    @Override
    public String toString() {
        return "AccountVerificationRequest{" +
                "mobile=" + mobile +
                ", passwordKey='" + passwordKey + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
