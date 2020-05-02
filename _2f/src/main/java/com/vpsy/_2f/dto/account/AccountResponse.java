package com.vpsy._2f.dto.account;

import com.vpsy._2f.vo.account.AccountStatus;
import org.dozer.Mapping;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class AccountResponse {

    @Mapping("mobile")
    private Long mobile;

    @Mapping("mailId")
    private String mailId;

    @Mapping("name")
    private String name;

    @Mapping("passwordKey")
    private String passwordKey;

    @Mapping("taluk.id")
    private Integer talukId;

    @Mapping("taluk.name")
    private String talukName;

    @Mapping("taluk.district.id")
    private Integer districtId;

    @Mapping("taluk.district.name")
    private String districtName;

    @Mapping("taluk.district.state.id")
    private Integer stateId;

    @Mapping("taluk.district.state.name")
    private String stateName;

    @Mapping("addressLine1")
    private String addressLine1;

    @Mapping("addressLine2")
    private String addressLine2;

    @Mapping("accountStatus")
    private AccountStatus accountStatus;

    @Mapping("profilePicture.profilePhoto")
    private byte[] profilePhoto;

    public Long getMobile() {
        return mobile;
    }

    public AccountResponse setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public AccountResponse setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public AccountResponse setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public Integer getTalukId() {
        return talukId;
    }

    public AccountResponse setTalukId(Integer talukId) {
        this.talukId = talukId;
        return this;
    }

    public String getTalukName() {
        return talukName;
    }

    public AccountResponse setTalukName(String talukName) {
        this.talukName = talukName;
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public AccountResponse setDistrictId(Integer districtId) {
        this.districtId = districtId;
        return this;
    }

    public String getDistrictName() {
        return districtName;
    }

    public AccountResponse setDistrictName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public Integer getStateId() {
        return stateId;
    }

    public AccountResponse setStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public AccountResponse setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public AccountResponse setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public AccountResponse setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public AccountResponse setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public AccountResponse setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "mobile=" + mobile +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", talukId=" + talukId +
                ", talukName='" + talukName + '\'' +
                ", districtId=" + districtId +
                ", districtName='" + districtName + '\'' +
                ", stateId=" + stateId +
                ", stateName='" + stateName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
