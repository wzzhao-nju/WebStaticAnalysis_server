package com.example.entity;

import java.io.Serializable;
import java.sql.Timestamp;

//Record table的联合主键
public class RecordCompositeKeys implements Serializable {

    private Integer uid; //用户id
    private Timestamp timestamp; //缺陷检测时间戳

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
