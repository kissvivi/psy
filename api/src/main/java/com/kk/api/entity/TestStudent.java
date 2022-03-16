package com.kk.api.entity;

import javax.persistence.*;

@Table(name = "t_test_student")
public class TestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sid;

    @Column(name = "self_checks")
    private String selfChecks;

    private Long scode;

    @Column(name = "tb_ids")
    private String tbIds;

    private String sname;

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
     * @return sid
     */
    public Long getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }

    /**
     * @return self_checks
     */
    public String getSelfChecks() {
        return selfChecks;
    }

    /**
     * @param selfChecks
     */
    public void setSelfChecks(String selfChecks) {
        this.selfChecks = selfChecks;
    }

    /**
     * @return scode
     */
    public Long getScode() {
        return scode;
    }

    /**
     * @param scode
     */
    public void setScode(Long scode) {
        this.scode = scode;
    }

    /**
     * @return tb_ids
     */
    public String getTbIds() {
        return tbIds;
    }

    /**
     * @param tbIds
     */
    public void setTbIds(String tbIds) {
        this.tbIds = tbIds;
    }

    /**
     * @return sname
     */
    public String getSname() {
        return sname;
    }

    /**
     * @param sname
     */
    public void setSname(String sname) {
        this.sname = sname;
    }
}