package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_student")
public class Student {
    /**
     * 学号
     */
    @Id
    private Long code;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 学生名
     */
    private String name;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 班级
     */
    private String clazz;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 性别
     */
    private String sex;

    /**
     * 个人介绍
     */
    private String detail;

    /**
     * 获取学号
     *
     * @return code - 学号
     */
    public Long getCode() {
        return code;
    }

    /**
     * 设置学号
     *
     * @param code 学号
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取学生名
     *
     * @return name - 学生名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学生名
     *
     * @param name 学生名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取专业
     *
     * @return specialty - 专业
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * 设置专业
     *
     * @param specialty 专业
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * 获取班级
     *
     * @return clazz - 班级
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * 设置班级
     *
     * @param clazz 班级
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
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
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取个人介绍
     *
     * @return detail - 个人介绍
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置个人介绍
     *
     * @param detail 个人介绍
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}