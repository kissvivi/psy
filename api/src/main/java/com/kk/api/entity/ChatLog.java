package com.kk.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_chat_log")
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_id")
    private String fromId;

    @Column(name = "to_id")
    private String toId;

    @Column(name = "from_name")
    private String fromName;

    @Column(name = "to_name")
    private String toName;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Long uid;

    private String text;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return from_id
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * @param fromId
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * @return to_id
     */
    public String getToId() {
        return toId;
    }

    /**
     * @param toId
     */
    public void setToId(String toId) {
        this.toId = toId;
    }

    /**
     * @return from_name
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return to_name
     */
    public String getToName() {
        return toName;
    }

    /**
     * @param toName
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatLog{" +
                "id=" + id +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", date=" + date +
                ", uid=" + uid +
                ", text='" + text + '\'' +
                '}';
    }

    public ChatLog(Long id, String fromId, String toId, String fromName, String toName, Date date, Long uid, String text) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.fromName = fromName;
        this.toName = toName;
        this.date = date;
        this.uid = uid;
        this.text = text;
    }

    public ChatLog() {
    }
}