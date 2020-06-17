package com.example.message;

public class Person {

    private int statusCode;
    private String username;

    public Person() {}

    public Person(int statusCode, String username){
        this.statusCode = statusCode;
        this.username = username;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
