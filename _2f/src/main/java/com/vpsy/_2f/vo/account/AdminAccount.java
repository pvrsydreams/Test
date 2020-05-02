package com.vpsy._2f.vo.account;

import com.vpsy._2f.vo.location.Taluk;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author punith
 * @date 2020-04-26
 * @description
 */
@Entity(name = "AdminAccount")
@Table(name = "admin_account_2f")
public class AdminAccount {

    @Id
    @Column(name = "mobile_number", unique = true, length = 15)
    @NotNull(message = "Please provide mobile number")
    private Long mobile;

    @Column(name = "mail_id", unique = true, length = 100)
    @Email
    private String mailId;

    @Column(name = "admin_name", length = 100)
    private String name;

    @Column(name = "password_key")
    private String passwordKey;

    @Column(name = "salt")
    private String salt;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "last_activity_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastActivityAt;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "failed_login_attempts")
    @ColumnDefault(value = "0")
    private Integer failedLoginAttempts;

    public AdminAccount() {
        super();
    }

    public Long getMobile() {
        return mobile;
    }

    public AdminAccount setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public AdminAccount setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AdminAccount setName(String name) {
        this.name = name;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public AdminAccount setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public AdminAccount setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public AdminAccount setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getLastActivityAt() {
        return lastActivityAt;
    }

    public AdminAccount setLastActivityAt(Date lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
        return this;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public AdminAccount setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public AdminAccount setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "mobile=" + mobile +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", salt='" + salt + '\'' +
                ", createdAt=" + createdAt +
                ", lastActivityAt=" + lastActivityAt +
                ", accountStatus=" + accountStatus +
                ", failedLoginAttempts=" + failedLoginAttempts +
                '}';
    }
}
