package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_tests")
public class Tests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "select_a")
    private String selectA;

    @Column(name = "select_b")
    private String selectB;

    @Column(name = "select_c")
    private String selectC;

    @Column(name = "select_d")
    private String selectD;

    @Column(name = "select_ok")
    private String selectOk;

    /**
     * 介绍
     */
    private String detial;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return select_a
     */
    public String getSelectA() {
        return selectA;
    }

    /**
     * @param selectA
     */
    public void setSelectA(String selectA) {
        this.selectA = selectA;
    }

    /**
     * @return select_b
     */
    public String getSelectB() {
        return selectB;
    }

    /**
     * @param selectB
     */
    public void setSelectB(String selectB) {
        this.selectB = selectB;
    }

    /**
     * @return select_c
     */
    public String getSelectC() {
        return selectC;
    }

    /**
     * @param selectC
     */
    public void setSelectC(String selectC) {
        this.selectC = selectC;
    }

    /**
     * @return select_d
     */
    public String getSelectD() {
        return selectD;
    }

    /**
     * @param selectD
     */
    public void setSelectD(String selectD) {
        this.selectD = selectD;
    }

    /**
     * @return select_ok
     */
    public String getSelectOk() {
        return selectOk;
    }

    /**
     * @param selectOk
     */
    public void setSelectOk(String selectOk) {
        this.selectOk = selectOk;
    }

    /**
     * 获取介绍
     *
     * @return detial - 介绍
     */
    public String getDetial() {
        return detial;
    }

    /**
     * 设置介绍
     *
     * @param detial 介绍
     */
    public void setDetial(String detial) {
        this.detial = detial;
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
}