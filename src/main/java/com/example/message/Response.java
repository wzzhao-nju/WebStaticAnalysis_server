package com.example.message;

import java.util.Vector;

public class Response {

    private int statusCode;
    private Vector<Result> results;

    public Response(){
        statusCode = -1;
        results = new Vector<>();
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setResults(Vector<Result> results) {
        this.results = results;
    }
}
