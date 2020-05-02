package com.vpsy._2f.vo.advertise;

import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.location.Taluk;
import com.vpsy._2f.vo.message.Message;
import com.vpsy._2f.vo.product.Item;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author 			punith
 * @date				24-Apr-2020
 * @description		The class represents <b>Ad</b> table.
 */

@Entity(name = "Ad")
@Table(name = "ad_2f")
public class Ad {

    @Id
    @Column(name = "ad_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id_fk")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id_fk")
    private Item item;

    @JoinColumn(name = "price_per_unit")
    private Double pricePerUnit;

    @Column(name = "posted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "taluk_id_fk")
    private Taluk taluk;

    @Column(name = "ad_status")
    @Enumerated(EnumType.STRING)
    private AdStatus adStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id_fk")
    private Set<Deal> deals;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id_fk")
    private Set<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id_fk")
    private Set<AdPicture> adPictures;

    public Ad() {
        super();
    }

    public Ad(Ad ad) {
        this.id = ad.id;
        this.title = ad.title;
        this.description = ad.description;
        this.account = new Account(ad.account);
        this.item = new Item(ad.item);
        this.pricePerUnit = ad.pricePerUnit;
        this.postedAt = ad.postedAt;
        this.updatedAt = ad.updatedAt;
        this.taluk = new Taluk(ad.taluk);
        this.adStatus = ad.adStatus;
    }

    public Integer getId() {
        return id;
    }

    public Ad setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Ad setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ad setDescription(String description) {
        this.description = description;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public Ad setAccount(Account account) {
        this.account = account;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public Ad setItem(Item item) {
        this.item = item;
        return this;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Ad setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public Ad setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Ad setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Taluk getTaluk() {
        return taluk;
    }

    public Ad setTaluk(Taluk taluk) {
        this.taluk = taluk;
        return this;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public Ad setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public Ad setDeals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Ad setMessages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Set<AdPicture> getAdPictures() {
        return adPictures;
    }

    public Ad setAdPictures(Set<AdPicture> adPictures) {
        this.adPictures = adPictures;
        return this;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", account=" + account +
                ", item=" + item +
                ", pricePerUnit=" + pricePerUnit +
                ", postedAt=" + postedAt +
                ", updatedAt=" + updatedAt +
                ", taluk=" + taluk +
                ", adStatus=" + adStatus +
                '}';
    }
}
