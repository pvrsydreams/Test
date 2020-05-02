package com.vpsy._2f.vo.account;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.vpsy._2f.vo.advertise.*;
import com.vpsy._2f.vo.location.Taluk;
import com.vpsy._2f.vo.message.Message;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author 			punith
 * @date				24-Apr-2020
 * @description		The class represents <b>Account</b> table.
 */
@Entity(name = "Account")
@Table(name = "account_2f")
public class Account {

	@Id
	@Column(name = "account_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "mobile_number", unique = true, length = 15)
	@NotNull(message = "Please provide mobile number")
	private Long mobile;
	
	@Column(name = "mail_id", unique = true, length = 100)
    @Email
	private String mailId;
	
	@Column(name = "account_name", length = 100)
	private String name;

	@Column(name = "password_key")
	private String passwordKey;

    @Column(name = "salt")
    private String salt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taluk_id_fk")
    private Taluk taluk;
	
	@Column(name = "address_line1", length = 255)
	private String addressLine1;
	
	@Column(name = "address_line2", length = 255)
	private String addressLine2;
	
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

    @OneToOne(mappedBy="account", optional = true)
	private ProfilePicture profilePicture;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id_fk")
    private Set<Deal> deals;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "from_account_id_fk")
    private Set<Message> messagesSent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "to_account_id_fk")
    private Set<Message> messagesReceived;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id_fk")
    private Set<Ad> ads;

	public Account() {
		super();
		deals = new HashSet<>();
		messagesSent = new HashSet<>();
		messagesReceived = new HashSet<>();
		ads = new HashSet<>();
	}

	public Account(Account account) {
	    this.id = account.id;
	    this.mobile = account.mobile;
	    this.mailId = account.mailId;
        this.name = account.name;
        this.passwordKey = account.passwordKey;
        this.salt = account.salt;
        this.taluk = new Taluk(account.taluk);
        this.addressLine1 = account.addressLine1;
        this.addressLine2 = account.addressLine2;
        this.createdAt = account.createdAt;
        this.lastActivityAt = account.lastActivityAt;
    }

    public Integer getId() {
        return id;
    }

    public Account setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getMobile() {
        return mobile;
    }

    public Account setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public Account setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public Account setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public Account setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Taluk getTaluk() {
        return taluk;
    }

    public Account setTaluk(Taluk taluk) {
        this.taluk = taluk;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public Account setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Account setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Account setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getLastActivityAt() {
        return lastActivityAt;
    }

    public Account setLastActivityAt(Date lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
        return this;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public Account setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public Account setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Account setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public Account setDeals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public Set<Message> getMessagesSent() {
        return messagesSent;
    }

    public Account setMessagesSent(Set<Message> messagesSent) {
        this.messagesSent = messagesSent;
        return this;
    }

    public Set<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public Account setMessagesReceived(Set<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
        return this;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public Account setAds(Set<Ad> ads) {
        this.ads = ads;
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", mobile=" + mobile +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", salt='" + salt + '\'' +
                ", taluk=" + taluk +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", createdAt=" + createdAt +
                ", lastActivityAt=" + lastActivityAt +
                ", accountStatus=" + accountStatus +
                ", failedLoginAttempts=" + failedLoginAttempts +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
