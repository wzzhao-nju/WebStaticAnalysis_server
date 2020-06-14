package com.example.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.util.*;

import com.example.entity.LoginInfo;
import com.example.entity.Record;
import com.example.entity.User;
import com.example.inter.LoginInfoRepository;
import com.example.inter.RecordRepository;
import com.example.inter.UserRepository;
import com.example.message.*;
import com.example.misc.CheckFile;
import com.example.misc.CodeLine;
import com.example.misc.AnalyzeID;
import com.example.misc.Login;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableAsync
//@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
public class Controller {

    private static final String savepath = "/home/WSA/toAnalyze/";
    private static final Logger log = LoggerFactory.getLogger(Controller.class); //用于输出信息

    @Autowired
    private Manager manager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private LoginInfoRepository loginInfoRepository;

    //传输代码字符串
    @PostMapping("/api/uploadString")
    public Message Post(@RequestBody CodeLine codeline, HttpServletRequest request){
        //对字符串进行转存
        String[] code = codeline.getCodeline().split("\n");
        String identity = UUID.randomUUID().toString().replaceAll("-", ""); //由于没有文件名，这里随机生成一个UUID
        Vector<String> filenames = new Vector<>();
        try {
            File directory = new File(savepath + identity);
            if(!directory.exists()) directory.mkdirs();
            File dst = new File(savepath + identity + "/" + identity + ".cpp");
            if(!dst.exists()) dst.createNewFile();
            FileWriter writer = new FileWriter(dst);
            for (String s : code)
                writer.write(s + "\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
            return new Message(-1, null, "服务器无法缓存您的代码，请联系管理员。");
        }
        filenames.add(identity + ".cpp");
        //异步调用分析程序进行分析
        Integer uid = (Integer) request.getSession().getAttribute("uid");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        manager.analyze(uid, timestamp, savepath, identity, filenames);
        return new Message(0, identity);
    }

    //传输文件
    @PostMapping("/api/uploadFile")
    public Message uploadFile(@RequestParam(value = "file") MultipartFile upload, HttpServletRequest request){
        String filename = upload.getOriginalFilename();
        String identity = UUID.randomUUID().toString().replaceAll("-", "");
        Vector<String> filenames = new Vector<>();
        //文件名不能为空，避免程序崩溃
        if(filename == null){
            log.info("Logger: filename is null!");
            return new Message(-1, null, "文件名不能为空！");
        }
        //文件是否为空
        if(upload.isEmpty()){
            log.info(String.format("Logger: file %s is empty", filename));
            return new Message(-1, null, "文件不能为空！");
        }
        //格式检查
        if(!filename.endsWith(".c") && !filename.endsWith(".cpp") && !filename.endsWith(".h") && !filename.endsWith(".zip")){
            log.info(String.format("Logger: the format of file %s is wrong!", filename));
            return new Message(-1, null, "文件格式错误！");
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
                //删除原压缩包
                dst.delete();
            }else{
                filenames.add(filename);
            }
        } catch (IOException | ZipException e){
            log.info(String.format("Logger: restore file %s failed", filename));
            e.printStackTrace();
            return new Message(-1, null, "服务器无法缓存您的文件，请联系管理员。");
        }
        log.info(String.format("Logger: download %s finished! Starting analyzing!\n", filename));
        //异步调用分析程序进行分析
        Integer uid = (Integer) request.getSession().getAttribute("uid");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        manager.analyze(uid, timestamp, savepath, identity, filenames);
        return new Message(0, identity);
    }

    @PostMapping("/api/getResult")
    public Response getResult(@RequestBody AnalyzeID analyzeID) {
        String id = analyzeID.getAnalyzeID();
        Response response = new Response();
        if(recordRepository.existsById(id)) {
            response.setStatusCode(0);
            response.setResults(manager.readJsonFinal(savepath + id + "/" + id + "FinalResult"));
        }
        return response;
    }

