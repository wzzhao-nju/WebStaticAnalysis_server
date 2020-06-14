package com.example.controller;

import com.example.entity.LoginInfo;
import com.example.inter.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    public LoginHandlerInterceptor(LoginInfoRepository loginInfoRepository){
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        System.out.print("111:" + request.getSession().getId() + "\n");
        Integer uid = (Integer)request.getSession().getAttribute("uid");
        response.setCharacterEncoding("UTF-8");
        try {
            if(uid == null) {
                System.out.print("uid is null\n");
                response.setStatus(430);
                response.getWriter().append("请先登录");
                return false;
            }
            if(uid == -1)
                return true;
            List<LoginInfo> loginInfoList = loginInfoRepository.findByUid(uid);
            if(loginInfoList.size() <= 0 || loginInfoList.get(0).getSessionId().equals(request.getSession().getId()))
                return true;
            else{
                System.out.print("sessionId:"+request.getSession().getId()+"\n");
                System.out.print("database:" + loginInfoList.get(0).getSessionId() + "\n");
                response.setStatus(430);
                response.getWriter().append("登录已过期, 或者已在其他地方登录");
                return false;
            }
        }catch (IOException e) {
            //这里不应该被执行
            e.printStackTrace();
            return false;
        }
    }
}
