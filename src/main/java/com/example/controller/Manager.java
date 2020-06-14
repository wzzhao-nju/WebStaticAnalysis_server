package com.example.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import com.example.entity.Record;
import com.example.inter.RecordRepository;
import com.example.json.Defect;
import com.example.json.DefectIntheSameFile;
import com.example.json.Report;
import com.example.message.Result;
import com.example.message.Error;
import com.example.message.Line;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//该类主要实现对缺陷检测程序的调用，读取结果后，生成json报文返回给Controller，再由Controller返回给前端
@Component
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

    @Autowired
    private RecordRepository recordRepository;

    @Async("taskExecutor")
    public void analyze(Integer uid, Timestamp timestamp, String savepath, String identity, Vector<String> filenames) {
        //savepath是代码的存储路径，identity(id)是文件夹名称(也是AnalyzeId)，filenames是所有要检测的文件
        //进行系统调用，检测缺陷
        run(savepath, identity, filenames);
        //转存缺陷报告，与Checker无关
        ArrayList<Report> reports = readJson(savepath + identity + "/" + identity);
        Vector<Defect> defects = new Vector<>();
        for(Report report: reports)
            defects.addAll(report.getDefects());
        //对所有缺陷按文件进行排序
        Collections.sort(defects);
        //去重并合并缺陷信息
        merge(defects);
        //将defects按照文件分类
        Vector<DefectIntheSameFile> disfs = classify(defects, identity);
        //逐个文件收集缺陷信息
        Vector<Result> results = new Vector<>();
        for(DefectIntheSameFile disf: disfs) {
            Result result = readFile(disf.getDefects());
            result.setFilename(disf.getFilename());
            results.add(result);
        }
        //将结果写入文件存储起来
        writeJson(savepath + identity + "/" + identity + "FinalResult", results);
        //统计缺陷数量
        int errorCount = 0;
        for(Result result: results)
            errorCount += result.getError_count();
        //分析完成后，将分析结果保存到数据库中
        Record record = new Record();
        record.setUid(uid);
        record.setAnalyzeId(identity);
        record.setTimestamp(timestamp);
        record.setFilecount(results.size());
        record.setErrorcount(errorCount);
        recordRepository.save(record);
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
                ast_writer.write(filename.substring(filename.lastIndexOf('/') + 1, filename.lastIndexOf('.')) + ".ast\n");
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

    public Vector<Result> readJsonFinal(String jsonFilename){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(jsonFilename + ".json");
        Vector<Result> results = new Vector<>();
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(Vector.class, Result.class);
            results = mapper.readValue(jsonFile, type);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    //将检测结果存入json文件
    public void writeJson(String jsonFilename, Vector<Result> results){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(jsonFilename + ".json");
        try {
            mapper.writeValue(jsonFile, results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //去重并合并缺陷信息
    public void merge(Vector<Defect> defects){
        for(int i = 0; i < defects.size() - 1; i++){
            String aLoc = defects.elementAt(i).getLocation();
            String bLoc = defects.elementAt(i+1).getLocation();
            if(aLoc.substring(0, aLoc.lastIndexOf(':')).equals(bLoc.substring(0, bLoc.lastIndexOf(':')))){
                String info = "第" + aLoc.substring(aLoc.lastIndexOf(':') + 1) + "列: " + defects.elementAt(i).getInfo() + "\n第"
                        + bLoc.substring(bLoc.lastIndexOf(':') + 1) + "列: " + defects.elementAt(i + 1).getInfo();
                defects.elementAt(i).setInfo(info);
                defects.removeElementAt(i + 1);
                i--;
            }
        }
    }

    //将defects按文件进行分类
    public Vector<DefectIntheSameFile> classify(Vector<Defect> defects, String identity){
        Vector<DefectIntheSameFile> disfs = new Vector<>();
        int i = 0;
        do{
            DefectIntheSameFile disf = new DefectIntheSameFile();
            for(; i < defects.size(); i++){
                String location = defects.elementAt(i).getLocation();
                String filename = location.substring(location.indexOf(identity) + identity.length() + 1, location.indexOf(':'));
                if(disf.isEmpty()){
                    disf.setFilename(filename);
                    disf.append(defects.elementAt(i));
                }else if(disf.getFilename().equals(filename)){
                    disf.append(defects.elementAt(i));
                }else {
                    disfs.add(disf);
                    break;
                }
            }
            if(i == defects.size())
                disfs.add(disf);
        }while (i < defects.size());
        return disfs;
    }

    public Result readFile(Vector<Defect> defects){
        Result result = new Result();
        for(Defect defect: defects){
            String location = defect.getLocation();
            String filename = location.substring(0, location.indexOf(':'));
            Error error = new Error();
            int startNo = Integer.parseInt(location.substring(location.indexOf(':') + 1, location.lastIndexOf(':')));
            int endNo = startNo;
            error.setStart_line(startNo);
            error.setEnd_line(endNo);
            error.setError_info(defect.getInfo());
            try {
                File file = new File(filename);
                FileReader in = new FileReader(file);
                LineNumberReader reader = new LineNumberReader(in);
                for(int i = startNo - 3; i <= endNo + 3; i++){
                    if(i < 0)
                        continue;
                    if(i < startNo)
                        error.push_before(new Line(i, readLine(reader, i - 1)));
                    else if(i > endNo)
                        error.push_after(new Line(i, readLine(reader, i - 1)));
                    else
                        error.push_rightIn(new Line(i, readLine(reader, i - 1)));
                }
                reader.close();
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            result.append(error);
        }
        return result;
    }

    public String readLine(LineNumberReader reader, int lineNo) throws IOException {
        while (reader.getLineNumber() < lineNo)
            reader.readLine();
        return reader.readLine();
    }

}
