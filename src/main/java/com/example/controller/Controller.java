package com.example.controller;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.message.Message;
import com.example.message.Result;
import com.example.misc.CodeLine;
import com.example.misc.FileName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.RandomStringUtils;

@CrossOrigin(origins = "*")
@RestController
public class Controller {

    private final String savepath = "";
    private static final Logger log = LoggerFactory.getLogger(Controller.class); //用于输出信息

    //传输代码字符串
    @PostMapping("/api/uploadString")
    public Message Post(@RequestBody CodeLine codeline){
        //对字符串进行转存
        String[] code = codeline.getCodeline().split("\n");
        String filename = RandomStringUtils.randomAlphabetic(16) + ".cpp"; //由于没有文件名，这里随机生成一个 todo 注册系统上线后，改成 用户名+时间
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

    //传输文件 todo 多线程
    @PostMapping("/api/uploadFile")
    public Message testFile(@RequestParam(value = "file") MultipartFile upload) throws Exception{
        String filename = upload.getOriginalFilename();
        //文件名不能为空，避免服务器崩溃
        if(filename == null){
            log.info("Logger: filename is null!");
            return new Message(-1, null, "Filename is null!");
        }
        //文件是否为空
        if(upload.isEmpty()){
            log.info(String.format("Logger: file %s is empty", filename));
            return new Message(-1, filename, "File is empty!");
        }
        //格式检查
        if(!filename.endsWith(".c") && !filename.endsWith(".cpp") && !filename.endsWith(".h")){
            log.info(String.format("Logger: the format of file %s is wrong!", filename));
            return new Message(-1, filename, "File format is wrong!");
        }
        //转存
        try{
            log.info(String.format("Logger: Starting to restore file %s", filename));
            File dst = new File(savepath + filename);
            upload.transferTo(dst);
        } catch (IOException e){
            log.info(String.format("Logger: restore file %s failed", filename));
            e.printStackTrace();
            return new Message(-1, filename, "Server could not restore your file! Ask admin for details.");
        }
        log.info(String.format("Logger: download %s finished!\n", filename));
        return new Message(0, filename);
    }

    @PostMapping("/api/getResult")
    public void testResult(@RequestBody FileName filename) {
        new Manager().getResult(filename.getFilename());
    }

}
