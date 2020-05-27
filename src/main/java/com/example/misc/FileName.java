package com.example.misc;

import com.fasterxml.jackson.annotation.JsonProperty;
//用该类来表示一个文件名，方便对应Post请求的RequestBody
public class FileName {

    @JsonProperty(value = "filename")
    private String filename;

    public FileName() { }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
