package com.vpsy._2f.dto.account;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class ForgotPasswordRequest {

    private Long mobile;
    private String otp;
    private String newPassword;

    public ForgotPasswordRequest() {
        super();
    }

    public ForgotPasswordRequest(Long mobile, String otp, String newPassword) {
        this.mobile = mobile;
        this.otp = otp;
        this.newPassword = newPassword;
    }

    public Long getMobile() {
        return mobile;
    }

    public ForgotPasswordRequest setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public ForgotPasswordRequest setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ForgotPasswordRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequest{" +
                "mobile=" + mobile +
                ", otp='" + otp + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
