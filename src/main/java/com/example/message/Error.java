package com.example.message;

import java.util.Vector;

public class Error {
    private int start_line; //缺陷起始行数
    private int end_line; //缺陷结束行数
    //private int n = 3; // 暂定显示上下相关的3行
    private final Vector<Line> before_error; //缺陷前n行
    private final Vector<Line> error_lines; //缺陷所在的行
    private final Vector<Line> after_error; //缺陷后n行
    private String error_info; //缺陷信息

    public Error() {
        before_error = new Vector<Line>();
        error_lines = new Vector<Line>();
        after_error = new Vector<Line>();
    }

    public void setStart_line(int start_line) {
        this.start_line = start_line;
    }

    public void setEnd_line(int end_line) {
        this.end_line = end_line;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    public void push_before(Line L){
        before_error.add(L);
    }

    public void push_rightIn(Line L){
        error_lines.add(L);
    }

    public void push_after(Line L){
        after_error.add(L);
    }

    public int getStart_line() {
        return start_line;
    }

    public int getEnd_line() {
        return end_line;
    }

    public Vector<Line> getBefore_error() {
        return before_error;
    }

    public Vector<Line> getError_lines() {
        return error_lines;
    }

    public Vector<Line> getAfter_error() {
        return after_error;
    }

    public String getError_info() {
        return error_info;
    }
}

