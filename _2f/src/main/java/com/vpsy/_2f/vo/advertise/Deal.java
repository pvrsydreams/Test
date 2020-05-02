package com.vpsy._2f.vo.advertise;

import com.vpsy._2f.vo.account.Account;

import javax.persistence.*;

/**
 * @author 			punith
 * @date				24-Apr-2020
 * @description		The class represents <b>Deal</b> table.
 */

@Entity(name = "Deal")
@Table(name = "deal_2f")
public class Deal {

    @Id
    @Column(name = "deal_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ad_id_fk")
    private Ad ad;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id_fk")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_status")
    private DealStatus dealStatus;

    public Deal() {
        super();
    }

    public Deal(Deal deal) {
        this.id = deal.id;
        this.ad = new Ad(deal.ad);
        this.account = new Account((deal.account));
        this.dealStatus = deal.dealStatus;
    }

    public Integer getId() {
        return id;
    }

    public Deal setId(Integer id) {
        this.id = id;
        return this;
    }

    public Ad getAd() {
        return ad;
    }

    public Deal setAd(Ad ad) {
        this.ad = ad;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public Deal setAccount(Account account) {
        this.account = account;
        return this;
    }

    public DealStatus getDealStatus() {
        return dealStatus;
    }

    public Deal setDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
        return this;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", ad=" + ad +
                ", account=" + account +
                ", dealStatus=" + dealStatus +
                '}';
    }
}
