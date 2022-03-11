package com.kk.api.entity;

import javax.persistence.*;

@Table(name = "t_test")
public class TTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sid;

    private Long tid;

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
     * @return tid
     */
    public Long getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }
}