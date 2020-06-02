package com.example.misc;

//用该类来表示一个Identity，方便对应Post请求的RequestBody
public class Identity {
    private String identity;

    public Identity() {}

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
