package com.kk.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

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
    private String title;

    /**
     * 题目列表
     */
    @Column(name = "test_ids")
    private String testIds;

    @Column(name = "create_time")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
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
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取题目列表
     *
     * @return test_ids - 题目列表
     */
    public String getTestIds() {
        return testIds;
    }

    /**
     * 设置题目列表
     *
     * @param testIds 题目列表
     */
    public void setTestIds(String testIds) {
        this.testIds = testIds;
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