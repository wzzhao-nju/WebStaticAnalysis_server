package com.example.message;

import java.util.Vector;

public class FileText {
    private Vector<String> lines;

    public FileText(){
        lines = new Vector<>();
    }

    public void append(String str){
        lines.add(str);
    }

    public Vector<String> getLines() {
        return lines;
    }
}
