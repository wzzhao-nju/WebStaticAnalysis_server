package com.example.message;

//代表一行代码
public class Line {
    private int lineNo; //行号
    private String code; //代码

    public Line(int lineNo, String code) {
        this.lineNo = lineNo;
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getCode() {
        return code;
    }

    public int getLineNo() {
        return lineNo;
    }
}
