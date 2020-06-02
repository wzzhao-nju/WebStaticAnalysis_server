package com.example.json;

public class Defect implements Comparable<Defect>{
    private String location;
    private String info;

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public int compareTo(Defect defect){
        return this.location.compareTo(defect.location);
    }
}
