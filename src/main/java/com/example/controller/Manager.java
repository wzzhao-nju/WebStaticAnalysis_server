package com.example.controller;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

//该类主要实现对缺陷检测程序的调用，读取结果后，生成json报文返回给Controller，再由Controller返回给前端
public class Manager {
    private final String astlistFilename = "astList.txt";
    private final String configFilename = "config.txt";
    private final String[] someConfigs = {"CheckerEnable", "{", "\tCharArrayBound = true",
            "\tCompareChecker = true", "\tSwitchChecker = true", "\tZeroChecker = true",
            "\tLoopChecker = true", "\tTemplateChecker = false", "\tCallGraphChecker = false",
            "}", "PrintLog", "{", "\tlevel = 0", "\ttaintChecker = false",
            "\tTemplateChecker = false", "\tarrayBound = false", "\trecursiveCall = false",
            "\tdivideChecker = false", "\tmemoryOPChecker = false", "}", "Framework", "{",
            "\tqueue_size = 100", "}", "TemplateChecker", "{", "\trequest_fun = 2", "}"};

    public void getResult(String filename) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("clang++ -emit-ast -c " + filename).waitFor();
            writeAstlistAndConfig(filename);
            runtime.exec(new String[]{"../SE-Experiment-master/cmake-build-debug/tools/Checker/Checker",
                    astlistFilename, configFilename}).waitFor();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void writeAstlistAndConfig(String filename) {
        try {
            FileWriter ast_writer = new FileWriter(astlistFilename, false);
            ast_writer.write(filename.substring(0, filename.lastIndexOf('.')) + ".ast");
            ast_writer.close();
            FileWriter cfg_writer = new FileWriter(configFilename, false);
            for (String someConfig : someConfigs)
                cfg_writer.write(someConfig + "\n");
            //目前暂定json结果文件和filename相同，保存在当前目录下 todo 后续改进工作
            cfg_writer.write("FileSettings\n");
            cfg_writer.write("{\n");
            cfg_writer.write("\tReportFileName = " + filename + "\n");
            cfg_writer.write("}\n");
            cfg_writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readJson(){
        ObjectMapper mapper = new ObjectMapper();
        //List<Result> results = mapper.readValue(%File, Result.class);
    }
}

//用完就删
    /*
    public void testResult(){
        try {
            Runtime.getRuntime().exec("../SE-Experiment-master/cmake-build-debug/tools/Checker/Checker " +
                    "../SE-Experiment-master/tests/IntegrationTest/astList.txt " +
                    "../SE-Experiment-master/tests/IntegrationTest/config.txt");
            //Runtime.getRuntime().exec("clang++ -emit-ast -c ../SE-Experiment-master/tests/IntegrationTest/CompareChecker.cpp");
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/