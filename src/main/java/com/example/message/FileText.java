package com.example.message;

import java.util.Vector;

public class FileText {
    private Vector<String> line;

    public FileText(){
        line = new Vector<>();
    }

    public void append(String str){
        line.add(str);
    }

    public Vector<String> getLine() {
        return line;
    }
}
