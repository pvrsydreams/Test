package com.vpsy._2f.vo.message;

import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.advertise.Ad;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 			punith
 * @date				24-Apr-2020
 * @description		The class represents <b>Message</b> table.
 */

@Entity(name = "Message")
@Table(name = "message_2f")
public class Message {

    @Id
    @Column(name = "message_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "from_account_id_fk")
    private Account messageFrom;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "to_account_id_fk")
    private Account messageTo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ad_id_fk")
    private Ad ad;

    @Column(name = "message_content", length = 255)
    private String content;

    @Column(name = "messaged_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messagedAt;

    @Column(name = "message_status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    public Message() {
        super();
    }

    public Message(Message message) {
        this.id = message.id;
        this.messageFrom = new Account(message.messageFrom);
        this.messageTo = new Account(message.messageTo);
        this.ad = new Ad(message.ad);
        this.content = message.content;
        this.messagedAt = message.messagedAt;
        this.status = message.status;
    }

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Account getMessageFrom() {
        return messageFrom;
    }

    public Message setMessageFrom(Account messageFrom) {
        this.messageFrom = messageFrom;
        return this;
    }

    public Account getMessageTo() {
        return messageTo;
    }

    public Message setMessageTo(Account messageTo) {
        this.messageTo = messageTo;
        return this;
    }

    public Ad getAd() {
        return ad;
    }

    public Message setAd(Ad ad) {
        this.ad = ad;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getMessagedAt() {
        return messagedAt;
    }

    public Message setMessagedAt(Date messagedAt) {
        this.messagedAt = messagedAt;
        return this;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public Message setStatus(MessageStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageFrom=" + messageFrom +
                ", messageTo=" + messageTo +
                ", ad=" + ad +
                ", content='" + content + '\'' +
                ", messagedAt=" + messagedAt +
                ", status=" + status +
                '}';
    }
}
