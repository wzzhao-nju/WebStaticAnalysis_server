package com.example.controller;

import com.example.message.Result;

import java.io.*;

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

    public void getResult(String filename) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process temp = runtime.exec("clang++ -emit-ast -c " + filename);
            System.out.print(temp.waitFor());
            FileWriter writer = new FileWriter("astList.txt", false);
            writer.write(filename.substring(0, filename.lastIndexOf('.')) + ".ast");
            writer.close();
            Process p = runtime.exec(new String[]{"../SE-Experiment-master/cmake-build-debug/tools/Checker/Checker", "astList.txt", "config.txt"});
            System.out.print(p.waitFor());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
