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
        String afilename = this.location.substring(0, this.location.indexOf(':'));
        String bfilename = defect.location.substring(0, defect.location.indexOf(':'));
        int aNo = Integer.parseInt(this.location.substring(this.location.indexOf(':') + 1, this.location.lastIndexOf(':')));
        int bNo = Integer.parseInt(defect.location.substring(defect.location.indexOf(':') + 1, defect.location.lastIndexOf(':')));
        if(afilename.compareTo(bfilename) != 0)
            return afilename.compareTo(bfilename);
        else
            return Integer.compare(aNo, bNo);
    }
}