    //查看完整文件
    @PostMapping("/api/checkFile")
    public FileText checkFile(@RequestBody CheckFile checkfile){
        FileText filetext = new FileText();
        String identity = checkfile.getAnalyzeID();
        String filename = checkfile.getFilename();
        try{
            File file = new File(savepath + identity + "/" + filename);
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            String str = reader.readLine();
            while (str != null){
                filetext.append(str);
                str = reader.readLine();
            }
            reader.close();
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return filetext;
    }

    //查看历史记录
    @PostMapping("/api/checkHistory")
    public Vector<History> checkHistory(HttpServletRequest request){
        Integer uid = (Integer) request.getSession().getAttribute("uid");
        List<Record> records = recordRepository.findByUid(uid);
        Vector<History> histories = new Vector<>();
        for(Record record: records)
            histories.add(new History(record.getAnalyzeId(), record.getTimestamp(),
                    record.getFilecount(), record.getErrorcount()));
        return histories;
    }

    @PostMapping("/api/register")
    public RegisterLoginInfo register(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response){
        String username = login.getUsername();
        if(username.length() < 4)
            return new RegisterLoginInfo(-1, "用户名不得小于4个字符");
        String password = login.getPassword();
        if(password.length() < 6)
            return new RegisterLoginInfo(-1, "密码不得少于6位");
        if(userRepository.existsByName(username)){
            return new RegisterLoginInfo(-1, "用户名已被使用，请更换一个用户名");
        }else {
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            userRepository.save(user);
            //注册成功后自动登录
            request.getSession().setAttribute("uid", user.getUid());
            setCookie(request, response);
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setSessionId(request.getSession().getId());
            loginInfo.setUid(user.getUid());
            loginInfoRepository.save(loginInfo);
            return new RegisterLoginInfo(0, "注册成功");
        }
    }

    @PostMapping("/api/login")
    public RegisterLoginInfo login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response){
        String username = login.getUsername();
        List<User> users = userRepository.findByName(username);
        if(users.size() != 1){
            return new RegisterLoginInfo(-1, "用户名不存在");
        }else{
            User user = users.get(0);
            String password = login.getPassword();
            if(user.getPassword().equals(password)) {
                System.out.print("uid="+user.getUid()+"\n");
                //如果重复登录, 顶掉前面的人
                List<LoginInfo> infos = loginInfoRepository.findByUid(user.getUid());
                if(infos.size() > 0)
                    for(LoginInfo info: infos)
                        loginInfoRepository.delete(info);
                //设置session, 将登录状态存储到数据库中
                System.out.print(request.getSession().getId()+"\n");
                request.getSession().setAttribute("uid", user.getUid());
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setSessionId(request.getSession().getId());
                loginInfo.setUid(userRepository.findByName(username).get(0).getUid());
                loginInfoRepository.save(loginInfo);
                return new RegisterLoginInfo(0, "登录成功");
            }
            else
                return new RegisterLoginInfo(-1, "密码输入错误");
        }
    }

    @PostMapping("/api/loginAsGuest")
    public RegisterLoginInfo loginAsGuest(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("uid", -1);
        //setCookie(request, response);
        /*
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setSessionId(request.getSession().getId());
        loginInfo.setUid(-1);
        loginInfoRepository.save(loginInfo);
        */
        return new RegisterLoginInfo(0, "登录成功");
    }

    @PostMapping("/api/logoff")
    public RegisterLoginInfo logoff(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        Optional<LoginInfo> loginInfo = loginInfoRepository.findById(sessionId);
        loginInfo.ifPresent(info -> loginInfoRepository.delete(info));
        request.getSession().removeAttribute("uid");
        request.getSession().invalidate();
        return new RegisterLoginInfo(0, "注销成功");
    }

    @PostMapping("setcookie")
     public Message cookie(HttpServletResponse response){
        System.out.print("method!\n");
        Cookie cookie = new Cookie("111", "222");
        //cookie.setDomain("");
        //response.setHeader("Access-Control-Expose-Headers","Set-Cookie");
        //response.setHeader("Access-Control-Allow-Headers","Set-Cookie");
        response.addCookie(cookie);
        response.setHeader("Access-Control-Expose-Headers","MyCookie");
        response.setHeader("Access-Control-Allow-Headers","MyCookie");
        response.setHeader("MyCookie","111=222");
        return new Message(-1, null, "try to set cookie");
    }

    public void setCookie(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setDomain("118.89.104.33");
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("Access-Control-Expose-Headers","MyCookie");
        response.setHeader("Access-Control-Allow-Headers","MyCookie");
        response.setHeader("MyCookie", "JSESSIONID="+sessionId);
    }

}
