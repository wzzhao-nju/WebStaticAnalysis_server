package com.example.controller;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.example.message.Message;
import com.example.message.Result;
import com.example.misc.CodeLine;
import com.example.misc.Identity;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.RandomStringUtils;

@CrossOrigin(origins = "*")
@RestController
public class Controller {

    private static final String savepath = "D:/";
    private static final Logger log = LoggerFactory.getLogger(Controller.class); //用于输出信息
    private HashMap<String, Vector<String>> identity_filename = new HashMap<>();

    //传输代码字符串
    @PostMapping("/api/uploadString")
    public Message Post(@RequestBody CodeLine codeline){
        //对字符串进行转存
        String[] code = codeline.getCodeline().split("\n");
        String filename = RandomStringUtils.randomAlphabetic(8) + ".cpp"; //由于没有文件名，这里随机生成一个 todo 注册系统上线后，改成 用户名+时间
        try {
            FileWriter writer = new FileWriter(savepath + filename);
            for (String s : code)
                writer.write(s + "\n");
        } catch (IOException e){
            e.printStackTrace();
            return new Message(-1, filename, "Server could not restore your code! Ask admin for details.");
        }
        return new Message(0, filename);
    }

    //传输文件 todo 压缩包
    @PostMapping("/api/uploadFile")
    public Message testFile(@RequestParam(value = "file") MultipartFile upload){
        String filename = upload.getOriginalFilename();
        String identity = UUID.randomUUID().toString().replaceAll("-", "");
        Vector<String> filenames = new Vector<>();
        //文件名不能为空，避免程序崩溃
        if(filename == null){
            log.info("Logger: filename is null!");
            return new Message(-1, null, "Filename is null!");
        }
        //文件是否为空
        if(upload.isEmpty()){
            log.info(String.format("Logger: file %s is empty", filename));
            return new Message(-1, null, "File is empty!");
        }
        //格式检查
        if(!filename.endsWith(".c") && !filename.endsWith(".cpp") && !filename.endsWith(".h") && !filename.endsWith(".zip")){
            log.info(String.format("Logger: the format of file %s is wrong!", filename));
            return new Message(-1, null, "File format is wrong!");
        }
        //转存
        try{
            log.info(String.format("Logger: Starting to restore file %s", filename));
            File dst = new File(savepath + identity + "/" + filename);
            if(!dst.exists()) dst.mkdirs();
            upload.transferTo(dst.getAbsoluteFile());
            //如果是压缩包的话
            if(filename.endsWith(".zip")){
                //解压缩
                ZipFile zipfile = new ZipFile(dst);
                String dest = savepath + identity + "/";
                zipfile.setFileNameCharset("UTF-8");
                zipfile.extractAll(dest);
                //获取其中的所有.c或.cpp文件名
                List<FileHeader> fileHeaderList = zipfile.getFileHeaders();
                for(FileHeader fileHeader: fileHeaderList){
                    String name = fileHeader.getFileName();
                    if(name.endsWith(".cpp") || name.endsWith(".c"))
                        filenames.add(name);
                }
                identity_filename.put(identity, filenames);
                //删除原压缩包
                dst.delete();
            }else{
                filenames.add(filename);
                identity_filename.put(identity, filenames);
            }
        } catch (IOException | ZipException e){
            log.info(String.format("Logger: restore file %s failed", filename));
            e.printStackTrace();
            return new Message(-1, null, "Server could not restore your file! Ask admin for details.");
        }
        log.info(String.format("Logger: download %s finished!\n", filename));
        return new Message(0, identity);
    }

    @PostMapping("/api/getResult")
    public void testResult(@RequestBody Identity identity) {
        //new Manager().getResult(filename.getFilename());
    }

}
