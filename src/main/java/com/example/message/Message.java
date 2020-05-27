package com.example.message;

//send message to client when receive something(code or file)
public class Message {
    private int stateCode;//0 means success, -1 means error
    private String filename;
    private String errorInfo;

    public Message(int stateCode, String filename){
        this.stateCode = stateCode;
        this.filename = filename;
        this.errorInfo = null;
    }

    public Message(int stateCode, String filename, String errorInfo) {
        this.stateCode = stateCode;
        this.filename = filename;
        this.errorInfo = errorInfo;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getFilename() {
        return filename;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
