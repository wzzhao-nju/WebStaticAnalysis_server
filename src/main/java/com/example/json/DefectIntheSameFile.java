package com.example.json;

import java.util.Vector;

public class DefectIntheSameFile {
    private String filename;
    private Vector<Integer> lineNo;

    public DefectIntheSameFile(){
        filename = null;
        lineNo = new Vector<>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setLineNo(Vector<Integer> lineNo) {
        this.lineNo = lineNo;
    }

    public Vector<Integer> getLineNo() {
        return lineNo;
    }

    public boolean isEmpty() {
        return filename == null;
    }

    public void append(Integer integer){
        lineNo.add(integer);
    }
}
