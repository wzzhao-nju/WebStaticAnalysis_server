package com.example.entity;

import javax.persistence.Id;

public class LoginInfo {

    @Id
    private String sessionId;
    private Integer uid;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }
}
