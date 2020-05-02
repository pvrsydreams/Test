package com.vpsy._2f.vo.advertise;

import javax.persistence.*;

/**
 * @author punith
 * @date 24-Apr-2020
 * @description The class represents <b>AdPicture</b> table.
 */

@Entity(name = "AdPicture")
@Table(name = "ad_picture_2f")
public class AdPicture {

    @Id
    @Column(name = "ad_picture_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ad_photo")
    private String adPhoto;    // Todo: Change it to blob

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ad_id_fk")
    private Ad ad;

    public AdPicture() {
        super();
    }

    public AdPicture(AdPicture adPicture) {
        this.id = adPicture.id;
        this.adPhoto = adPicture.adPhoto;
        this.ad = new Ad(adPicture.ad);
    }

    public Integer getId() {
        return id;
    }

    public AdPicture setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAdPhoto() {
        return adPhoto;
    }

    public AdPicture setAdPhoto(String adPhoto) {
        this.adPhoto = adPhoto;
        return this;
    }

    public Ad getAd() {
        return ad;
    }

    public AdPicture setAd(Ad ad) {
        this.ad = ad;
        return this;
    }

    @Override
    public String toString() {
        return "AdPicture{" +
                "id=" + id +
                ", adPhoto='" + adPhoto + '\'' +
                ", ad=" + ad +
                '}';
    }
}
