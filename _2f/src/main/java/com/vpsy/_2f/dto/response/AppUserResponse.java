package com.vpsy._2f.dto.response;

import org.dozer.Mapping;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class AppUserResponse {

    private Long mobile;

    private String mailId;

    private String name;

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

    private String addressLine1;

    private String addressLine2;

    public Long getMobile() {
        return mobile;
    }

    public AppUserResponse setMobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMailId() {
        return mailId;
    }

    public AppUserResponse setMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AppUserResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public AppUserResponse setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public Integer getTalukId() {
        return talukId;
    }

    public AppUserResponse setTalukId(Integer talukId) {
        this.talukId = talukId;
        return this;
    }

    public String getTalukName() {
        return talukName;
    }

    public AppUserResponse setTalukName(String talukName) {
        this.talukName = talukName;
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public AppUserResponse setDistrictId(Integer districtId) {
        this.districtId = districtId;
        return this;
    }

    public String getDistrictName() {
        return districtName;
    }

    public AppUserResponse setDistrictName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public Integer getStateId() {
        return stateId;
    }

    public AppUserResponse setStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public AppUserResponse setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public AppUserResponse setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public AppUserResponse setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    @Override
    public String toString() {
        return "AppUserResponse{" +
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
                '}';
    }
}
