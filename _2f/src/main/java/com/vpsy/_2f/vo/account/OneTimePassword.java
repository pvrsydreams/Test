package com.vpsy._2f.vo.account;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author punith
 * @date 2020-04-25
 * @description Class to hold One Time Password information
 */
@Entity(name = "OneTimePassword")
@Table(name = "one_time_password_2f")
public class OneTimePassword {

    @Id
    @Column(name = "mobile_number")
    private Long mobile;

    @Column(name = "otp_key")
    private String otpKey;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date createdAt;

    public OneTimePassword() {
        super();
    }

    public OneTimePassword(Long mobile, String otpKey) {
        this.mobile = mobile;
        this.otpKey = otpKey;
    }

    public OneTimePassword(Long mobile, String otpKey, Date createdAt) {
        this.mobile = mobile;
        this.otpKey = otpKey;
        this.createdAt = createdAt;
    }

    public Long getMobile() {
        return mobile;
    }

    public OneTimePassword setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public OneTimePassword setOtpKey(String otpKey) {
        this.otpKey = otpKey;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public OneTimePassword setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return "OTP{" +
                "mobile=" + mobile +
                ", otpKey='" + otpKey + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
