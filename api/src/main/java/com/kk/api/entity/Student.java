package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}