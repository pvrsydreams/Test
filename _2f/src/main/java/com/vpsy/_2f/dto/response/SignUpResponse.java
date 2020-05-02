package com.vpsy._2f.dto.response;

/**
 * @author punith
 * @date 2020-04-25
 * @description The class holds the response information when user tries to sign up
 */
public class SignUpResponse {

    private Long mobile;

    private String mailId;

    private String name;

    private String otp;

    public SignUpResponse() {
        super();
    }

    public SignUpResponse(Long mobile, String mailId, String name, String otp) {
        this.mobile = mobile;
        this.mailId = mailId;
        this.name = name;
        this.otp = otp;
    }

    public Long getMobile() {
        return mobile;
    }

    public SignUpResponse setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public SignUpResponse setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SignUpResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public SignUpResponse setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "mobile=" + mobile +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
