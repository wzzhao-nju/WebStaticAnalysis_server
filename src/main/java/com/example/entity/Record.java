package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Record {

    @Id
    private String analyzeId;

    private Integer uid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp timestamp;

    private Integer filecount;

    private Integer errorcount;

    public void setAnalyzeId(String analyzeId) {
        this.analyzeId = analyzeId;
    }

    public String getAnalyzeId() {
        return analyzeId;
    }

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

    public void setFilecount(Integer filecount) {
        this.filecount = filecount;
    }

    public Integer getFilecount() {
        return filecount;
    }

    public void setErrorcount(Integer errorcount) {
        this.errorcount = errorcount;
    }

    public Integer getErrorcount() {
        return errorcount;
    }
}
