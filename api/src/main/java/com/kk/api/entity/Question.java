package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    private Long title;

    /**
     * 题目列表
     */
    @Column(name = "t_ids")
    private String tIds;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 介绍
     */
    private String detail;

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
     * 获取标题
     *
     * @return title - 标题
     */
    public Long getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(Long title) {
        this.title = title;
    }

    /**
     * 获取题目列表
     *
     * @return t_ids - 题目列表
     */
    public String gettIds() {
        return tIds;
    }

    /**
     * 设置题目列表
     *
     * @param tIds 题目列表
     */
    public void settIds(String tIds) {
        this.tIds = tIds;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取介绍
     *
     * @return detail - 介绍
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置介绍
     *
     * @param detail 介绍
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}