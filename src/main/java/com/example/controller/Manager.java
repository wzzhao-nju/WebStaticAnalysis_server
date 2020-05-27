package com.example.controller;

import com.example.message.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Manager {

    public void testResult(){
        try {
            Runtime.getRuntime().exec("../SE-Experiment-master/cmake-build-debug/tools/Checker/Checker " +
                    "../SE-Experiment-master/tests/IntegrationTest/astList.txt " +
                    "../SE-Experiment-master/tests/IntegrationTest/config.txt");
            //Runtime.getRuntime().exec("clang++ -emit-ast -c ../SE-Experiment-master/tests/IntegrationTest/CompareChecker.cpp");
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
