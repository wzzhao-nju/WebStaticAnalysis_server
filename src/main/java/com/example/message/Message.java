package com.example.message;

//send message to client when receive something(code or file)
public class Message {
    private int stateCode;//0 means success, -1 means error
    private String identity;
    private String errorInfo;

    public Message(int stateCode, String identity){
        this.stateCode = stateCode;
        this.identity = identity;
        this.errorInfo = null;
    }

    public Message(int stateCode, String identity, String errorInfo) {
        this.stateCode = stateCode;
        this.identity = identity;
        this.errorInfo = errorInfo;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getIdentity() {
        return identity;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
