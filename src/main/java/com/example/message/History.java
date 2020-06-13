package com.example.message;

import java.sql.Timestamp;

public class History {

    private String analyzeId;
    private Timestamp timestamp;
    private Integer filecount;
    private Integer errorcount;

    public History(String analyzeId, Timestamp timestamp, Integer filecount, Integer errorcount){
        this.analyzeId = analyzeId;
        this.timestamp = timestamp;
        this.filecount = filecount;
        this.errorcount = errorcount;
    }
}
