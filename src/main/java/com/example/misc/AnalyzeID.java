package com.example.misc;

//用该类来表示一个Identity，方便对应Post请求的RequestBody
public class AnalyzeID {
    private String analyzeID;

    public AnalyzeID() {}

    public void setAnalyzeID(String analyzeID) {
        this.analyzeID = analyzeID;
    }

    public String getAnalyzeID() {
        return analyzeID;
    }
}
