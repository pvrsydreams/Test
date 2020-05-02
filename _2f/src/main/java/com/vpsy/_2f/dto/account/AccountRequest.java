package com.vpsy._2f.dto.account;

/**
 * @author punith
 * @date 2020-04-25
 * @description The class represent Account Creation Request
 */
public class AccountRequest {
    private Long mobile;
    private String password;

    public AccountRequest() {
        super();
    }

    public AccountRequest(Long mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Long getMobile() {
        return mobile;
    }

    public AccountRequest setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "mobile=" + mobile +
                ", password='" + password + '\'' +
                '}';
    }
}
