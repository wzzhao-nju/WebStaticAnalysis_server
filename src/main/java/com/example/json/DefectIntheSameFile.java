package com.example.json;

import java.util.Vector;

public class DefectIntheSameFile {
    private String filename;
    private Vector<Defect> defects;

    public DefectIntheSameFile(){
        filename = null;
        defects = new Vector<>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setDefects(Vector<Defect> defects) {
        this.defects = defects;
    }

    public Vector<Defect> getDefects() {
        return defects;
    }

    public boolean isEmpty() {
        return filename == null;
    }

    public void append(Defect defect){
        defects.add(defect);
    }
}
