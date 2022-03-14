package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_consult")
public class Consult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 医生id
     */
    private Long did;

    /**
     * 学生id
     */
    private Long sid;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评价
     */
    private String assess;

    /**
     * 1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成，
     */
    private Integer status;

    /**
     * 结果
     */
    private String content;



    /**
     * 报告地址
     */
    private String report_url;


    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }


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
     * 获取医生id
     *
     * @return did - 医生id
     */
    public Long getDid() {
        return did;
    }

    /**
     * 设置医生id
     *
     * @param did 医生id
     */
    public void setDid(Long did) {
        this.did = did;
    }

    /**
     * 获取学生id
     *
     * @return sid - 学生id
     */
    public Long getSid() {
        return sid;
    }

    /**
     * 设置学生id
     *
     * @param sid 学生id
     */
    public void setSid(Long sid) {
        this.sid = sid;
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
     * 获取评价
     *
     * @return assess - 评价
     */
    public String getAssess() {
        return assess;
    }

    /**
     * 设置评价
     *
     * @param assess 评价
     */
    public void setAssess(String assess) {
        this.assess = assess;
    }

    /**
     * 获取1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成，
     *
     * @return status - 1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成，
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成，
     *
     * @param status 1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成，
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取结果
     *
     * @return content - 结果
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置结果
     *
     * @param content 结果
     */
    public void setContent(String content) {
        this.content = content;
    }
}