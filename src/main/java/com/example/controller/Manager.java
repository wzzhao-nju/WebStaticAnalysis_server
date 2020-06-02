package com.example.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import com.example.json.Report;
import com.example.message.Result;
import com.fasterxml.jackson.databind.JavaType;
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

    public void getResult(String savepath, String identity, Vector<String> filenames) {
        //savepath是代码的存储路径，identity是文件夹名称，filenames是所有要检测的文件
        run(savepath, identity, filenames);
        ArrayList<Report> reports = readJson(savepath + identity + "/" + identity + ".json");

        Result rst = new Result();
    }

    //系统调用
    public void run(String savepath, String identity, Vector<String> filenames){
        String workpath = savepath + identity + "/";
        try {
            Runtime runtime = Runtime.getRuntime();
            for(String filename: filenames)
                runtime.exec(new String[]{"clang++", "-emit-ast", "-c", filename}, null, new File(workpath)).waitFor();
            writeAstlistAndConfig(savepath, identity, filenames);
            runtime.exec(new String[]{"../../SE-Experiment-master/cmake-build-debug/tools/Checker/Checker",
                    astlistFilename, configFilename}, null, new File(workpath)).waitFor();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    //写缺陷检测程序的相关依赖(astList和config)
    public void writeAstlistAndConfig(String savepath, String identity, Vector<String> filenames) {
        String workpath = savepath + identity + "/";
        try {
            FileWriter ast_writer = new FileWriter(workpath + astlistFilename, false);
            for(String filename:filenames)
                ast_writer.write(filename.substring(filename.lastIndexOf('/'), filename.lastIndexOf('.')) + ".ast\n");
            ast_writer.close();
            FileWriter cfg_writer = new FileWriter(workpath + configFilename, false);
            for (String someConfig : someConfigs)
                cfg_writer.write(someConfig + "\n");
            cfg_writer.write("FileSettings\n");
            cfg_writer.write("{\n");
            cfg_writer.write("\tReportFileName = " + identity + "\n");
            cfg_writer.write("}\n");
            cfg_writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取并解析json结果报告
    public ArrayList<Report> readJson(String jsonFilename){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(jsonFilename + ".json");
        ArrayList<Report> reports = new ArrayList<>();
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Report.class);
            reports = mapper.readValue(jsonFile, type);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return reports;
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