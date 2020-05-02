package com.vpsy._2f.vo.account;

import javax.persistence.*;

/**
 * @author 			punith
 * @date				24-Apr-2020
 * @description		The class represents <b>ProfilePicture</b> table.
 */

@Entity(name = "ProfilePicture")
@Table(name = "profile_picture_2f")
public class ProfilePicture {

    @Id
    @Column(name = "profile_pic_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;    // Todo: Change it to blob

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id_fk", unique = true)
    private Account account;

    public ProfilePicture() {
        super();
    }

    public ProfilePicture(ProfilePicture profilePicture) {
        this.id = profilePicture.id;
        this.profilePhoto = profilePicture.profilePhoto;
    }

    public Integer getId() {
        return id;
    }

    public ProfilePicture setId(Integer id) {
        this.id = id;
        return this;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public ProfilePicture setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public ProfilePicture setAccount(Account account) {
        this.account = account;
        return this;
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "id=" + id +
                ", account=" + account +
                '}';
    }
}
