package com.example.json;

import java.util.Vector;

public class Report {
    private String checkerName;
    private Vector<Defect> defects;

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setDefects(Vector<Defect> defects) {
        this.defects = defects;
    }

    public Vector<Defect> getDefects() {
        return defects;
    }
}
