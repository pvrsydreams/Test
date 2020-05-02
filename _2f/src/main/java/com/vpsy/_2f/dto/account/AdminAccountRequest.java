package com.vpsy._2f.dto.account;

/**
 * @author punith
 * @date 2020-04-26
 * @description
 * Todo: The class should be deleted. It is there only to add admin account
 */
public class AdminAccountRequest {

    private Long mobile;
    private String mailId;
    private String name;
    private String password;

    public Long getMobile() {
        return mobile;
    }

    public AdminAccountRequest setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public AdminAccountRequest setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AdminAccountRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdminAccountRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "AdminAccountRequest{" +
                "mobile=" + mobile +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
