package com.example.controller;

import com.example.message.Result;

import java.io.IOException;

public class Manager {

    public void testResult(){
        try {
            Runtime.getRuntime().exec("cd ../SE-Experiment-master/tests/IntegrationTest");
            Runtime.getRuntime().exec("source test.sh");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getResult(String savepath, String filename) {
        try {
            Runtime.getRuntime().exec("cd " + savepath);
            Runtime.getRuntime().exec("clang++ -emit-ast -c " + filename);
            String astfilename = filename.substring(0, filename.lastIndexOf('.')) + ".ast";
            Runtime.getRuntime().exec("");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
