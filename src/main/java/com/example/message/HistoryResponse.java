package com.example.message;

import java.util.Vector;

public class HistoryResponse {
    private int statusCode;
    private Vector<History> histories;

    public HistoryResponse(){
        statusCode = 0;
        histories = new Vector<>();
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setHistories(Vector<History> histories) {
        this.histories = histories;
    }

    public Vector<History> getHistories() {
        return histories;
    }
}
