package com.example.message;

//send message to client when receive something(code or file)
public class Message {
    private int stateCode;//0 means success, -1 means error
    private String analyzeID;
    private String errorInfo;

    public Message(int stateCode, String analyzeID){
        this.stateCode = stateCode;
        this.analyzeID = analyzeID;
        this.errorInfo = null;
    }

    public Message(int stateCode, String analyzeID, String errorInfo) {
        this.stateCode = stateCode;
        this.analyzeID = analyzeID;
        this.errorInfo = errorInfo;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public void setAnalyzeID(String analyzeID) {
        this.analyzeID = analyzeID;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getAnalyzeID() {
        return analyzeID;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
