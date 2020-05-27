package com.example.entity;

import com.example.message.Result;

import javax.persistence.*;
import java.sql.Timestamp;

//每次缺陷检测的记录
@Entity
@IdClass(RecordCompositeKeys.class)
public class Record {
    @Id
    private Integer uid; //用户id
    private Result result; //缺陷检测结果
    @Id
    private Timestamp timestamp; //缺陷检测的时间戳

    public void setId(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
