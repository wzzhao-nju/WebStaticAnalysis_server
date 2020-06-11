package com.example.message;

public class RegisterLoginInfo {

    private int statusCode;
    private String info;

    public RegisterLoginInfo(int statusCode, String info){
        this.statusCode = statusCode;
        this.info = info;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
