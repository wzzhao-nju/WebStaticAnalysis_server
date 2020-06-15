package com.example.message;

import java.sql.Timestamp;

public class History {

    private String analyzeId;
    private Timestamp timestamp;
    private Integer filecount;
    private Integer errorcount;

    public History(){}

    public History(String analyzeId, Timestamp timestamp, Integer filecount, Integer errorcount){
        this.analyzeId = analyzeId;
        this.timestamp = timestamp;
        this.filecount = filecount;
        this.errorcount = errorcount;
    }

    public void setAnalyzeId(String analyzeId) {
        this.analyzeId = analyzeId;
    }

    public String getAnalyzeId() {
        return analyzeId;
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
