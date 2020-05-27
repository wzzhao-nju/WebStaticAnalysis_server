package com.example.misc;

//用该类来表示一段代码，方便对应Post请求的RequestBody
public class CodeLine {
    private String codeline;

    public CodeLine() { }

    public void setCodeline(String codeline) {
        this.codeline = codeline;
    }

    public String getCodeline() {
        return codeline;
    }

}
